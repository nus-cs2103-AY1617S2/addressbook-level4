package seedu.taskmanager.model;

import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.taskmanager.commons.core.ComponentManager;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.commons.events.model.TaskManagerChangedEvent;
import seedu.taskmanager.commons.util.CollectionUtil;
import seedu.taskmanager.commons.util.StringUtil;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.UniqueTaskList;
import seedu.taskmanager.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private TaskManager taskManager;
    private Stack<TaskManager> undoTaskManager;
    private Stack<TaskManager> redoTaskManager;
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
        undoTaskManager = new Stack<TaskManager>();
        redoTaskManager = new Stack<TaskManager>();
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        saveInstance();
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

    // @@author A0142418L
    /** Re-save data when save location has changed */
    public void saveTaskManager() {
        indicateTaskManagerChanged();
    }

    /** Save a copy of task manager before data is changed. */
    private void saveInstance() {
        undoTaskManager.push(new TaskManager(taskManager));
        redoTaskManager.clear();
    }

    /** Undo previous action of task manager. */
    public void undoTaskManager() {
        TaskManager currentTaskManager = new TaskManager(taskManager);
        taskManager.resetData(undoTaskManager.peek());
        undoTaskManager.pop();
        redoTaskManager.push(currentTaskManager);
        indicateTaskManagerChanged();
    }

    /** Undo previous action of task manager. */
    public void redoTaskManager() {
        TaskManager currentTaskManager = new TaskManager(taskManager);
        taskManager.resetData(redoTaskManager.peek());
        redoTaskManager.pop();
        undoTaskManager.push(currentTaskManager);
        indicateTaskManagerChanged();
    }

    // @@author
    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        saveInstance();
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    // @@author A0142418L
    /**
     * Deletes tasks by their date. Returns the number of tasks deleted.
     */
    @Override
    public synchronized int deleteTasksDate(UnmodifiableObservableList<ReadOnlyTask> targets)
            throws TaskNotFoundException {
        int numDeletedTasks = 0;
        saveInstance();
        while (targets.size() != 0) {
            try {
                ReadOnlyTask taskToDelete = targets.get(0);
                saveInstance();
                taskManager.removeTask(taskToDelete);
                numDeletedTasks++;
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
        }
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
        return numDeletedTasks;
    }

    /**
     * Deletes the task by its name. Returns the number of tasks deleted.
     */
    @Override
    public synchronized int deleteTasksName(UnmodifiableObservableList<ReadOnlyTask> targets, String toDeleteTaskName)
            throws TaskNotFoundException {
        int numDeletedTasks = 0;
        saveInstance();
        for (int index = 0; targets.size() != index; index++) {
            try {
                ReadOnlyTask taskToDelete = targets.get(index);
                if (toDeleteTaskName.equals(taskToDelete.getTaskName().fullTaskName)) {
                    taskManager.removeTask(taskToDelete);
                    index--;
                    numDeletedTasks++;
                }
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
        }
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
        return numDeletedTasks;
    }

    @Override
    public synchronized int addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        saveInstance();
        int addIndex = taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
        return addIndex;
    }

    //
    // @Override
    // public synchronized int addRecurringDeadlineTask(ReadOnlyTask
    // taskToRecur, int recurring, String type) throws
    // UniqueTaskList.DuplicateTaskException {
    // saveInstance();
    // for (int loop = 1; loop <= recurring; loop++) {
    //
    // try {
    // recurringTask = new Task(taskToRecur.getTaskName(),
    // taskToRecur.getStartDate(),
    // taskToRecur.getStartTime(),
    // new EndDate(DateTimeUtil.getFutureDate(loop, type,
    // taskToRecur.getEndDate().toString())),
    // taskToRecur.getEndTime(), false, taskToRecur.getCategories());
    // } catch (IllegalValueException ive) {
    // throw new CommandException("Wrong format for deadline!");
    // }
    //
    // if (DateTimeUtil.isValidDate(recurringTask.getEndDate().toString())) {
    // try {
    // addTask(recurringTask);
    // } catch (DuplicateTaskException dte) {
    // throw new CommandException(MESSAGE_DUPLICATE_TASK);
    // }
    // }
    // }
    //
    // @Override
    // public synchronized int addRecurringEventTask(ReadOnlyTask taskToRecur,
    // int recurring, String type) throws UniqueTaskList.DuplicateTaskException
    // {
    //
    // @@author A0139520L
    @Override
    public int isBlockedOutTime(Task t) throws UniqueTaskList.DuplicateTaskException {
        int index = 0;
        while (index < (filteredTasks.size())) {
            if (filteredTasks.get(index).isEventTask() && !filteredTasks.get(index).getIsMarkedAsComplete()
                    && t.isWithinStartEndDuration(filteredTasks.get(index))) {
                if (isAddEventEarlierAddListIndex(t, filteredTasks.get(index))) {
                    return index + 2;
                }
                return index + 1;
            }
            index++;
        }
        return -1;
    }

    @Override
    public int isBlockedOutTime(Task t, int UpdateTaskIndex) throws UniqueTaskList.DuplicateTaskException {
        int index = 0;
        while (index < (filteredTasks.size())) {
            if ((index != (UpdateTaskIndex)) && filteredTasks.get(index).isEventTask()
                    && !filteredTasks.get(index).getIsMarkedAsComplete()
                    && t.isWithinStartEndDuration(filteredTasks.get(index))) {
                if (isAddEventEarlierAddListIndex(t, filteredTasks.get(index))) {
                    if (UpdateTaskIndex > index) {
                        return index + 2;
                    } else {
                        return index + 1;
                    }
                } else {
                    if (UpdateTaskIndex > index) {
                        return index + 1;
                    } else {
                        return index;
                    }
                }
            }
            index++;
        }
        return -1;
    }

    @Override
    public int updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;
        saveInstance();
        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        int updateIndex = taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
        return updateIndex;
    }

    // @@author A0139520L
    @Override
    public void markTask(int filteredTaskListIndex) throws UniqueTaskList.DuplicateTaskException {
        saveInstance();
        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.markTask(taskManagerIndex, true);
        indicateTaskManagerChanged();
    }

    @Override
    public void unmarkTask(int filteredTaskListIndex) throws UniqueTaskList.DuplicateTaskException {
        saveInstance();
        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.markTask(taskManagerIndex, false);
        indicateTaskManagerChanged();
    }
    // =========== Filtered Task List Accessors
    // =============================================================

    // @@author
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
        updateFilteredTaskListToShowByCompletion(false);
        indicateTaskManagerChanged();
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new TaskQualifier(keywords)));
        indicateTaskManagerChanged();
    }

    // @@author A0141102H
    @Override
    public void updateFilteredTaskListForListCommand(Set<String> keywords, boolean isComplete) {
        updateFilteredTaskList(new PredicateExpression(new ListQualifier(keywords, isComplete)));
        indicateTaskManagerChanged();
    }

    // @@author A0139520L
    @Override
    public void updateFilteredTaskListToShowByCompletion(boolean isComplete) {
        updateFilteredTaskList(new PredicateExpression(new CompletedQualifier(isComplete)));
        indicateTaskManagerChanged();
    }

    // @@author A0142418L
    @Override
    public void updateFilteredTaskListForInitialView() {
        updateFilteredTaskList(new PredicateExpression(new CompletedQualifier(false)));
    }

    // ========== Inner classes/interfaces used for filtering
    // =================================================

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

    private class TaskQualifier implements Qualifier {
        private Set<String> taskKeyWords;

        TaskQualifier(Set<String> taskKeyWords) {
            this.taskKeyWords = taskKeyWords;
        }

        public boolean run(ReadOnlyTask task) {
            return (taskKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getTaskName().fullTaskName, keyword))
                    .findAny().isPresent())
                    || (taskKeyWords.stream()
                            .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getStartDate().value, keyword))
                            .findAny().isPresent())
                    || (taskKeyWords.stream()
                            .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getStartTime().value, keyword))
                            .findAny().isPresent())
                    || (taskKeyWords.stream()
                            .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getEndDate().value, keyword))
                            .findAny().isPresent())
                    || (taskKeyWords.stream()
                            .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getEndTime().value, keyword))
                            .findAny().isPresent())
                    || (taskKeyWords.stream()
                            .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getCategories(), keyword))
                            .findAny().isPresent());
        }

        @Override
        public String toString() {
            return "task name=" + String.join(", ", taskKeyWords);
        }
    }

    // @@author A0139520L
    private class CompletedQualifier implements Qualifier {
        private boolean isComplete;

        CompletedQualifier(boolean isComplete) {
            this.isComplete = isComplete;
        }

        public boolean run(ReadOnlyTask task) {
            return (task.getIsMarkedAsComplete().equals(isComplete));
        }

        @Override
        public String toString() {
            return "task name=" + String.join(", ", "w");
        }
    }

    // @@author A0141102H
    private class ListQualifier implements Qualifier {
        private boolean isComplete;
        private Set<String> taskKeyWords;

        ListQualifier(Set<String> taskKeyWords, boolean isComplete) {
            this.taskKeyWords = taskKeyWords;
            this.isComplete = isComplete;
        }

        public boolean run(ReadOnlyTask task) {
            return (task.getIsMarkedAsComplete().equals(isComplete))
                    && (taskKeyWords.stream()
                            .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getStartDate().value, keyword))
                            .findAny().isPresent())
                    || (taskKeyWords.stream()
                            .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getEndDate().value, keyword))
                            .findAny().isPresent());
        }

        @Override
        public String toString() {
            return "task name=" + String.join(", ", taskKeyWords);
        }
    }

    // @@author A0142418L
    /**
     * Compares the starting date and time of 2 event tasks.
     *
     * @return true if 1st event task is earlier than the 2nd event task based
     *         on the startDate and startTime
     * @return false, if otherwise.
     */
    private boolean isAddEventEarlierAddListIndex(Task toAdd, ReadOnlyTask readOnlyTask) {
        if (toAdd.getStartDate().value.substring(toAdd.getStartDate().value.length() - 2).compareTo(
                readOnlyTask.getStartDate().value.substring(readOnlyTask.getStartDate().value.length() - 2)) < 0) {
            return true;
        } else {
            if (toAdd.getStartDate().value.substring(toAdd.getStartDate().value.length() - 2).compareTo(
                    readOnlyTask.getStartDate().value.substring(readOnlyTask.getStartDate().value.length() - 2)) == 0) {
                if (toAdd.getStartDate().value
                        .substring(toAdd.getStartDate().value.length() - 5, toAdd.getStartDate().value.length() - 3)
                        .compareTo(readOnlyTask.getStartDate().value.substring(
                                readOnlyTask.getStartDate().value.length()
                                        - 5,
                                readOnlyTask.getStartDate().value.length() - 3)) < 0) {
                    return true;
                } else {
                    if (toAdd.getStartDate().value
                            .substring(toAdd.getStartDate().value.length() - 5, toAdd.getStartDate().value.length() - 3)
                            .compareTo(readOnlyTask.getStartDate().value.substring(
                                    readOnlyTask.getStartDate().value.length()
                                            - 5,
                                    readOnlyTask.getStartDate().value.length() - 3)) == 0) {
                        if (toAdd.getStartDate().value.substring(0, toAdd.getStartDate().value.length() - 6)
                                .compareTo(readOnlyTask.getStartDate().value.substring(0,
                                        readOnlyTask.getStartDate().value.length() - 6)) < 0) {
                            return true;
                        } else {
                            if (toAdd.getStartDate().value.substring(0, toAdd.getStartDate().value.length() - 6)
                                    .compareTo(readOnlyTask.getStartDate().value.substring(0,
                                            readOnlyTask.getStartDate().value.length() - 6)) == 0) {
                                if (toAdd.getStartTime().value.compareTo(readOnlyTask.getStartTime().value) < 0) {
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
    }
}
