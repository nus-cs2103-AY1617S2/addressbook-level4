package seedu.doit.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.doit.commons.core.ComponentManager;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.commons.events.model.TaskManagerChangedEvent;
import seedu.doit.commons.exceptions.EmptyTaskManagerStackException;
import seedu.doit.commons.util.CollectionUtil;
import seedu.doit.commons.util.StringUtil;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.Task;
import seedu.doit.model.item.UniqueTaskList;
import seedu.doit.model.item.UniqueTaskList.DuplicateTaskException;
import seedu.doit.model.item.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private TaskManager taskManager;
    private FilteredList<ReadOnlyTask> filteredTasks;

    private static final TaskManagerStack taskManagerStack = TaskManagerStack.getInstance();

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyItemManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        updateFilteredTasks();

    }

    /**
     * Updates the filteredTasks after the taskmanager have changed
     */
    public void updateFilteredTasks() {
        this.filteredTasks = new FilteredList<ReadOnlyTask>(this.taskManager.getTaskList());
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyItemManager newData) {
        this.taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyItemManager getTaskManager() {
        return this.taskManager;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(this.taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        logger.info("delete task in model manager");
        TaskManagerStack.addToUndoStack(this.getTaskManager());
        this.taskManager.removeTask(target);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();

    }

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        logger.info("add task in model manager");
        TaskManagerStack.addToUndoStack(this.getTaskManager());
        this.taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void markTask(int taskIndex, ReadOnlyTask taskToDone)
            throws UniqueTaskList.TaskNotFoundException, DuplicateTaskException {
        logger.info("marked a task in model manager as done");
        TaskManagerStack.addToUndoStack(this.getTaskManager());
        this.taskManager.markTask(taskIndex, taskToDone);
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask) throws DuplicateTaskException {
        assert editedTask != null;
        logger.info("update task in model manager");
        TaskManagerStack.addToUndoStack(this.getTaskManager());
        int taskManagerIndex = this.filteredTasks.getSourceIndex(filteredTaskListIndex);
        this.taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
    }

    // =========== Filtered Task List Accessors
    // =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(this.filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        this.filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> nameKeywords, Set<String> priorityKeywords,
            Set<String> descriptionKeywords) {
        if (!nameKeywords.isEmpty()) {
            updateFilteredTaskList(new PredicateExpression(new NameQualifier(nameKeywords)));
        }

        if (!priorityKeywords.isEmpty()) {
            updateFilteredTaskList(new PredicateExpression(new PriorityQualifier(priorityKeywords)));
        }

        if (!descriptionKeywords.isEmpty()) {
            updateFilteredTaskList(new PredicateExpression(new DescriptionQualifier(descriptionKeywords)));
        }

    }

    private void updateFilteredTaskList(Expression expression) {
        this.filteredTasks.setPredicate(expression::satisfies);
    }

    // ========== Inner classes/interfaces used for filtering
    // =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);

        @Override
        String toString();
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);

        @Override
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return this.qualifier.run(task);
        }

        @Override
        public String toString() {
            return this.qualifier.toString();
        }
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override

        public boolean run(ReadOnlyTask task) {
            return this.nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword)).findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", this.nameKeyWords);
        }
    }

    @Override
    public void undo() throws EmptyTaskManagerStackException {
        this.taskManager.resetData(taskManagerStack.loadOlderTaskManager(this.getTaskManager()));
        updateFilteredTasks();
        indicateTaskManagerChanged();
    }

    @Override
    public void redo() throws EmptyTaskManagerStackException {
        this.taskManager.resetData(taskManagerStack.loadNewerTaskManager(this.getTaskManager()));
        updateFilteredTasks();
        indicateTaskManagerChanged();
    }

    private class PriorityQualifier implements Qualifier {
        private Set<String> priorityKeywords;

        PriorityQualifier(Set<String> priorityKeywords) {
            this.priorityKeywords = priorityKeywords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return this.priorityKeywords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getPriority().value, keyword)).findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "priority=" + String.join(", ", this.priorityKeywords);
        }
    }

    private class DescriptionQualifier implements Qualifier {
        private Set<String> descriptionKeywords;

        DescriptionQualifier(Set<String> descriptionKeywords) {
            this.descriptionKeywords = descriptionKeywords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return this.descriptionKeywords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().value, keyword))
                    .findAny().isPresent();
        }

        @Override
        public String toString() {
            return "description=" + String.join(", ", this.descriptionKeywords);
        }
    }

}
