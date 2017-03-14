package seedu.tasklist.model;

import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;


import javafx.collections.transformation.FilteredList;
import seedu.tasklist.commons.core.ComponentManager;
import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.commons.events.model.TaskListChangedEvent;
import seedu.tasklist.commons.exceptions.EmptyModelStackException;
import seedu.tasklist.commons.util.CollectionUtil;
import seedu.tasklist.commons.util.StringUtil;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList;
import seedu.tasklist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskList taskList;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private Stack<ReadOnlyTaskList> undoStack;
    private Stack<ReadOnlyTaskList> redoStack;

    /**
     * Initializes a ModelManager with the given taskList and userPrefs.
     */
    public ModelManager(ReadOnlyTaskList taskList, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskList, userPrefs);

        logger.fine("Initializing with address book: " + taskList + " and user prefs " + userPrefs);

        this.taskList = new TaskList(taskList);
        filteredTasks = new FilteredList<>(this.taskList.getTaskList());

        this.undoStack = new Stack<ReadOnlyTaskList>();
        this.redoStack = new Stack<ReadOnlyTaskList>();
    }

    public ModelManager() {
        this(new TaskList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskList newData) {
        taskList.resetData(newData);
        indicateTaskListChanged();
    }

    @Override
    public ReadOnlyTaskList getTaskList() {
        return taskList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskListChanged() {
        raise(new TaskListChangedEvent(taskList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        undoStack.push(new TaskList(taskList));
        taskList.removeTask(target);
        indicateTaskListChanged();


    }

    @Override
    public synchronized void addTask(Task person) throws UniqueTaskList.DuplicateTaskException {
        undoStack.push(new TaskList(taskList));
        taskList.addTask(person);
        updateFilteredListToShowAll();
        indicateTaskListChanged();


    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;
        undoStack.push(new TaskList(taskList));
        int taskListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskList.updateTask(taskListIndex, editedTask);
        indicateTaskListChanged();

    }

    @Override
    public void setPreviousState() throws EmptyUndoRedoStackException {
        if (undoStack.empty()) {
            throw new EmptyUndoRedoStackException();
        }
        redoStack.push(new TaskList(taskList));
        ReadOnlyTaskList previousState = undoStack.pop();
        taskList.resetData(previousState);
        updateFilteredListToShowAll();
    }

    @Override
    public void setNextState() throws EmptyUndoRedoStackException {
        if (redoStack.empty()) {
            throw new EmptyUndoRedoStackException();
        }
        undoStack.push(new TaskList(taskList));
        ReadOnlyTaskList nextState = redoStack.pop();
        taskList.resetData(nextState);
        updateFilteredListToShowAll();
    }

    @Override
    public void enableUndoForClear() {
        undoStack.push(new TaskList(taskList));
    }
    public static class EmptyUndoRedoStackException extends EmptyModelStackException {
        protected EmptyUndoRedoStackException() {
            super("No available states");
        }
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
        boolean satisfies(ReadOnlyTask person);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask person) {
            return qualifier.run(person);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask person);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask person) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
