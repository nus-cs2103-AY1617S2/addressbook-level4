package seedu.task.model;

import java.util.Comparator;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.model.TaskListChangedEvent;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.DueDate;
import seedu.task.model.task.Duration;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskId;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task list data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskList taskList;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    //@@author A0163744B
    private final SortedList<ReadOnlyTask> sortedTasks;
    //@@author

    /**
     * Initializes a ModelManager with the given taskList and userPrefs.
     */
    public ModelManager(ReadOnlyTaskList taskList, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskList, userPrefs);

        logger.fine("Initializing with task list: " + taskList + " and user prefs " + userPrefs);

        this.taskList = new TaskList(taskList);
        filteredTasks = new FilteredList<>(this.taskList.getTaskList());
        sortedTasks = new SortedList<>(this.filteredTasks, new TaskIdComparator());
    }

    public ModelManager() {
        this(new TaskList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskList newData) {
        taskList.resetData(newData);
        indicateTaskListChanged();
    }

    @Override
    public ReadOnlyTaskList getTaskList() {
        return taskList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskListChanged() {
        raise(new TaskListChangedEvent(taskList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskList.removeTask(target);
        indicateTaskListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskList.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskListChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int taskListIndex = filteredTasks.getSourceIndex((sortedTasks.getSourceIndex(filteredTaskListIndex)));
        taskList.updateTask(taskListIndex, editedTask);
        indicateTaskListChanged();
    }

    @Override
    public void updateTaskById(TaskId id, ReadOnlyTask newTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert newTask != null;
        taskList.updateTaskById(id, newTask);
        indicateTaskListChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(sortedTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //@@author A0163744B
    @Override
    public void updateFilteredListToShowCompletion(boolean isComplete) {
        updateFilteredTaskList(new PredicateExpression(new CompletionQualifier(isComplete)));
    }

    //=========== Sorted Task List Accessors =============================================================

    @Override
    public void updateFilteredListToSortById() {
        sortedTasks.setComparator(new TaskIdComparator());
    }

    @Override
    public void updateFilteredListToSortByDue() {
        sortedTasks.setComparator(new TaskDueComparator());
    }

    @Override
    public void updateFilteredListToSortByStart() {
        sortedTasks.setComparator(new TaskStartComparator());
    }

    @Override
    public void updateFilteredListToSortByEnd() {
        sortedTasks.setComparator(new TaskEndComparator());
    }
    //@@author

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
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

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        //@@author A0163673Y
        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().description, keyword)
                            || UniqueTagList.containsWordIgnoreCase(task.getTags(), keyword))
                    .findAny()
                    .isPresent();
        }
        //@@author

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    //@@author A0163744B
    private class CompletionQualifier implements Qualifier {
        private boolean isComplete;

        private static final String TRUE = "true";
        private static final String FALSE = "false";

        CompletionQualifier(boolean isComplete) {
            this.isComplete = isComplete;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getComplete().isComplete == this.isComplete;
        }

        @Override
        public String toString() {
            return "complete=" + (this.isComplete ? TRUE : FALSE);
        }
    }

    private class TaskIdComparator implements Comparator<ReadOnlyTask> {

        /**
         * Compares the ids of {@code task1} to {@code task2}.
         * If the id of {@code task1} is less than the id of {@code task2}, a value less than 0
         * is returned. If the id of {@code task1} is after the id of {@code task2}, a
         * value greater than 0 is returned. If the ids are equal, 0 is returned.
         */
        @Override
        public int compare(ReadOnlyTask task1, ReadOnlyTask task2) {
            TaskId id1 = task1.getTaskId();
            TaskId id2 = task2.getTaskId();

            assert id1 != null && id2 != null;
            if (id1.id == id2.id) {
                return 1;
            }
            return id1.id < id2.id ? -1 : 1;
        }

    }

    private class TaskDueComparator implements Comparator<ReadOnlyTask> {

        /**
         * Compares the due date of {@code task1} to {@code task2}.
         * If the due date of {@code task1} is before the due date of {@code task2}, a value less than 0
         * is returned. If the due date of {@code task1} is after the due date of {@code task2}, a
         * value greater than 0 is returned. If the due dates are equal, 0 is returned. Note that a
         * null due date is considered to be infinity.
         */
        @Override
        public int compare(ReadOnlyTask task1, ReadOnlyTask task2) {
            DueDate dueDate1 = task1.getDueDate();
            DueDate dueDate2 = task2.getDueDate();

            if ((dueDate1 == null ? dueDate2 == null : dueDate1.equals(dueDate2))) {
                return 0;
            }
            if (dueDate1 == null) {
                return 1;
            }
            if (dueDate2 == null) {
                return -1;
            }
            return dueDate1.dueDate.compareTo(dueDate2.dueDate);
        }
    }

    private class TaskStartComparator implements Comparator<ReadOnlyTask> {

        /**
         * Compares the start of {@code task1} to {@code task2}.
         * If the start of {@code task1} is before the start of {@code task2}, a value less than 0
         * is returned. If the start of {@code task1} is after the start of {@code task2}, a
         * value greater than 0 is returned. If the starts are equal, 0 is returned. Note that a
         * null start is considered to be infinity.
         */
        @Override
        public int compare(ReadOnlyTask task1, ReadOnlyTask task2) {
            Duration duration1 = task1.getDuration();
            Duration duration2 = task2.getDuration();

            if ((duration1 == null ? duration2 == null : duration1.equals(duration2))) {
                return 0;
            }
            if (duration1 == null) {
                return 1;
            }
            if (duration2 == null) {
                return -1;
            }
            return duration1.start.compareTo(duration2.start);
        }
    }

    private class TaskEndComparator implements Comparator<ReadOnlyTask> {

        /**
         * Compares the end of {@code task1} to {@code task2}.
         * If the end of {@code task1} is before the end of {@code task2}, a value less than 0
         * is returned. If the end of {@code task1} is after the end of {@code task2}, a
         * value greater than 0 is returned. If the ends are equal, 0 is returned. Note that a
         * null end is considered to be infinity.
         */
        @Override
        public int compare(ReadOnlyTask task1, ReadOnlyTask task2) {
            Duration duration1 = task1.getDuration();
            Duration duration2 = task2.getDuration();

            if ((duration1 == null ? duration2 == null : duration1.equals(duration2))) {
                return 0;
            }
            if (duration1 == null) {
                return 1;
            }
            if (duration2 == null) {
                return -1;
            }
            return duration1.end.compareTo(duration2.end);
        }
    }
}
