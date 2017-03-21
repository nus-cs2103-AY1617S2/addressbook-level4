package seedu.taskboss.model;

import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.taskboss.commons.core.ComponentManager;
import seedu.taskboss.commons.core.LogsCenter;
import seedu.taskboss.commons.core.UnmodifiableObservableList;
import seedu.taskboss.commons.events.model.TaskBossChangedEvent;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.commons.util.CollectionUtil;
import seedu.taskboss.commons.util.StringUtil;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList.SortBy;
import seedu.taskboss.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the TaskBoss data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBoss taskBoss;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final Stack<ReadOnlyTaskBoss> taskbossHistory;
    private SortBy currentSortType;

    /**
     * Initializes a ModelManager with the given TaskBoss and userPrefs.
     * @throws IllegalValueException
     */
    public ModelManager(ReadOnlyTaskBoss taskBoss, UserPrefs userPrefs) throws IllegalValueException {
        super();
        assert !CollectionUtil.isAnyNull(taskBoss, userPrefs);

        logger.fine("Initializing with TaskBoss: " + taskBoss + " and user prefs " + userPrefs);

        this.taskBoss = new TaskBoss(taskBoss);
        // sort by end date time by default
        currentSortType = SortBy.END_DATE_TIME;
        sortTasks(currentSortType);
        filteredTasks = new FilteredList<>(this.taskBoss.getTaskList());
        taskbossHistory = new Stack<ReadOnlyTaskBoss>();
    }

    public ModelManager() throws IllegalValueException {
        this(new TaskBoss(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskBoss newData) {
        taskbossHistory.push(new TaskBoss(this.taskBoss));
        taskBoss.resetData(newData);
        indicateTaskBossChanged();
    }

    @Override
    public ReadOnlyTaskBoss getTaskBoss() {
        return taskBoss;
    }

    @Override
    public void undoTaskboss() throws EmptyStackException {
        taskBoss.resetData(taskbossHistory.pop());
        indicateTaskBossChanged();
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskBossChanged() {
        raise(new TaskBossChangedEvent(taskBoss));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskbossHistory.push(new TaskBoss(this.taskBoss));
        taskBoss.removeTask(target);
        indicateTaskBossChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws IllegalValueException {
        taskbossHistory.push(new TaskBoss(this.taskBoss));
        taskBoss.addTask(task);
        taskBoss.sortTasks(currentSortType);
        updateFilteredListToShowAll();
        indicateTaskBossChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws IllegalValueException {
        assert editedTask != null;
        taskbossHistory.push(new TaskBoss(this.taskBoss));
        int taskBossIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskBoss.updateTask(taskBossIndex, editedTask);
        taskBoss.sortTasks(currentSortType);
        indicateTaskBossChanged();
    }

    @Override
    public void sortTasks(SortBy sortType) throws IllegalValueException {
        assert sortType != null;
        this.currentSortType = sortType;
        taskBoss.sortTasks(sortType);
        indicateTaskBossChanged();
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
    public void updateFilteredTaskListByName(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    @Override
    public void updateFilteredTaskListByStartDateTime(String keywords) {
        updateFilteredTaskList(new PredicateExpression(new StartDatetimeQualifier(keywords)));
    }

    @Override
    public void updateFilteredTaskListByEndDateTime(String keywords) {
        updateFilteredTaskList(new PredicateExpression(new EndDatetimeQualifier(keywords)));
    }

    @Override
    public void updateFilteredTaskListByInformation(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new InformationQualifier(keywords)));
    }

    @Override
    public void updateFilteredTaskListByCategory(Category category) {
        updateFilteredTaskList(new PredicateExpression(new CategoryQualifier(category)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
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
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    //@@author A0147990R
    private class StartDatetimeQualifier implements Qualifier {
        private String startDateKeyWords;

        StartDatetimeQualifier(String startDateKeyWords) {
            this.startDateKeyWords = startDateKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getStartDateTime().toString().contains(startDateKeyWords) ||
                    task.getStartDateTime().toString().toLowerCase().contains(startDateKeyWords);
        }

        @Override
        public String toString() {
            return "startDateTime=" + startDateKeyWords;
        }
    }

    //@@author A0147990R
    private class EndDatetimeQualifier implements Qualifier {
        private String endDateKeyWords;

        EndDatetimeQualifier(String endDateKeyWords) {
            this.endDateKeyWords = endDateKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getEndDateTime().toString().contains(endDateKeyWords) ||
                    task.getEndDateTime().toString().toLowerCase().contains(endDateKeyWords);
        }

        @Override
        public String toString() {
            return "endDateTime=" + endDateKeyWords;
        }
    }

    //@@author A0147990R
    private class InformationQualifier implements Qualifier {
        private Set<String> informationKeyWords;

        InformationQualifier(Set<String> informationKeyWords) {
            this.informationKeyWords = informationKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return informationKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(
                            task.getInformation().value, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "information=" + String.join(", ", informationKeyWords);
        }
    }

    //@@author A0147990R
    private class CategoryQualifier implements Qualifier {
        private Category categoryKeyWords;

        CategoryQualifier(Category categoryKeyWords) {
            this.categoryKeyWords = categoryKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getCategories().contains(categoryKeyWords);
        }

        @Override
        public String toString() {
            return "category=" + categoryKeyWords.categoryName;
        }
    }

}
