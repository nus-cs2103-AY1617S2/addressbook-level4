package seedu.taskmanager.model;

import static seedu.taskmanager.ui.MainWindow.TAB_DONE;
import static seedu.taskmanager.ui.MainWindow.TAB_TO_DO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.taskmanager.commons.core.ComponentManager;
import seedu.taskmanager.commons.core.EventsCenter;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.commons.events.model.TaskManagerChangedEvent;
import seedu.taskmanager.commons.events.ui.JumpToListRequestEvent;
import seedu.taskmanager.commons.util.CollectionUtil;
import seedu.taskmanager.commons.util.StringUtil;
import seedu.taskmanager.logic.Logic;
import seedu.taskmanager.logic.LogicManager;
import seedu.taskmanager.model.tag.Tag;
import seedu.taskmanager.model.tag.UniqueTagList;
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

    private final TaskManager taskManager;
    // @@author A0131278H
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final FilteredList<ReadOnlyTask> filteredToDoTasks;
    private final FilteredList<ReadOnlyTask> filteredDoneTasks;
    private String selectedTab;
    // @@author

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task managerk: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        // @@author A0131278H
        filteredToDoTasks = new FilteredList<>(this.taskManager.getToDoTaskList());
        filteredDoneTasks = new FilteredList<>(this.taskManager.getDoneTaskList());
        setSelectedTab(TAB_TO_DO); // default tab is to-do task list tab
        // @@author
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    // @@author A0131278H
    @Override
    public String getSelectedTab() {
        return selectedTab;
    }

    @Override
    public void setSelectedTab(String currentTab) {
        this.selectedTab = currentTab;
    }
    // @@author

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
        Logic logic = LogicManager.getInstance();
        raise(new TaskManagerChangedEvent(taskManager, logic.getCommandText()));
    }

    // @@author A0131278H
    /** Raises an event to indicate an automatic selection */
    private void indicateJumpToListRequestEvent(int index) {
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
    }
    // @@author

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
        // @@author A0131278H
        ReadOnlyTask taskToEdit = getSelectedTaskList().get(filteredTaskListIndex);
        int indexInFullList = filteredTasks.indexOf(taskToEdit);
        int taskManagerIndex = filteredTasks.getSourceIndex(indexInFullList);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
    }

    @Override
    public void sortTasks(String keyword) {
        taskManager.sortByDate(keyword);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public void highlightTask(ReadOnlyTask task) {
        int targetIndex = getSelectedTaskList().indexOf(task);
        indicateJumpToListRequestEvent(targetIndex);
    }
    // @@author

    // =========== Filtered Task List Accessors
    // =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    // @@author A0131278H

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getSelectedTaskList() {
        switch (selectedTab) {
        case TAB_TO_DO:
            return getFilteredToDoTaskList();
        case TAB_DONE:
            return getFilteredDoneTaskList();
        default:
            assert false : selectedTab + " is invalid";
            return null;
        }
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredToDoTaskList() {
        return new UnmodifiableObservableList<>(filteredToDoTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDoneTaskList() {
        return new UnmodifiableObservableList<>(filteredDoneTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
        filteredToDoTasks.setPredicate(null);
        filteredDoneTasks.setPredicate(null);
    }
    // @@author

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    // @@author A0140032E
    @Override
    public void updateFilteredTaskList(Date date) {
        updateFilteredTaskList(new PredicateExpression(new DateQualifier(date)));

    }

    @Override
    public void updateFilteredTaskList(Date startDateCriteria, Date endDateCriteria) {
        updateFilteredTaskList(new PredicateExpression(new DateRangeQualifier(startDateCriteria, endDateCriteria)));

    }
    // @@author

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
        // @@author A0131278H
        filteredToDoTasks.setPredicate(expression::satisfies);
        filteredDoneTasks.setPredicate(expression::satisfies);
    }
    // @@author

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

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        // @@author A0140032E
        public boolean run(ReadOnlyTask task) {
            boolean hasName = nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getTitle().value, keyword)).findAny()
                    .isPresent();
            boolean hasDescription = nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(
                            task.getDescription().isPresent() ? task.getDescription().get().value : "", keyword))
                    .findAny().isPresent();
            boolean hasTag = false;
            UniqueTagList tagList = task.getTags();
            for (Tag tag : tagList) {
                hasTag = hasTag || nameKeyWords.stream()
                        .filter(keyword -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword)).findAny()
                        .isPresent();
            }
            return hasName || hasDescription || hasTag;
        }
        // @@author

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    // @@author A0140032E
    private class DateQualifier implements Qualifier {
        private LocalDate date;

        DateQualifier(Date date) {
            this.date = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        @Override
        public boolean run(ReadOnlyTask task) {

            LocalDate taskStartDate = task.getStartDate().isPresent()
                    ? task.getStartDate().get().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
            LocalDate taskEndDate = task.getEndDate().isPresent()
                    ? task.getEndDate().get().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;

            boolean isFloatingTask = !(task.getStartDate().isPresent() || task.getEndDate().isPresent());
            if (isFloatingTask) {
                return false;
            }
            if (task.getStartDate().isPresent() && task.getEndDate().isPresent()) {

                return !(taskStartDate.isAfter(date) || taskEndDate.isBefore(date));
            }
            if (task.getStartDate().isPresent()) {
                return !(taskStartDate.isAfter(date));
            }
            return !(taskEndDate.isBefore(date));
        }

        @Override
        public String toString() {
            return "date=" + date.toString();
        }
    }

    private class DateRangeQualifier implements Qualifier {
        private LocalDate startDateCriteria, endDateCriteria;

        DateRangeQualifier(Date startDateCriteria, Date endDateCriteria) {
            this.startDateCriteria = startDateCriteria.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            this.endDateCriteria = endDateCriteria.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            LocalDate taskStartDate = task.getStartDate().isPresent()
                    ? task.getStartDate().get().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
            LocalDate taskEndDate = task.getEndDate().isPresent()
                    ? task.getEndDate().get().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;

            boolean isFloatingTask = !(task.getStartDate().isPresent() || task.getEndDate().isPresent());
            if (isFloatingTask) {
                return false;
            } else if (task.getStartDate().isPresent() && task.getEndDate().isPresent()) {
                return !(taskStartDate.isBefore(startDateCriteria) || taskEndDate.isAfter(endDateCriteria));
            } else if (task.getStartDate().isPresent()) {
                return !(taskStartDate.isBefore(startDateCriteria) || taskStartDate.isAfter(startDateCriteria));
            } else {
                return !(taskEndDate.isBefore(startDateCriteria) || taskEndDate.isAfter(startDateCriteria));
            }
        }

        @Override
        public String toString() {
            return "date range=" + startDateCriteria.toString() + " to " + endDateCriteria.toString();
        }
    }
    // @@author
}
