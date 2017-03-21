package werkbook.task.model;

import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import werkbook.task.commons.core.ComponentManager;
import werkbook.task.commons.core.LogsCenter;
import werkbook.task.commons.core.UnmodifiableObservableList;
import werkbook.task.commons.events.model.TaskListChangedEvent;
import werkbook.task.commons.util.CollectionUtil;
import werkbook.task.commons.util.StringUtil;
import werkbook.task.model.task.ReadOnlyTask;
import werkbook.task.model.task.Task;
import werkbook.task.model.task.UniqueTaskList;
import werkbook.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskList taskList;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private static Stack<TaskList> undoStack, redoStack;

    /**
     * Initializes a ModelManager with the given taskList and userPrefs.
     */
    public ModelManager(ReadOnlyTaskList taskList, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskList, userPrefs);

        logger.fine("Initializing with task list: " + taskList + " and user prefs " + userPrefs);

        this.taskList = new TaskList(taskList);
        filteredTasks = new FilteredList<>(this.taskList.getTaskList());
        undoStack = new Stack<TaskList>();
        redoStack = new Stack<TaskList>();
    }

    public ModelManager() {
        this(new TaskList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskList newData) {
        undoStack.push(new TaskList(taskList));
        redoStack.clear();
        taskList.resetData(newData);
        indicateTaskListChanged();
    }

    @Override
    public ReadOnlyTaskList getTaskList() {
        return taskList;
    }

    @Override
    public void indicateTaskListChanged() {
        raise(new TaskListChangedEvent(taskList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        undoStack.push(new TaskList(taskList));
        redoStack.clear();
        taskList.removeTask(target);
        indicateTaskListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        undoStack.push(new TaskList(taskList));
        redoStack.clear();
        taskList.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskListChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        undoStack.push(new TaskList(taskList));
        redoStack.clear();
        int addressBookIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskList.updateTask(addressBookIndex, editedTask);
        indicateTaskListChanged();
    }

    @Override
    public void undo() throws EmptyStackException {
        if (undoStack.isEmpty()) {
            throw new EmptyStackException();
        }
        redoStack.push(new TaskList(taskList));
        taskList.resetData(undoStack.pop());
        indicateTaskListChanged();
    }

    @Override
    public void redo() throws EmptyStackException {
        if (redoStack.isEmpty()) {
            throw new EmptyStackException();
        }
        undoStack.push(new TaskList(taskList));
        taskList.resetData(redoStack.pop());
        indicateTaskListChanged();
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
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
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
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().taskName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
