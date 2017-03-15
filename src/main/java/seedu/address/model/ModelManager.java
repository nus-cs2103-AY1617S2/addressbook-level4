package seedu.address.model;

import java.util.Date;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.exceptions.InvalidUndoCommandException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.label.Label;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
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
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    @Override
    public TaskManager getRawTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;
        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
    }

    @Override
    public void undoPrevious(ObservableList<ReadOnlyTask> oldTaskState, ObservableList<Label> oldLabelState)
            throws InvalidUndoCommandException {
        taskManager.undoData(oldTaskState, oldLabelState);
        updateFilteredListToShowAll();
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
    public void updateFilteredTaskList(Date startDate, Date endDate) {
        updateFilteredTaskListByDate(new DateFilter(startDate, endDate));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    @Override
    public void updateFilteredTaskList(Boolean isCompleted) {
        updateFilteredTaskListByCompletion(new StatusFilter(isCompleted));
    }

    private void updateFilteredTaskListByDate(DateFilter dateFilter) {
        filteredTasks.setPredicate(dateFilter::run);
    }

    private void updateFilteredTaskListByCompletion(StatusFilter statusFilter) {
        filteredTasks.setPredicate(statusFilter::run);
    }

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

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getTitle().title, keyword))
                    .findAny()
                    .isPresent() ||
                    nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getDeadline().toString(), keyword))
                    .findAny()
                    .isPresent() ||
                    nameKeyWords.stream()
                    .filter(keyword -> task.getLabels().containsStringLabel(keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    private class DateFilter {
        private Date startTime;
        private Date endTime;

        DateFilter(Date startTime, Date endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public boolean run(ReadOnlyTask task) {
            if (task.getDeadline().isPresent() && task.getStartTime().isPresent()) {
                return (task.getDeadline().get().getDateTime().before(endTime)
                        && task.getDeadline().get().getDateTime().after(startTime))
                        || task.getDeadline().get().getDateTime().equals(endTime);
            }
            return false;
        }
    }

    private class StatusFilter {
        private boolean isCompleted;

        StatusFilter(boolean isCompleted) {
            this.isCompleted = isCompleted;
        }

        public boolean run(ReadOnlyTask task) {
            return task.isCompleted().booleanValue() == isCompleted;
        }
    }
}
