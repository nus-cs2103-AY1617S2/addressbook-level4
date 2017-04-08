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
import seedu.tache.model.tag.Tag;
import seedu.tache.model.task.DateTime;
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
    public static final String TASK_LIST_TYPE_ALL = "All Tasks";
    public static final String TASK_LIST_TYPE_COMPLETED = "Completed Tasks";
    public static final String TASK_LIST_TYPE_UNCOMPLETED = "Uncompleted Tasks";
    public static final String TASK_LIST_TYPE_TIMED = "Timed Tasks";
    public static final String TASK_LIST_TYPE_FLOATING = "Floating Tasks";
    //@@author A0139925U
    public static final String TASK_LIST_TYPE_FOUND = "Found Tasks";
    //@@author A0139961U
    public static final String TASK_LIST_TYPE_DUE_TODAY = "Tasks Due Today";
    public static final String TASK_LIST_TYPE_DUE_THIS_WEEK = "Tasks Due This Week";
    public static final String TASK_LIST_TYPE_OVERDUE = "Overdue Tasks";
    //@@author
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    //@@author A0139925U
    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    private Set<String> latestKeywords;
    //@@author A0142255M
    private String filteredTaskListType = TASK_LIST_TYPE_ALL;
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
        assert newData != null;
        taskManager.resetData(newData);
        updateFilteredListToShowAll();
        updateFilteredTaskListType(TASK_LIST_TYPE_ALL);
        indicateTaskManagerChanged();
    }
    //@@author

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    //@@author A0142255M
    /**
     * Raises events to indicate that the model has changed.
     */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
        raise(new FilteredTaskListUpdatedEvent(getFilteredTaskList()));
        raise(new PopulateRecurringGhostTaskEvent(getAllUncompletedRecurringGhostTasks(),
                                getAllCompletedRecurringGhostTasks()));
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
        assert task != null;
        taskManager.addTask(task);
        indicateTaskManagerChanged();
    }

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
    //@@author A0133925U
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        ObservableList<ReadOnlyTask> filteredTasksWithRecurringTasks = populateRecurringDatesAsTask();
        raise(new TaskPanelConnectionChangedEvent(filteredTasksWithRecurringTasks));
        return new UnmodifiableObservableList<>(filteredTasksWithRecurringTasks);
    }

    //@@author A0142255M
    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
        updateFilteredTaskListType(TASK_LIST_TYPE_ALL);
        raise(new FilteredTaskListUpdatedEvent(getFilteredTaskList()));
    }

    //@@author A0139925U
    @Override
    public void updateFilteredListToShowUncompleted() {
        updateFilteredTaskListType(TASK_LIST_TYPE_UNCOMPLETED);
        updateFilteredTaskList(new PredicateExpression(new ActiveQualifier(true)));
    }

    @Override
    public void updateFilteredListToShowCompleted() {
        updateFilteredTaskListType(TASK_LIST_TYPE_COMPLETED);
        updateFilteredTaskList(new PredicateExpression(new ActiveQualifier(false)));
    }

    //@@author A0142255M
    @Override
    public void updateFilteredListToShowTimed() {
        updateFilteredTaskListType(TASK_LIST_TYPE_TIMED);
        updateFilteredTaskList(new PredicateExpression(new ActiveTimedQualifier(true)));
    }

    @Override
    public void updateFilteredListToShowFloating() {
        updateFilteredTaskListType(TASK_LIST_TYPE_FLOATING);
        updateFilteredTaskList(new PredicateExpression(new ActiveTimedQualifier(false)));
    }

    //@@author A0139961U
    @Override
    public void updateFilteredListToShowDueToday() {
        updateFilteredTaskListType(TASK_LIST_TYPE_DUE_TODAY);
        updateFilteredTaskList(new PredicateExpression(new DueTodayQualifier(true)));
    }

    public void updateFilteredListToShowDueThisWeek() {
        updateFilteredTaskListType(TASK_LIST_TYPE_DUE_THIS_WEEK);
        updateFilteredTaskList(new PredicateExpression(new DueThisWeekQualifier(true)));
    }

    public void updateFilteredListToShowOverdueTasks() {
        updateFilteredTaskListType(TASK_LIST_TYPE_OVERDUE);
        updateFilteredTaskList(new PredicateExpression(new OverdueQualifier()));
    }

    //@@author A0142255M
    /**
     * Provides functionality for find command and raises TaskListTypeChangedEvent to update UI.
     * Set<String> is converted to ArrayList<String> so that String can be retrieved.
     */
    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        assert keywords != null;
        updateFilteredTaskList(new PredicateExpression(new MultiQualifier(keywords)));
        updateFilteredTaskListType(TASK_LIST_TYPE_FOUND);
        retainLatestKeywords(keywords);
        raise(new TaskListTypeChangedEvent("Find \"" + StringUtil.generateStringFromKeywords(keywords) + "\""));
    }

    private void updateFilteredTaskList(Expression expression) {
        assert expression != null;
        filteredTasks.setPredicate(expression::satisfies);
        raise(new FilteredTaskListUpdatedEvent(getFilteredTaskList()));
    }

    @Override
    public String getFilteredTaskListType() {
        return filteredTaskListType;
    }

    private void updateFilteredTaskListType(String newFilteredTaskListType) {
        assert newFilteredTaskListType != null;
        assert !newFilteredTaskListType.equals("");
        if (!filteredTaskListType.equals(newFilteredTaskListType)) {
            raise(new TaskListTypeChangedEvent(newFilteredTaskListType));
        }
        filteredTaskListType = newFilteredTaskListType;
    }

    //@@author A0139925U
    private void retainLatestKeywords(Set<String> keywords) {
        latestKeywords = keywords;
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
    //@@author A0139925U
    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            String[] nameElements = task.getName().fullName.split(" ");
            boolean partialMatch = false;
            //Remove square brackets
            String trimmedNameKeywords = nameKeyWords.toString()
                                         .substring(1, nameKeyWords.toString().length() - 1).toLowerCase();
            for (int i = 0; i < nameElements.length; i++) {
                if (computeLevenshteinDistance(trimmedNameKeywords, nameElements[i].toLowerCase()) <= MARGIN_OF_ERROR) {
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

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    //@@author
    //@@author A0142255M
    private class TimedQualifier implements Qualifier {
        private boolean isTimed;

        TimedQualifier(boolean isTimed) {
            this.isTimed = isTimed;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            assert task != null;
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
                    return task.isWithinDate(DateTime.removeTime(new Date()));
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

    private class OverdueQualifier implements Qualifier {

        @Override
        public boolean run(ReadOnlyTask task) {
            if (task.getEndDateTime().isPresent() && task.getActiveStatus()) {
                return task.getEndDateTime().get().hasPassed();
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
        private TagQualifier tagQualifier;

        MultiQualifier(Set<String> multiKeyWords) {
            this.multiKeyWords = multiKeyWords;
            nameQualifier = new NameQualifier(multiKeyWords);
            dateTimeQualifier = new DateTimeQualifier(multiKeyWords);
            activeQualifier = new ActiveQualifier(true);
            tagQualifier = new TagQualifier(multiKeyWords);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return (nameQualifier.run(task) || dateTimeQualifier.run(task)
                        || tagQualifier.run(task)) && activeQualifier.run(task);
        }

        @Override
        public String toString() {
            return "multi=" + String.join(", ", multiKeyWords);
        }

    }

    private class TagQualifier implements Qualifier {
        private Set<String> tagKeywords;

        TagQualifier(Set<String> tagKeywords) {
            this.tagKeywords = tagKeywords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (task.getTags().toSet().size() != 0) {
                Set<Tag> tagElements = (Set<Tag>) task.getTags().toSet();
                boolean validMatch = false;
                //Remove square brackets
                String trimmedTagKeywords = tagKeywords.toString()
                                             .substring(1, tagKeywords.toString().length() - 1).toLowerCase();
                for (Tag tag: tagElements) {
                    if (computeLevenshteinDistance(trimmedTagKeywords, tag.tagName.toLowerCase())
                                    <= MARGIN_OF_ERROR) {
                        validMatch = true;
                        break;
                    }
                }
                return validMatch;
            }
            return false;
        }

        @Override
        public String toString() {
            return "tag=" + String.join(", ", tagKeywords);
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

    private ObservableList<ReadOnlyTask> populateRecurringDatesAsTask() {
        List<ReadOnlyTask> concatenated = new ArrayList<>();
        for (int i = 0; i < filteredTasks.size(); i++) {
            if (filteredTasks.get(i).getRecurState().isRecurring()) {
                if (filteredTaskListType.equals(TASK_LIST_TYPE_DUE_TODAY)) {
                    Collections.addAll(concatenated, filteredTasks.get(i)
                                                .getUncompletedRecurList(new Date()).toArray());
                } else if (filteredTaskListType.equals(TASK_LIST_TYPE_DUE_THIS_WEEK)) {
                    Calendar dateThisWeek = Calendar.getInstance();
                    dateThisWeek.setTime(new Date());
                    dateThisWeek.add(Calendar.WEEK_OF_YEAR, 1);
                    Collections.addAll(concatenated, filteredTasks.get(i)
                                                .getUncompletedRecurList(dateThisWeek.getTime()).toArray());
                } else {
                    Collections.addAll(concatenated, filteredTasks.get(i).getUncompletedRecurList().toArray());
                }
            }
        }
        if (filteredTaskListType.equals(TASK_LIST_TYPE_COMPLETED)) {
            for (int i = 0; i < taskManager.getTaskList().size(); i++) {
                if (taskManager.getTaskList().get(i).getRecurState().isRecurring()) {
                    if (taskManager.getTaskList().get(i).getActiveStatus()) {
                        Collections.addAll(concatenated, taskManager.getTaskList().get(i)
                                                .getCompletedRecurList().toArray());
                    } else {
                        Collections.addAll(concatenated, taskManager.getTaskList().get(i)
                                .getCompletedRecurList().toArray());
                    }
                }
            }
        }
        Collections.addAll(concatenated, filteredTasks.toArray());
        ObservableList<ReadOnlyTask> concatenatedList = FXCollections.observableList(concatenated);
        concatenatedList.sort(ReadOnlyTask.READONLYTASK_DATE_COMPARATOR);
        return concatenatedList;
    }

    public ObservableList<ReadOnlyTask> getAllUncompletedRecurringGhostTasks() {
        List<ReadOnlyTask> concatenated = new ArrayList<>();
        for (int i = 0; i < taskManager.getTaskList().size(); i++) {
            if (taskManager.getTaskList().get(i).getRecurState().isRecurring()) {
                Collections.addAll(concatenated, taskManager.getTaskList().get(i)
                                            .getUncompletedRecurList().toArray());
            }
        }
        ObservableList<ReadOnlyTask> concatenatedList = FXCollections.observableList(concatenated);
        concatenatedList.sort(ReadOnlyTask.READONLYTASK_DATE_COMPARATOR);
        return concatenatedList;
    }

    public ObservableList<ReadOnlyTask> getAllCompletedRecurringGhostTasks() {
        List<ReadOnlyTask> concatenated = new ArrayList<>();
        for (int i = 0; i < taskManager.getTaskList().size(); i++) {
            if (taskManager.getTaskList().get(i).getRecurState().isRecurring()) {
                Collections.addAll(concatenated, taskManager.getTaskList().get(i)
                                            .getCompletedRecurList().toArray());
            }
        }
        ObservableList<ReadOnlyTask> concatenatedList = FXCollections.observableList(concatenated);
        concatenatedList.sort(ReadOnlyTask.READONLYTASK_DATE_COMPARATOR);
        return concatenatedList;
    }
    //@@author A0150120H
    @Override
    public int getFilteredTaskListIndex(ReadOnlyTask targetTask) {
        return getFilteredTaskList().indexOf(targetTask);
    }

}
