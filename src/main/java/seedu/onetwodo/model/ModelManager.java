package seedu.onetwodo.model;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.onetwodo.commons.core.ComponentManager;
import seedu.onetwodo.commons.core.LogsCenter;
import seedu.onetwodo.commons.core.UnmodifiableObservableList;
import seedu.onetwodo.commons.events.model.ToDoListChangedEvent;
import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.commons.util.CollectionUtil;
import seedu.onetwodo.commons.util.StringUtil;
import seedu.onetwodo.logic.parser.DoneStatus;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.Task;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.model.task.UniqueTaskList;
import seedu.onetwodo.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the todo list data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    // toDoList is data, not observable
    private final ToDoList toDoList;

    // filteredTasks is observable
    private final FilteredList<ReadOnlyTask> filteredTasks;

    // All commands except find clears the search String filter
    private Set<String> searchStrings;

    // To persist on list command
    private DoneStatus doneStatus;

    /**
     * Initializes a ModelManager with the given toDoList and userPrefs.
     */
    public ModelManager(ReadOnlyToDoList toDoList, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(toDoList, userPrefs);

        logger.fine("Initializing with toDo list: " + toDoList + " and user prefs " + userPrefs);

        this.toDoList = new ToDoList(toDoList);
        this.filteredTasks = new FilteredList<>(this.toDoList.getTaskList());
        this.searchStrings = new HashSet<String>();
        this.doneStatus = DoneStatus.UNDONE;
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
    public void resetSearchStrings() {
        this.searchStrings = new HashSet<String>();
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
        toDoList.removeTask(target);
        indicateToDoListChanged();
    }

    @Override
    public synchronized void doneTask(int filteredTaskListIndex) throws IllegalValueException {
        int toDoListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        toDoList.doneTask(toDoListIndex);
        indicateToDoListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        toDoList.addTask(task);
        indicateToDoListChanged();
    }

    @Override
    public synchronized void addTask(int internalIdx, Task task) throws UniqueTaskList.DuplicateTaskException {
        toDoList.addTask(internalIdx, task);
        indicateToDoListChanged();
    };

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int toDoListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        toDoList.updateTask(toDoListIndex, editedTask);
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
        searchStrings = keywords;
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

    @Override
    public void updateByTaskType(TaskType taskType) {
        updateFilteredTaskList(new PredicateExpression(p -> p.getTaskType() == taskType));
    }

    @Override
    public void updateByDoneStatus() {
        switch (doneStatus) {
        case DONE:
            updateFilteredDoneTaskList();
            break;
        case UNDONE:
            updateFilteredUndoneTaskList();
            break;
        case ALL:
            updateFilteredListToShowAll();
            break;
        }
    };

    @Override
    public void updateBySearchStrings() {
        if (searchStrings.size() > 0) {
            updateFilteredTaskList(searchStrings);
        }
    }

    public FilteredList<ReadOnlyTask> getFilteredByDoneFindType(TaskType type) {
        // update by find before getting
        updateBySearchStrings();

        // filter by type
        FilteredList<ReadOnlyTask> filtered = getFilteredTaskList().filtered(t -> t.getTaskType() == type);

        // filter by done and return
        switch (doneStatus) {
        case DONE:
            return filtered.filtered(t -> t.getDoneStatus() == true);
        case UNDONE:
            return filtered.filtered(t -> t.getDoneStatus() == false);
        case ALL:
        default:
            return filtered;
        }
    }

    public int getTaskIndex(ReadOnlyTask task) {
        FilteredList<ReadOnlyTask> filtered = getFilteredByDoneFindType(task.getTaskType());
        return filtered.indexOf(task);
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
