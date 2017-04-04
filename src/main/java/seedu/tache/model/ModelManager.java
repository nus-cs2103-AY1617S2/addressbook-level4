package seedu.tache.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import edu.emory.mathcs.backport.java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.tache.commons.core.ComponentManager;
import seedu.tache.commons.core.LogsCenter;
import seedu.tache.commons.core.UnmodifiableObservableList;
import seedu.tache.commons.events.model.TaskManagerChangedEvent;
import seedu.tache.commons.events.ui.FilteredTaskListUpdatedEvent;
import seedu.tache.commons.events.ui.PopulateRecurringGhostTaskEvent;
import seedu.tache.commons.events.ui.TaskListTypeChangedEvent;
import seedu.tache.commons.events.ui.TaskPanelConnectionChangedEvent;
import seedu.tache.commons.util.CollectionUtil;
import seedu.tache.commons.util.StringUtil;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.UniqueTaskList;
import seedu.tache.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.tache.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    //@@author A0139925U
    public static final int MARGIN_OF_ERROR = 1;
    //@@author A0142255M
    public static final String ALL_TASK_LIST_TYPE = "All Tasks";
    public static final String COMPLETED_TASK_LIST_TYPE = "Completed Tasks";
    public static final String UNCOMPLETED_TASK_LIST_TYPE = "Uncompleted Tasks";
    public static final String TIMED_TASK_LIST_TYPE = "Timed Tasks";
    public static final String FLOATING_TASK_LIST_TYPE = "Floating Tasks";
  //@@author A0139925U
    public static final String FOUND_TASK_LIST_TYPE = "Found Tasks";
    //@@author A0139961U
    public static final String DUE_TODAY_TASK_LIST_TYPE = "Tasks Due Today";
    public static final String DUE_THIS_WEEK_TASK_LIST_TYPE = "Tasks Due This Week";
    //@@author
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    //@@author A0139925U
    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    private Set<String> latestKeywords;
    //@@author
    //@@author A0142255M
    private String filteredTaskListType = ALL_TASK_LIST_TYPE;
    //@@author

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

    //@@author A0142255M
    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        updateFilteredListToShowAll();
        updateFilteredTaskListType(ALL_TASK_LIST_TYPE);
        indicateTaskManagerChanged();
    }
    //@@author

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    //@@author A0142255M
    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
        raise(new FilteredTaskListUpdatedEvent(getFilteredTaskList()));
        raise(new PopulateRecurringGhostTaskEvent(getAllRecurringGhostTasks()));
    }
    //@@author

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    //@@author A0142255M
    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        taskManager.addTask(task);
        indicateTaskManagerChanged();
    }
    //@@author

    //@@author A0150120H
    @Override
    public synchronized void addTask(int index, Task task) throws DuplicateTaskException {
        taskManager.addTask(index, task);
        indicateTaskManagerChanged();
    }
    //@@author

    @Override
    public void updateTask(ReadOnlyTask taskToUpdate, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;
        taskManager.updateTask(taskToUpdate, editedTask);
        indicateTaskManagerChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        ObservableList<ReadOnlyTask> filteredTasksWithRecurringTasks = populateUncompletedRecurringDatesAsTask();
        raise(new TaskPanelConnectionChangedEvent(filteredTasksWithRecurringTasks));
        return new UnmodifiableObservableList<>(filteredTasksWithRecurringTasks);
    }

    //@@author A0142255M
    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
        raise(new FilteredTaskListUpdatedEvent(getFilteredTaskList()));
        updateFilteredTaskListType(ALL_TASK_LIST_TYPE);
    }
    //@@author

    //@@author A0139925U
    @Override
    public void updateFilteredListToShowUncompleted() {
        updateFilteredTaskList(new PredicateExpression(new ActiveQualifier(true)));
        updateFilteredTaskListType(UNCOMPLETED_TASK_LIST_TYPE);

    }

    @Override
    public void updateFilteredListToShowCompleted() {
        updateFilteredTaskList(new PredicateExpression(new ActiveQualifier(false)));
        updateFilteredTaskListType(COMPLETED_TASK_LIST_TYPE);
    }

    //@@author A0142255M
    @Override
    public void updateFilteredListToShowTimed() {
        updateFilteredTaskList(new PredicateExpression(new ActiveTimedQualifier(true)));
        updateFilteredTaskListType(TIMED_TASK_LIST_TYPE);
    }

    @Override
    public void updateFilteredListToShowFloating() {
        updateFilteredTaskList(new PredicateExpression(new ActiveTimedQualifier(false)));
        updateFilteredTaskListType(FLOATING_TASK_LIST_TYPE);
    }

    //@@author A0139961U
    @Override
    public void updateFilteredListToShowDueToday() {
        updateFilteredTaskListType(DUE_TODAY_TASK_LIST_TYPE);
        updateFilteredTaskList(new PredicateExpression(new DueTodayQualifier(true)));
    }

    public void updateFilteredListToShowDueThisWeek() {
        updateFilteredTaskListType(DUE_THIS_WEEK_TASK_LIST_TYPE);
        updateFilteredTaskList(new PredicateExpression(new DueThisWeekQualifier(true)));
    }

    //@@author A0142255M
    /**
     * Provides functionality for find command and raises TaskListTypeChangedEvent to update UI.
     * Set<String> is converted to ArrayList<String> so that String can be retrieved.
     */
    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new MultiQualifier(keywords)));
        ArrayList<String> keywordsList = new ArrayList<String>(keywords);
        updateFilteredTaskListType(FOUND_TASK_LIST_TYPE);
        retainLatestKeywords(keywords);
        raise(new TaskListTypeChangedEvent("Find \"" + keywordsList.get(0) + "\""));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
        raise(new FilteredTaskListUpdatedEvent(getFilteredTaskList()));
    }

    @Override
    public String getFilteredTaskListType() {
        return filteredTaskListType;
    }

    private void updateFilteredTaskListType(String newFilteredTaskListType) {
        if (!filteredTaskListType.equals(newFilteredTaskListType)) {
            raise(new TaskListTypeChangedEvent(newFilteredTaskListType));
        }
        filteredTaskListType = newFilteredTaskListType;
    }
    //@@author
    //@@author A0139925U
    private void retainLatestKeywords(Set<String> keywords) {
        latestKeywords = keywords;
    }

    public void updateCurrentFilteredList() {
        switch(filteredTaskListType) {
        case ALL_TASK_LIST_TYPE:
            updateFilteredListToShowAll();
            break;
        case COMPLETED_TASK_LIST_TYPE:
            updateFilteredListToShowCompleted();
            break;
        case UNCOMPLETED_TASK_LIST_TYPE:
            updateFilteredListToShowUncompleted();
            break;
        case TIMED_TASK_LIST_TYPE:
            updateFilteredListToShowTimed();
            break;
        case FLOATING_TASK_LIST_TYPE:
            updateFilteredListToShowFloating();
            break;
        case FOUND_TASK_LIST_TYPE:
            updateFilteredTaskList(latestKeywords);
            break;
        case DUE_TODAY_TASK_LIST_TYPE:
            updateFilteredListToShowDueToday();
            break;
        case DUE_THIS_WEEK_TASK_LIST_TYPE:
            updateFilteredListToShowDueThisWeek();
            break;
        default:
        }
    }
    //@@author

    //========== Inner classes/interfaces/methods used for filtering =================================================

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
        //@@author A0139925U
        @Override
        public boolean run(ReadOnlyTask task) {
            String[] nameElements = task.getName().fullName.split(" ");
            boolean partialMatch = false;
            String trimmedNameKeyWords = nameKeyWords.toString()
                                         .substring(1, nameKeyWords.toString().length() - 1).toLowerCase();
            for (int i = 0; i < nameElements.length; i++) {
                if (computeLevenshteinDistance(trimmedNameKeyWords, nameElements[i].toLowerCase()) <= MARGIN_OF_ERROR) {
                    partialMatch = true;
                    break;
                }
            }
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent()
                    || partialMatch;
        }
        //@@author
        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    //@@author A0142255M
    private class TimedQualifier implements Qualifier {
        private boolean isTimed;

        TimedQualifier(boolean isTimed) {
            this.isTimed = isTimed;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (isTimed) {
                return task.getTimedStatus();
            } else {
                return !task.getTimedStatus();
            }
        }

        @Override
        public String toString() {
            return "timed=" + isTimed;
        }
    }

    //@@author A0139925U
    private class ActiveQualifier implements Qualifier {
        private boolean isActive;

        ActiveQualifier(boolean isActive) {
            this.isActive = isActive;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (isActive) {
                return task.getActiveStatus();
            } else {
                return !task.getActiveStatus();
            }
        }

        @Override
        public String toString() {
            return "active=" + isActive;
        }
    }
    //@@author

    //@@author A0139961U
    private class DueTodayQualifier implements Qualifier {
        private boolean isDueToday;

        DueTodayQualifier(boolean isDueToday) {
            this.isDueToday = isDueToday;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (task.getEndDateTime().isPresent() && isDueToday) {
                if (task.getStartDateTime().isPresent()) {
                    return task.isWithinDate(new Date());
                }
                return task.getEndDateTime().get().isToday();
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return "dueToday=true";
        }
    }

    //@@author A0139961U
    private class DueThisWeekQualifier implements Qualifier {
        private boolean isDueThisWeek;

        DueThisWeekQualifier(boolean isDueThisWeek) {
            this.isDueThisWeek = isDueThisWeek;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (task.getEndDateTime().isPresent() && isDueThisWeek) {
                return task.getEndDateTime().get().isSameWeek();
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return "dueThisWeek=true";
        }
    }

    //@@author A0139925U
    private class DateTimeQualifier implements Qualifier {
        private Set<String> dateTimeKeyWords;

        DateTimeQualifier(Set<String> dateTimeKeyWords) {
            this.dateTimeKeyWords = dateTimeKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (task.getStartDateTime().isPresent()) {
                for (int i = 0; i < dateTimeKeyWords.size(); i++) {
                    if (dateTimeKeyWords.toArray()[i].equals(task.getStartDateTime().get().getDateOnly()) ||
                                dateTimeKeyWords.toArray()[i].equals(task.getStartDateTime().get().getTimeOnly())) {
                        return true;
                    }
                }
            }
            if (task.getEndDateTime().isPresent()) {
                for (int i = 0; i < dateTimeKeyWords.size(); i++) {
                    if (dateTimeKeyWords.toArray()[i].equals(task.getEndDateTime().get().getDateOnly()) ||
                                dateTimeKeyWords.toArray()[i].equals(task.getEndDateTime().get().getTimeOnly())) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "datetime=" + String.join(", ", dateTimeKeyWords);
        }

    }

    private class MultiQualifier implements Qualifier {
        private Set<String> multiKeyWords;
        private NameQualifier nameQualifier;
        private DateTimeQualifier dateTimeQualifier;
        private ActiveQualifier activeQualifier;

        MultiQualifier(Set<String> multiKeyWords) {
            this.multiKeyWords = multiKeyWords;
            nameQualifier = new NameQualifier(multiKeyWords);
            dateTimeQualifier = new DateTimeQualifier(multiKeyWords);
            activeQualifier = new ActiveQualifier(true);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return (nameQualifier.run(task) || dateTimeQualifier.run(task)) && activeQualifier.run(task);
        }

        @Override
        public String toString() {
            return "multi=" + String.join(", ", multiKeyWords);
        }

    }

    private class ActiveTimedQualifier implements Qualifier {
        private TimedQualifier timedQualifier;
        private ActiveQualifier activeQualifier;

        ActiveTimedQualifier(boolean isTimed) {
            timedQualifier = new TimedQualifier(isTimed);
            activeQualifier = new ActiveQualifier(true);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return timedQualifier.run(task) && activeQualifier.run(task);
        }

        @Override
        public String toString() {
            return "activetimed";
        }

    }

    private int computeLevenshteinDistance(CharSequence str1, CharSequence str2) {
        int[][] distance = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++) {
            distance[i][0] = i;
        }
        for (int j = 1; j <= str2.length(); j++) {
            distance[0][j] = j;
        }
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                distance[i][j] =
                 minimum(
                    distance[i - 1][j] + 1,
                    distance[i][j - 1] + 1,
                    distance[i - 1][j - 1] +
                        ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1));
            }
        }
        return distance[str1.length()][str2.length()];
    }

    private int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    private ObservableList<ReadOnlyTask> populateUncompletedRecurringDatesAsTask() {
        List<ReadOnlyTask> concatenated = new ArrayList<>();
        for (int i = 0; i < filteredTasks.size(); i++) {
            if (filteredTasks.get(i).getRecurringStatus()) {
                if (filteredTaskListType.equals(DUE_TODAY_TASK_LIST_TYPE)) {
                    Collections.addAll(concatenated, filteredTasks.get(i)
                                                .getUncompletedRecurList(new Date()).toArray());
                } else if (filteredTaskListType.equals(DUE_TODAY_TASK_LIST_TYPE)) {
                    Calendar dateThisWeek = Calendar.getInstance();
                    dateThisWeek.setTime(new Date());
                    dateThisWeek.add(Calendar.WEEK_OF_YEAR, 1);
                    Collections.addAll(concatenated, filteredTasks.get(i)
                                                .getUncompletedRecurList(dateThisWeek.getTime()).toArray());
                } else {
                    Collections.addAll(concatenated, filteredTasks.get(i).getUncompletedRecurList(null).toArray());
                }
            }
        }
        Collections.addAll(concatenated, filteredTasks.toArray());
        return FXCollections.observableList(concatenated);
    }

    public ObservableList<ReadOnlyTask> getAllRecurringGhostTasks() {
        List<ReadOnlyTask> concatenated = new ArrayList<>();
        for (int i = 0; i < taskManager.getTaskList().size(); i++) {
            if (taskManager.getTaskList().get(i).getRecurringStatus()) {
                Collections.addAll(concatenated, taskManager.getTaskList().get(i)
                                            .getUncompletedRecurList(null).toArray());
            }
        }
        return FXCollections.observableList(concatenated);
    }
    //@@author
}
