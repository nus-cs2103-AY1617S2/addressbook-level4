package seedu.opus.model;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.opus.commons.core.ComponentManager;
import seedu.opus.commons.core.LogsCenter;
import seedu.opus.commons.core.UnmodifiableObservableList;
import seedu.opus.commons.events.model.ChangeSaveLocationEvent;
import seedu.opus.commons.events.model.TaskManagerChangedEvent;
import seedu.opus.commons.exceptions.InvalidUndoException;
import seedu.opus.commons.util.CollectionUtil;
import seedu.opus.model.qualifier.Qualifier;
import seedu.opus.model.task.ReadOnlyTask;
import seedu.opus.model.task.Task;
import seedu.opus.model.task.UniqueTaskList;
import seedu.opus.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private FilteredList<ReadOnlyTask> filteredTasks;

    private History history;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        history = new History();
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        history.backupCurrentState(this.taskManager);
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    //@@author A0148081H
    /** Raises an event to indicate that save location has changed */
    private void indicateChangeSaveLocation(String location) {
        raise(new ChangeSaveLocationEvent(location));
    }
    //@@author

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        history.backupCurrentState(this.taskManager);
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        history.backupCurrentState(this.taskManager);
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        history.backupCurrentState(this.taskManager);
        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
    }

    //@@author A0148087W
    @Override
    public void resetToPreviousState() throws InvalidUndoException {
        this.taskManager.resetData(this.history.getPreviousState(this.taskManager));
        indicateTaskManagerChanged();
    }

    @Override
    public void resetToPrecedingState() throws InvalidUndoException {
        this.taskManager.resetData(this.history.getPrecedingState(this.taskManager));
        indicateTaskManagerChanged();
    }
    //@@author

    //=========== Storage Method ==========================================================================

    //@@author A0148081H
    @Override
    public synchronized void changeSaveLocation(String location) {
        indicateChangeSaveLocation(location);
        indicateTaskManagerChanged();
    }
    //@@author

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    //@@author A0148081H
    @Override
    public void sortList(String keyword) {
        taskManager.sortTasks(keyword);
        indicateTaskManagerChanged();
    }
    //@@author

    public int getTaskIndex(ReadOnlyTask task) {
        return filteredTasks.indexOf(task);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    //@@author A0126345J
    @Override
    public void updateFilteredTaskList(List<Qualifier> qualifiers) {
        updateFilteredTaskList(new PredicateExpression(qualifiers));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
    }

    private class PredicateExpression implements Expression {

        private final List<Qualifier> qualifiers;

        PredicateExpression(List<Qualifier> qualifiers) {
            this.qualifiers = qualifiers;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            boolean result = true;
            for (Qualifier qualifier: qualifiers) {
                result = result & qualifier.run(task);
            }
            return result;
        }

    }
    //@@author

}
