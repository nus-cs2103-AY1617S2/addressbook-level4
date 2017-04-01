package seedu.taskboss.model;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
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
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.category.UniqueCategoryList.DuplicateCategoryException;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList.SortBy;
import seedu.taskboss.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the TaskBoss data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final String CATEGORY_DONE = "Done";

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBoss taskBoss;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final Stack<ReadOnlyTaskBoss> taskbossHistory;
    private final Stack<ReadOnlyTaskBoss> taskbossUndoHistory;
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
        taskbossUndoHistory = new Stack<ReadOnlyTaskBoss>();
    }

    public ModelManager() throws IllegalValueException {
        this(new TaskBoss(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskBoss newData) throws IllegalValueException {
        taskbossHistory.push(new TaskBoss(this.taskBoss));
        taskBoss.resetData(newData);

        indicateTaskBossChanged();
    }

    @Override
    public ReadOnlyTaskBoss getTaskBoss() {
        return taskBoss;
    }

    //@@author A0138961W
    @Override
    public void undoTaskboss() throws EmptyStackException, IllegalValueException {
        TaskBoss currentTaskList = new TaskBoss(this.taskBoss);
        taskBoss.resetData(taskbossHistory.pop());
        taskbossUndoHistory.push(currentTaskList);
        updateFilteredListToShowAll();

        indicateTaskBossChanged();
    }

    @Override
    public void redoTaskboss() throws EmptyStackException, IllegalValueException {
        TaskBoss previousTaskList = new TaskBoss(this.taskBoss);
        taskBoss.resetData(taskbossUndoHistory.pop());
        taskbossHistory.push(previousTaskList);
        updateFilteredListToShowAll();

        indicateTaskBossChanged();
    }

    @Override
    public void saveTaskboss() {
        indicateTaskBossChanged();
    }

    //@@author
    /** Raises an event to indicate the model has changed */
    private void indicateTaskBossChanged() {
        raise(new TaskBossChangedEvent(taskBoss));
    }

    //@@author A0138961W
    @Override
    public synchronized void deleteTask(List<ReadOnlyTask> targets) throws TaskNotFoundException,
            IllegalValueException {

        taskbossHistory.push(new TaskBoss(this.taskBoss));

        for (ReadOnlyTask target: targets) {
            taskBoss.removeTask(target);
        }
        indicateTaskBossChanged();
    }

    //@@author
    @Override
    public synchronized void addTask(Task task) throws IllegalValueException {
        taskbossHistory.push(new TaskBoss(this.taskBoss));
        taskBoss.addTask(task);
        taskBoss.sortTasks(currentSortType);
        updateFilteredListToShowAll();
        indicateTaskBossChanged();
        taskbossUndoHistory.clear();
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

    //@@author A0143157J
    @Override
    public void sortTasks(SortBy sortType) throws IllegalValueException {
        assert sortType != null;
        this.currentSortType = sortType;
        taskBoss.sortTasks(sortType);

        indicateTaskBossChanged();
    }

    //@@author A0144904H
    @Override
    public void markDone(ArrayList<Integer> indices, ArrayList<ReadOnlyTask> tasksToMarkDone)
                                                                        throws IllegalValueException {
        taskbossHistory.push(new TaskBoss(this.taskBoss));
        int index = 0;
        for (ReadOnlyTask task : tasksToMarkDone) {
            int targetIndex = indices.get(index) - 1;
            if (!task.isRecurring()) {
                Task newTask = new Task(task.getName(), task.getPriorityLevel(),
                        task.getStartDateTime(), task.getEndDateTime(),
                        task.getInformation(), task.getRecurrence(),
                        new UniqueCategoryList(CATEGORY_DONE));
                this.taskBoss.updateTask(targetIndex, newTask);
            } else {
                Task newRecurredTask = createRecurredTask(task);
                this.taskBoss.updateTask(targetIndex, newRecurredTask);
            }
            index++;
        }

        indicateTaskBossChanged();
    }

    //@@author A0143157J
    @Override
    public void renameCategory(Category oldCategory, Category newCategory)
            throws IllegalValueException, CommandException, DuplicateCategoryException {
        assert oldCategory != null;
        taskbossHistory.push(new TaskBoss(this.taskBoss));
        taskBoss.renameCategory(newCategory, oldCategory);
        removeCategoryFromTaskboss(oldCategory);
        taskbossUndoHistory.clear();
        indicateTaskBossChanged();
    }

    /**
     * Returns a new recurred task with updated task dates according to the recurrence
     * of the given task
     */
    private Task createRecurredTask(ReadOnlyTask taskToMarkDone) throws IllegalValueException {
        Task newRecurredTask = new Task(taskToMarkDone);
        newRecurredTask.getRecurrence().updateTaskDates(newRecurredTask);
        return newRecurredTask;
    }

    //=========== Filtered Task List Accessors =============================================================

    //@@author
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskListByKeywords(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new KeywordQualifier(keywords)));
    }

    //@@author A0147990R
    @Override
    public void updateFilteredTaskListByStartDateTime(String keywords) {
        updateFilteredTaskList(new PredicateExpression(new StartDatetimeQualifier(keywords)));
    }

    @Override
    public void updateFilteredTaskListByEndDateTime(String keywords) {
        updateFilteredTaskList(new PredicateExpression(new EndDatetimeQualifier(keywords)));
    }

    @Override
    public void updateFilteredTaskListByCategory(Category category) {
        updateFilteredTaskList(new PredicateExpression(new CategoryQualifier(category)));
    }

    //@@author
    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //@@author A0147990R
    @Override
    public void clearTasksByCategory(Category category) throws IllegalValueException {
        taskbossHistory.push(new TaskBoss(this.taskBoss));
        FilteredList<ReadOnlyTask> taskListWithCategory = filteredTasks;
        int listSize = taskListWithCategory.size();
        int i;
        for (i = 0; i < listSize; i++) {
            ReadOnlyTask target = taskListWithCategory.get(0);
            try {
                taskBoss.removeTask(target);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
        }
        updateFilteredListToShowAll();
        removeCategoryFromTaskboss(category);
        indicateTaskBossChanged();
    }

    /**
     * Removes the category from the UniqueCategoryList of Taskboss
     **/
    public void removeCategoryFromTaskboss(Category category) {
        taskBoss.removeCategory(category);
    }

    //========== Inner classes/interfaces used for filtering =================================================
    //@@author
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

    private class KeywordQualifier implements Qualifier {
        private Set<String> keyWords;

        KeywordQualifier(Set<String> keyWords) {
            this.keyWords = keyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return keyWords.stream()
                    .filter(keyword -> StringUtil
                            .containsWordIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent() ||
                    keyWords.stream()
                    .filter(keyword -> StringUtil
                            .containsWordIgnoreCase(task.getInformation().value, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", keyWords);
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
