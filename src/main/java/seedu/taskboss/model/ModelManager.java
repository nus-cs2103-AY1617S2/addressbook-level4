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
import seedu.taskboss.logic.commands.TerminateCommand;
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
    private static final String CATEGORY_ALL_TASKS = "Alltasks";

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBoss taskBoss;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final Stack<ReadOnlyTaskBoss> taskbossHistory;
    private final Stack<ReadOnlyTaskBoss> taskbossUndoHistory;
    private final Stack<String> undoInputList;
    private SortBy currentSortType;
    private String undoInput = null;

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
        undoInputList = new Stack<String>();
    }

    public ModelManager() throws IllegalValueException {
        this(new TaskBoss(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskBoss newData) throws IllegalValueException {
        taskbossHistory.push(new TaskBoss(this.taskBoss));
        taskBoss.resetData(newData);
        undoInputList.push("clear");
        indicateTaskBossChanged();
        taskbossUndoHistory.clear();
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

        indicateTaskBossChanged();
    }

    @Override
    public String undoTaskbossInput() throws IllegalValueException {
        undoInput = undoInputList.pop();

        return undoInput;
    }

    @Override
    public void redoTaskboss() throws EmptyStackException, IllegalValueException {
        TaskBoss previousTaskList = new TaskBoss(this.taskBoss);
        taskBoss.resetData(taskbossUndoHistory.pop());
        taskbossHistory.push(previousTaskList);
        undoInputList.push("redo");
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
        undoInputList.push("delete");
        indicateTaskBossChanged();
        taskbossUndoHistory.clear();
    }

    //@@author
    @Override
    public synchronized void addTask(Task task) throws IllegalValueException {
        taskbossHistory.push(new TaskBoss(this.taskBoss));
        taskBoss.addTask(task);
        taskBoss.sortTasks(currentSortType);
        updateFilteredListToShowAll();
        undoInputList.push("add");
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
        undoInputList.push("edit");
        indicateTaskBossChanged();
        taskbossUndoHistory.clear();
    }

    //@@author A0143157J
    @Override
    public void sortTasks(SortBy sortType) throws IllegalValueException {
        assert sortType != null;
        if (taskbossHistory != null) {
            taskbossHistory.push(new TaskBoss(this.taskBoss));
        }
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
                UniqueCategoryList newCategoryList = new UniqueCategoryList(task.getCategories());
                newCategoryList.add(new Category(CATEGORY_DONE));
                Task newTask = new Task(task.getName(), task.getPriorityLevel(),
                        task.getStartDateTime(), task.getEndDateTime(),
                        task.getInformation(), task.getRecurrence(),
                        newCategoryList);
                int taskBossIndex = filteredTasks.getSourceIndex(targetIndex);
                this.taskBoss.updateTask(taskBossIndex, newTask);
            } else {
                Task newRecurredTask = createRecurredTask(task);
                int taskBossIndex = filteredTasks.getSourceIndex(targetIndex);
                this.taskBoss.updateTask(taskBossIndex, newRecurredTask);
            }
            index++;
        }
        undoInputList.push("mark");
        indicateTaskBossChanged();
        taskbossUndoHistory.clear();
    }

    //@@author A0144904H
    @Override
    public void end(ArrayList<Integer> indices, ArrayList<ReadOnlyTask> tasksToMarkDone)
            throws IllegalValueException,
            CommandException {
        taskbossHistory.push(new TaskBoss(this.taskBoss));
        int index = 0;
        for (ReadOnlyTask task : tasksToMarkDone) {
            int targetIndex = indices.get(index) - 1;
            if (task.isRecurring()) {
                UniqueCategoryList newCategories = new UniqueCategoryList(task.getCategories());
                newCategories.add(Category.done);
                Task newTask = new Task(task.getName(), task.getPriorityLevel(),
                        task.getStartDateTime(), task.getEndDateTime(),
                        task.getInformation(), task.getRecurrence(),
                        newCategories);
                int taskBossIndex = filteredTasks.getSourceIndex(targetIndex);
                this.taskBoss.updateTask(taskBossIndex, newTask);
            } else {
                throw new CommandException(TerminateCommand.ERROR_TASK_NOT_RECURRING);
            }
            index++;
        }

        undoInputList.push("terminate");
        indicateTaskBossChanged();
        taskbossUndoHistory.clear();
    }

  //@@author A0144904H
    @Override
    public void unmarkTask(ArrayList<Integer> indices, ArrayList<ReadOnlyTask> tasksToMarkDone)
            throws IllegalValueException {
        taskbossHistory.push(new TaskBoss(this.taskBoss));
        int index = 0;
        for (ReadOnlyTask task : tasksToMarkDone) {
            int targetIndex = indices.get(index) - 1;
            if (!task.isRecurring()) {
                UniqueCategoryList newCategoryList = new UniqueCategoryList(task.getCategories());
                newCategoryList.remove(Category.done);
                Task newTask = new Task(task.getName(), task.getPriorityLevel(),
                        task.getStartDateTime(), task.getEndDateTime(),
                        task.getInformation(), task.getRecurrence(),
                        newCategoryList);
                int taskBossIndex = filteredTasks.getSourceIndex(targetIndex);
                this.taskBoss.updateTask(taskBossIndex, newTask);
            } else {
                Task newRecurredTask = createRecurredTaskForUnmarking(task);
                int taskBossIndex = filteredTasks.getSourceIndex(targetIndex);
                this.taskBoss.updateTask(taskBossIndex, newRecurredTask);
            }
            index++;
        }
        undoInputList.push("mark");
        indicateTaskBossChanged();
        taskbossUndoHistory.clear();
    }

    //@@author A0143157J
    @Override
    public void renameCategory(Category oldCategory, Category newCategory)
            throws IllegalValueException, CommandException, DuplicateCategoryException {
        assert oldCategory != null;
        taskbossHistory.push(new TaskBoss(this.taskBoss));
        taskBoss.renameCategory(newCategory, oldCategory);
        removeCategoryFromTaskboss(oldCategory);

        undoInputList.push("rename");
        indicateTaskBossChanged();
        taskbossUndoHistory.clear();
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

    /**
     * Returns a new recurred task with updated task dates according to the recurrence
     * of the given task and removes done category
     */
    private Task createRecurredTaskForUnmarking(ReadOnlyTask taskToUnmark)
            throws IllegalValueException {
        UniqueCategoryList newCategoryList = new UniqueCategoryList(taskToUnmark.getCategories());
        newCategoryList.remove(Category.done);
        Task newRecurredTask = new Task(taskToUnmark);
        newRecurredTask.getRecurrence().updateTaskDates(newRecurredTask);
        newRecurredTask.setCategories(newCategoryList);
        return newRecurredTask;
    }

    //=========== Filtered Task List Accessors =============================================================

    //@@author
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    //@@author A0147990R
    @Override
    public void updateFilteredListToShowAll() {
        try {
            updateFilteredTaskListByCategory(new Category(CATEGORY_ALL_TASKS));
        } catch (IllegalValueException e) {
            // Never reach here as CATEGORY_ALL_TASKS is a valid category name
            e.printStackTrace();
        }
    }

    @Override
    public void updateFilteredTaskListByKeywords(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new KeywordQualifier(keywords)));
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
     * if the category is not a build-in category
     **/
    public void removeCategoryFromTaskboss(Category category) {
        try {
            if (!category.equals(new Category(CATEGORY_ALL_TASKS)) &&
                    !category.equals(new Category(CATEGORY_DONE))) {
                taskBoss.removeCategory(category);
            }
        } catch (IllegalValueException ive) {
            //this exception should never be caught as CATEGORY_ALL_TASKS and CATEGORY_DONE
            // are always valid
        }
    }

    @Override
    /**
     * Check whether task boss contains the specified category
     **/
    public boolean hasCategory(Category t) {
        return taskBoss.hasCategory(t);
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

        //@@author A0144904H
        @Override
        public boolean run(ReadOnlyTask task) {
            if (categoryKeyWords.categoryName.equals(CATEGORY_DONE)) {
                return task.getCategories().contains(categoryKeyWords);
            } else {
                try {
                    return task.getCategories().contains(categoryKeyWords) &&
                            !task.getCategories().contains(new Category(CATEGORY_DONE));
                } catch (IllegalValueException e) {
                    // neven reach here as CATEGORY_DONE is a valid category name
                    e.printStackTrace();
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "category=" + categoryKeyWords.categoryName;
        }
    }
}
