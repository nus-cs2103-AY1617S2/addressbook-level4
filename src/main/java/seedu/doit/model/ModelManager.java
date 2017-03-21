package seedu.doit.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.doit.commons.core.ComponentManager;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.commons.events.model.TaskManagerChangedEvent;
import seedu.doit.commons.util.CollectionUtil;
import seedu.doit.commons.util.StringUtil;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.Task;
import seedu.doit.model.item.UniqueTaskList;
import seedu.doit.model.item.UniqueTaskList.DuplicateTaskException;
import seedu.doit.model.item.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;


    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyItemManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());


    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyItemManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyItemManager getTaskManager() {
        return taskManager;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();

    }


    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void markTask(int taskIndex, ReadOnlyTask taskToDone)
            throws UniqueTaskList.TaskNotFoundException, DuplicateTaskException {
        taskManager.markTask(taskIndex, taskToDone);
        indicateTaskManagerChanged();
    }


    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask) throws DuplicateTaskException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
    }



    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
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
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering =================================================

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
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword))
                .findAny()
                .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    private class PriorityQualifier implements Qualifier {
        private Set<String> priorityQualifier;

        PriorityQualifier(Set<String> priorityQualifier) {
            this.priorityQualifier = priorityQualifier;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return priorityQualifier.stream()
                .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getPriority().value, keyword))
                .findAny()
                .isPresent();
        }

        @Override
        public String toString() {
            return "priority=" + String.join(", ", priorityQualifier);
        }
    }

    private class DescriptionQualifier implements Qualifier {
        private Set<String> descriptionQualifier;

        DescriptionQualifier(Set<String> descriptionQualifier) {
            this.descriptionQualifier = descriptionQualifier;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return descriptionQualifier.stream()
                .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().value, keyword))
                .findAny()
                .isPresent();
        }

        @Override
        public String toString() {
            return "description=" + String.join(", ", descriptionQualifier);
        }
    }

}
