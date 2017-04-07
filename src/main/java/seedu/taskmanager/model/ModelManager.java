package seedu.taskmanager.model;

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
    private final FilteredList<ReadOnlyTask> filteredTasks;
    // @@author A0114523U
    private final FilteredList<ReadOnlyTask> filteredOverdueTasks;
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
        // @@author A0114523U
        filteredOverdueTasks = new FilteredList<>(this.taskManager.getTaskList());
        // @@author
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
        Logic logic = LogicManager.getInstance();
        raise(new TaskManagerChangedEvent(taskManager, logic.getCommandText()));
    }

    // @@author A0131278H
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
        int index = taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
        // @@author A0131278H
        indicateJumpToListRequestEvent(index);
        // @@author
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        int updatedIndex = taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
        // @@author A0131278H
        indicateJumpToListRequestEvent(updatedIndex);
        // @@author
    }

    // @@author A0131278H
    @Override
    public void sortTasks(String keyword) {
        taskManager.sortByDate(keyword);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    // @@author

    // =========== Filtered Task List Accessors
    // =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    // @@author A0114523U
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredOverdueTaskList() {
        return new UnmodifiableObservableList<>(filteredOverdueTasks);
    }
    // @@author

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

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
        private Date startDateCriteria, endDateCriteria;

        DateRangeQualifier(Date startDateCriteria, Date endDateCriteria) {
            this.startDateCriteria = startDateCriteria;
            this.endDateCriteria = endDateCriteria;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            boolean isFloatingTask = !(task.getStartDate().isPresent() || task.getEndDate().isPresent());
            if (isFloatingTask) {
                return false;
            }
            if (task.getStartDate().isPresent() && task.getEndDate().isPresent()) {
                return !(task.getStartDate().get().before(startDateCriteria)
                        || task.getEndDate().get().after(endDateCriteria));
            }
            if (task.getStartDate().isPresent()) {
                return !(task.getStartDate().get().before(startDateCriteria)
                        || task.getStartDate().get().after(startDateCriteria));
            }
            return !(task.getEndDate().get().before(startDateCriteria)
                    || task.getStartDate().get().after(startDateCriteria));
        }

        @Override
        public String toString() {
            return "date range=" + startDateCriteria.toString() + " to " + endDateCriteria.toString();
        }
    }
    // @@author

}
