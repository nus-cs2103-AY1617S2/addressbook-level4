package seedu.doit.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.doit.commons.core.ComponentManager;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.commons.events.model.TaskManagerChangedEvent;
import seedu.doit.commons.exceptions.EmptyTaskManagerStackException;
import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.commons.util.CollectionUtil;
import seedu.doit.commons.util.StringUtil;
import seedu.doit.logic.commands.exceptions.CommandExistedException;
import seedu.doit.logic.commands.exceptions.NoSuchCommandException;
import seedu.doit.model.item.EndTimeComparator;
import seedu.doit.model.item.PriorityComparator;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.StartTimeComparator;
import seedu.doit.model.item.Task;
import seedu.doit.model.item.TaskNameComparator;
import seedu.doit.model.item.UniqueTaskList;
import seedu.doit.model.item.UniqueTaskList.DuplicateTaskException;
import seedu.doit.model.item.UniqueTaskList.TaskNotFoundException;
import seedu.doit.model.tag.Tag;

/**
 * Represents the in-memory model of the task manager data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private TaskManager taskManager;
    private FilteredList<ReadOnlyTask> filteredTasks;

    private static final TaskManagerStack taskManagerStack = TaskManagerStack.getInstance();
    private UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyItemManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        this.userPrefs = userPrefs;
        updateFilteredTasks();

    }

    // @@author A0138909R
    /**
     * Updates the filteredTasks after the taskmanager have changed
     */
    public void updateFilteredTasks() {
        this.filteredTasks = new FilteredList<ReadOnlyTask>(this.taskManager.getTaskList());
    }

    // @@author
    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyItemManager newData) {
        this.taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    // @@author A0138909R
    @Override
    public void clearData() {
        logger.info("clears all tasks in model manager");
        taskManagerStack.addToUndoStack(this.getTaskManager());
        taskManagerStack.clearRedoStack();
        this.taskManager.resetData(new TaskManager());
        indicateTaskManagerChanged();
    }
    // @@author

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
        taskManagerStack.addToUndoStack(this.getTaskManager());
        taskManagerStack.clearRedoStack();
        this.taskManager.removeTask(target);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();

    }

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        logger.info("add task in model manager");
        taskManagerStack.addToUndoStack(this.getTaskManager());
        taskManagerStack.clearRedoStack();
        this.taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void markTask(int taskIndex, ReadOnlyTask taskToDone)
            throws UniqueTaskList.TaskNotFoundException, DuplicateTaskException {
        logger.info("marked a task in model manager as done");
        taskManagerStack.addToUndoStack(this.getTaskManager());
        taskManagerStack.clearRedoStack();
        this.taskManager.markTask(taskIndex, taskToDone);
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask) throws DuplicateTaskException {
        assert editedTask != null;
        logger.info("update task in model manager");
        taskManagerStack.addToUndoStack(this.getTaskManager());
        taskManagerStack.clearRedoStack();
        int taskManagerIndex = this.filteredTasks.getSourceIndex(filteredTaskListIndex);
        this.taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
    }

    @Override
    public void sortBy(String sortType) {
        switch (sortType) {
        case "start time":
            this.taskManager.setTaskComparator(new StartTimeComparator());
            break;
        case "end time":
            this.taskManager.setTaskComparator(new EndTimeComparator());
            break;
        case "priority":
            this.taskManager.setTaskComparator(new PriorityComparator());
            break;
        case "name":
            // fallthrough
        default:
            this.taskManager.setTaskComparator(new TaskNameComparator());
            break;
        }
    }

    // =========== Filtered Task List Accessors
    // ============================================================

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
            Set<String> descriptionKeywords, Set<String> tagKeywords) {
        if (!nameKeywords.isEmpty()) {
            updateFilteredTaskList(new PredicateExpression(new NameQualifier(nameKeywords)));
        }

        if (!priorityKeywords.isEmpty()) {
            updateFilteredTaskList(new PredicateExpression(new PriorityQualifier(priorityKeywords)));
        }

        if (!descriptionKeywords.isEmpty()) {
            updateFilteredTaskList(new PredicateExpression(new DescriptionQualifier(descriptionKeywords)));
        }

        if (!tagKeywords.isEmpty()) {
            updateFilteredTaskList(new PredicateExpression(new TagQualifier(tagKeywords)));
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
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword));
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", this.nameKeyWords);
        }
    }

    // @@author A0138909R
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

    @Override
    public void commandSet(String oldCommand, String newCommand)
            throws NoSuchCommandException, CommandExistedException {
        this.userPrefs.getCommandSettings().setCommand(oldCommand, newCommand);
    }

    // @@author
    private class PriorityQualifier implements Qualifier {
        private Set<String> priorityKeywords;

        PriorityQualifier(Set<String> priorityKeywords) {
            this.priorityKeywords = priorityKeywords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return this.priorityKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getPriority().value, keyword));
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
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().value, keyword));
        }

        @Override
        public String toString() {
            return "description=" + String.join(", ", this.descriptionKeywords);
        }
    }

    private class TagQualifier implements Qualifier {
        private Set<String> tagKeywords;

        TagQualifier(Set<String> tagKeywords) {
            this.tagKeywords = tagKeywords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return this.tagKeywords.stream().anyMatch(keyword -> {
                try {
                    return (task.getTags().contains(new Tag(keyword)));
                } catch (IllegalValueException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return false;
                }
            });
        }

        @Override
        public String toString() {
            return "tag=" + String.join(", ", this.tagKeywords);
        }
    }

}
