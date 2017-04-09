package typetask.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import typetask.commons.core.ComponentManager;
import typetask.commons.core.LogsCenter;
import typetask.commons.core.UnmodifiableObservableList;
import typetask.commons.events.model.TaskManagerChangedEvent;
import typetask.commons.util.CollectionUtil;
import typetask.commons.util.StorageUtil;
import typetask.commons.util.StringUtil;
import typetask.logic.parser.DateParser;
import typetask.model.task.Priority;
import typetask.model.task.ReadOnlyTask;
import typetask.model.task.Task;
import typetask.model.task.TaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    //@@author A0139926R
    private Stack<TaskManager> taskManagerHistory = new Stack<TaskManager>();
    private Stack<TaskManager> redoTaskManagerHistory = new Stack<TaskManager>();

    public static final Integer STATUS_EMPTY_HISTORY = 0;
    public static final Integer STATUS_AVAILABLE_HISTORY = 1;
    public static final Integer STATUS_ERROR_HISTORY = -1;

    public static final int WANTED_DATE = 0;
    //@@author
    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with Task Manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        updateFilteredTaskList(false);
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
    public synchronized void addTask(Task task) {
        taskManager.addTask(task);
        updateFilteredTaskList(false);
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask) {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
    }

    //@@author A0144902L
    /** Updates the completeTask in storage*/
    @Override
    public synchronized void completeTask(int index, ReadOnlyTask target) throws TaskNotFoundException {
        assert target != null;
        taskManager.completeTask(target);
        indicateTaskManagerChanged();
    }

    //@@author A0139926R
    /**
     * Stores current TaskManager state for undo and redo features
     */
    @Override
    public synchronized void storeTaskManager(String command) {

        StorageUtil.storeConfig(null);
        taskManagerHistory.push(new TaskManager(taskManager));
        redoTaskManagerHistory.clear();
        StorageUtil.clearRedoConfig();
    }
  //@@author A0139926R
    /**
     * Reverts changes made from restoring recently saved TaskManager state for redo command
     */
    @Override
    public synchronized int revertTaskManager() {
        if (StorageUtil.isRedoConfigHistoryEmpty() && redoTaskManagerHistory.isEmpty()) {
            return STATUS_EMPTY_HISTORY;
        } else if (!redoTaskManagerHistory.isEmpty() && redoTaskManagerHistory.peek() == null) {
            redoTaskManagerHistory.pop();
            taskManagerHistory.push(null);
            return STATUS_AVAILABLE_HISTORY;
        } else {
            TaskManager redoTaskManager = redoTaskManagerHistory.pop();
            taskManagerHistory.push(new TaskManager(taskManager));
            this.resetData(redoTaskManager);
            return STATUS_AVAILABLE_HISTORY;
        }
    }
  //@@author A0139926R
    /**
     * Restores recently saved TaskManager state for undo command
     */
    @Override
    public synchronized int restoreTaskManager() {
        if (StorageUtil.isConfigHistoryEmpty() && taskManagerHistory.isEmpty()) {
            return STATUS_EMPTY_HISTORY;
        } else if (!taskManagerHistory.isEmpty() && taskManagerHistory.peek() == null) {
            taskManagerHistory.pop();
            redoTaskManagerHistory.push(null);
            return STATUS_AVAILABLE_HISTORY;
        } else {
            TaskManager recentTaskManager = taskManagerHistory.pop();
            redoTaskManagerHistory.push(new TaskManager(taskManager));
            this.resetData(recentTaskManager);
            return STATUS_AVAILABLE_HISTORY;
        }

    }
  //@@author A0139926R
    /**
     * Restores configuration to the previous configuration
     * Not implemented yet
     */
    @Override
    public synchronized void rollBackTaskManager(boolean isStorageOperation) {

        taskManagerHistory.pop();
        if (isStorageOperation) {
            StorageUtil.undoConfig();
        }
    }
    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    //@@author A0144902L
    /** Filters to find tasks that are completed */
    @Override
    public void updateFilteredTaskList(boolean showComplete) {
        updateFilteredTaskList(new PredicateExpression(new CompleteQualifier(showComplete)));
    }
    /** Filters to find tasks that have high priority */
    @Override
    public void updateFilteredTaskList(Priority priority) {
        updateFilteredTaskList(new PredicateExpression(new PriorityQualifier(priority)));
    }
    //@@author A0139154E
    @Override
    public void updateFilteredTaskList(Calendar today) {
        updateFilteredTaskList(new PredicateExpression(new TodayQualifier(today)));
    }
    //@@author A0139926R
    /** Filters related dates for find command */
    @Override
    public void updateFilteredTaskList(String date) {
        updateFilteredTaskList(new PredicateExpression(new DateQualifier(date)));
    }
    //@@author A0139926R
    @Override
    public int getFilteredTaskListIndex(ReadOnlyTask targetTask) {
        return getFilteredTaskList().indexOf(targetTask);
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
    //@@author A0139926R
    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            boolean result = false;
            boolean date = false;
            boolean endDate = false;
            boolean name = false;
            date = nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getDate().value, keyword))
                    .findAny()
                    .isPresent();
            endDate = nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getEndDate().value, keyword))
                    .findAny()
                    .isPresent();
            name = nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
            if (date || endDate || name) {
                result = true;
            }
            return result;
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    //@@author A0144902L
    /** Examines if the task is qualified to be in list of completed tasks*/
    private class CompleteQualifier implements Qualifier {
        private boolean showComplete;

        CompleteQualifier(boolean showComplete) {
            this.showComplete = showComplete;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return (task.getIsCompleted() == showComplete);
        }

        @Override
        public String toString() {
            return "showComplete=" + String.valueOf(showComplete);
        }
    }
    //@@author A0139926R
    /** Examines if the task is qualified to be in list of tasks that consist similar date*/
    private class DateQualifier implements Qualifier {
        private String date;
        boolean result = false;
        DateQualifier(String date) {
            this.date = date;
        }
        @Override
        public boolean run(ReadOnlyTask task) {
            if (!task.getEndDate().value.equals("")) {
                result = task.getEndDate().value.contains(date);
                if (!task.getDate().value.equals("")) {
                    List<Date> endDates = DateParser.parse(task.getEndDate().value);
                    List<Date> startDates = DateParser.parse(task.getDate().value);
                    DateFormat format = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss");
                    Date keywordDate;
                    try {
                        keywordDate = format.parse(date);
                    } catch (ParseException e) {
                        return false;
                    }
                    Date taskEndDate = endDates.get(WANTED_DATE);
                    Date taskStartDate = startDates.get(WANTED_DATE);
                    if (keywordDate.before(taskEndDate) && keywordDate.after(taskStartDate)) {
                        result = true;
                    }
                    if (keywordDate.equals(taskStartDate)) {
                        result = true;
                    }
                }
            }
            return result;
        }
    }
    //@@author A0144902L
    /** Examines if the task is qualified to be in list of priority tasks*/
    private class PriorityQualifier implements Qualifier {
        private Priority priority;

        PriorityQualifier(Priority priority) {
            this.priority = priority;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return (task.getPriority().value.equals("High") && !task.getIsCompleted());
        }

        @Override
        public String toString() {
            return "showPriority=" + String.valueOf(priority);
        }
    }

    //@@author A0139154E
    /** Examines if the task is qualified to be in the list of today's tasks*/
    private class TodayQualifier implements Qualifier {
        private Calendar today;

        TodayQualifier(Calendar today) {
            this.today = today;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            boolean isEventDueToday = false;
            boolean isTaskDueToday = false;
            if (!task.getEndDate().value.equals("")) {
                Calendar taskEndDateCalendar = Calendar.getInstance();
                List<Date> endDates = DateParser.parse(task.getEndDate().value);
                Date taskEndDate = endDates.get(0);
                taskEndDateCalendar.setTime(taskEndDate);
                isTaskDueToday = (today.get(Calendar.YEAR) == taskEndDateCalendar.get(Calendar.YEAR) &&
                        today.get(Calendar.DAY_OF_YEAR) == taskEndDateCalendar.get(Calendar.DAY_OF_YEAR));

                if (!task.getDate().value.equals("")) {
                    Calendar taskStartDateCalendar = Calendar.getInstance();
                    List<Date> startDates = DateParser.parse(task.getDate().value);
                    Date taskStartDate = startDates.get(0);
                    taskStartDateCalendar.setTime(taskStartDate);
                    isEventDueToday = (taskStartDateCalendar.before(today) ||
                            (today.get(Calendar.YEAR) == taskStartDateCalendar.get(Calendar.YEAR) &&
                            today.get(Calendar.DAY_OF_YEAR) == taskStartDateCalendar.get(Calendar.DAY_OF_YEAR))) &&
                            (taskEndDateCalendar.after(today) ||
                                    (today.get(Calendar.YEAR) == taskEndDateCalendar.get(Calendar.YEAR) &&
                                    today.get(Calendar.DAY_OF_YEAR) == taskEndDateCalendar.get(Calendar.DAY_OF_YEAR)));
                }
            }

            return ((isTaskDueToday || isEventDueToday) && !task.getIsCompleted());
        }

        @Override
        public String toString() {
            return "showToday=" + today;
        }
    }

}
