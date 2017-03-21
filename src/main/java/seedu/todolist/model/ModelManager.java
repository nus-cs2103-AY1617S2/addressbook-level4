package seedu.todolist.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.todolist.commons.core.ComponentManager;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.commons.events.model.ToDoListChangedEvent;
import seedu.todolist.commons.events.model.ViewListChangedEvent;
import seedu.todolist.commons.util.CollectionUtil;
import seedu.todolist.commons.util.StringUtil;
import seedu.todolist.logic.commands.ListCommand;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList;
import seedu.todolist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the to-do list data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ToDoList toDoList;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given to-do list and userPrefs.
     */
    public ModelManager(ReadOnlyToDoList toDoList, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(toDoList, userPrefs);

        logger.fine("Initializing with to-do list: " + toDoList + " and user prefs " + userPrefs);

        this.toDoList = new ToDoList(toDoList);
        filteredTasks = new FilteredList<>(this.toDoList.getTaskList());
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

    //@@author A0144240W
    /** Raises an event to indicate that the filteredList has changed */
    private void indicateViewListChanged(String typeOfList) {
        raise(new ViewListChangedEvent(typeOfList));
    }

    @Override
    public synchronized void deleteTask(Task target) throws TaskNotFoundException {
        toDoList.removeTask(target);
        indicateToDoListChanged();
    }

    @Override
    public synchronized void completeTask(int filteredTaskListIndex, Task target) throws TaskNotFoundException {
        int toDoListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        toDoList.completeTask(toDoListIndex, target);
        indicateToDoListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        toDoList.addTask(task);
        updateFilteredListToShowAll();
        indicateToDoListChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, Task editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int toDoListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        toDoList.updateTask(toDoListIndex, editedTask);
        indicateToDoListChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<Task> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    //@@author A0139633B
    public UnmodifiableObservableList<Task> getFilteredIncompleteTaskList() {
        filteredTasks.setPredicate((Predicate<? super Task>) task -> {
            return !task.isComplete();
        });
        indicateViewListChanged(ListCommand.TYPE_INCOMPLETE);
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    //@@author A0139633B
    public UnmodifiableObservableList<Task> getFilteredCompleteTaskList() {
        filteredTasks.setPredicate((Predicate<? super Task>) task -> {
            return task.isComplete();
        });
        indicateViewListChanged(ListCommand.TYPE_COMPLETE);
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    //@@author A0139633B
    public UnmodifiableObservableList<Task> getFilteredOverdueTaskList() {
        filteredTasks.setPredicate((Predicate<? super Task>) task -> {
            //get current time and compare with the task's end time
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy h.mm a");
            Date currentDate = new Date();
            if (task.getEndTime() != null) {
                String taskDateString = task.getEndTime().toString();
                try {
                    Date taskDate = dateFormat.parse(taskDateString);
                    return currentDate.compareTo(taskDate) > 0;
                } catch (ParseException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                return false;
            }
        });
        indicateViewListChanged(ListCommand.TYPE_OVERDUE);
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        indicateViewListChanged(ListCommand.TYPE_ALL);
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
        boolean satisfies(Task task);
        @Override
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(Task task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(Task task);
        @Override
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(Task task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName,
                            task.getTags().toSet(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
