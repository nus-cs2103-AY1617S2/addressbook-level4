package seedu.geekeep.model;

import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.geekeep.commons.core.ComponentManager;
import seedu.geekeep.commons.core.LogsCenter;
import seedu.geekeep.commons.core.TaskCategory;
import seedu.geekeep.commons.core.UnmodifiableObservableList;
import seedu.geekeep.commons.events.model.SwitchTaskCategoryEvent;
import seedu.geekeep.commons.events.model.TaskManagerChangedEvent;
import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.commons.util.CollectionUtil;
import seedu.geekeep.commons.util.StringUtil;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.UniqueTaskList;
import seedu.geekeep.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the GeeKeep data. All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final Stack<TaskManager> pastTaskManagers;
    private final Stack<TaskManager> futureTaskManagers;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with GeeKeep: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        pastTaskManagers = new Stack<>();
        futureTaskManagers = new Stack<>();

    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        pastTaskManagers.add(new TaskManager(taskManager));
        futureTaskManagers.clear();
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
        pastTaskManagers.add(new TaskManager(taskManager));
        futureTaskManagers.clear();
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        pastTaskManagers.add(new TaskManager(taskManager));
        futureTaskManagers.clear();
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException, IllegalValueException {
        assert editedTask != null;

        pastTaskManagers.add(new TaskManager(taskManager));
        futureTaskManagers.clear();
        int taskListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskListIndex, editedTask);

        indicateTaskManagerChanged();
    }

    // =========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
        raise(new SwitchTaskCategoryEvent(TaskCategory.ALL));
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new TitleQualifier(keywords)));
        raise(new SwitchTaskCategoryEvent(TaskCategory.ALL));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    // ========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);

        @Override
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

        @Override
        String toString();
    }

    private class TitleQualifier implements Qualifier {
        private Set<String> titleKeyWords;

        TitleQualifier(Set<String> nameKeyWords) {
            this.titleKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return titleKeyWords.stream()
                    .filter(keyword-> StringUtil.containsWordIgnoreCase(task.getTitle().title, keyword))
                    .findAny().isPresent();
        }

        @Override
        public String toString() {
            return "title=" + String.join(", ", titleKeyWords);
        }
    }

    @Override
    public void markTaskDone(int filteredTaskListIndex) {
        pastTaskManagers.add(new TaskManager(taskManager));
        futureTaskManagers.clear();
        int taskListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.markTaskDone(taskListIndex);
        indicateTaskManagerChanged();
    }

    @Override
    public void markTaskUndone(int filteredTaskListIndex) {
        pastTaskManagers.add(new TaskManager(taskManager));
        futureTaskManagers.clear();
        int taskListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.markTaskUndone(taskListIndex);
        indicateTaskManagerChanged();
    }

    @Override
    public void updateFilteredTaskListToShowDone() {
        filteredTasks.setPredicate(t -> t.isDone());
        raise(new SwitchTaskCategoryEvent(TaskCategory.DONE));
    }

    @Override
    public void updateFilteredTaskListToShowUndone() {
        filteredTasks.setPredicate(t -> !t.isDone());
        raise(new SwitchTaskCategoryEvent(TaskCategory.UNDONE));
    }

    @Override
    public void updateFilteredTaskListToShowEvents() {
        filteredTasks.setPredicate(t -> t.isEvent());

    }

    @Override
    public void updateFilteredTaskListToShowDeadlines() {
        filteredTasks.setPredicate(t -> t.isDeadline());

    }

    @Override
    public void updateFilteredTaskListToShowFloatingTasks() {
        filteredTasks.setPredicate(t -> t.isFloatingTask());

    }

    @Override
    public void undo() throws NothingToUndoException {
        if (pastTaskManagers.empty()) {
            throw new NothingToUndoException();
        }
        futureTaskManagers.push(new TaskManager(taskManager));
        taskManager.resetData(pastTaskManagers.pop());
        indicateTaskManagerChanged();
    }

    @Override
    public void redo() throws NothingToRedoException {
        if (futureTaskManagers.empty()) {
            throw new NothingToRedoException();
        }
        pastTaskManagers.push(new TaskManager(taskManager));
        taskManager.resetData(futureTaskManagers.pop());
        indicateTaskManagerChanged();
    }

}
