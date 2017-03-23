package seedu.onetwodo.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.onetwodo.commons.core.ComponentManager;
import seedu.onetwodo.commons.core.LogsCenter;
import seedu.onetwodo.commons.core.UnmodifiableObservableList;
import seedu.onetwodo.commons.events.model.ToDoListChangedEvent;
import seedu.onetwodo.commons.exceptions.EmptyHistoryException;
import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.commons.util.CollectionUtil;
import seedu.onetwodo.commons.util.StringUtil;
import seedu.onetwodo.logic.parser.DoneStatus;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.Task;
import seedu.onetwodo.model.task.UniqueTaskList;
import seedu.onetwodo.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the todo list data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private ToDoList toDoList;
    private FilteredList<ReadOnlyTask> filteredTasks;
    private DoneStatus doneStatus;

    private ToDoListHistoryManager history;


    /**
     * Initializes a ModelManager with the given toDoList and userPrefs.
     */
    public ModelManager(ReadOnlyToDoList toDoList, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(toDoList, userPrefs);

        logger.fine("Initializing with toDo list: " + toDoList + " and user prefs " + userPrefs);

        this.toDoList = new ToDoList(toDoList);
        this.filteredTasks = new FilteredList<>(this.toDoList.getTaskList());
        this.doneStatus = DoneStatus.UNDONE;
        this.history = new ToDoListHistoryManager();
    }

    public ModelManager() {
        this(new ToDoList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyToDoList newData) {
        toDoList.resetData(newData);
        indicateToDoListChanged();
    }

    @Override
    public ReadOnlyToDoList getToDoList() {
        return toDoList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateToDoListChanged() {
        raise(new ToDoListChangedEvent(toDoList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        history.saveToDoList(this.toDoList);
        toDoList.removeTask(target);
        indicateToDoListChanged();
    }

    @Override
    public synchronized void doneTask(int filteredTaskListIndex) throws IllegalValueException {
        history.saveToDoList(this.toDoList);
        int toDoListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        toDoList.doneTask(toDoListIndex);
        setDoneStatus(DoneStatus.UNDONE);
        updateFilteredUndoneTaskList();
        indicateToDoListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        history.saveToDoList(this.toDoList);
        toDoList.addTask(task);
        setDoneStatus(DoneStatus.UNDONE);
        updateFilteredUndoneTaskList();
        indicateToDoListChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int toDoListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        toDoList.updateTask(toDoListIndex, editedTask);
        indicateToDoListChanged();
    }

    @Override
    public void undo() throws EmptyHistoryException {
        this.toDoList.resetData(history.getPreviousToDoList());
        setDoneStatus(DoneStatus.UNDONE);
        updateFilteredUndoneTaskList();
        indicateToDoListChanged();
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

    @Override
    public void updateFilteredUndoneTaskList() {
        updateFilteredTaskList(new PredicateExpression(p -> p.getDoneStatus() == false));
    }

    @Override
    public void updateFilteredDoneTaskList() {
        updateFilteredTaskList(new PredicateExpression(p -> p.getDoneStatus() == true));
    }

    //========== Inner classes/interfaces used for filtering =================================================

    @Override
    public DoneStatus getDoneStatus() {
        return doneStatus;
    }

    @Override
    public void setDoneStatus(DoneStatus doneStatus) {
        this.doneStatus = doneStatus;
    }

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

}
