package todolist.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import todolist.commons.core.ComponentManager;
import todolist.commons.core.LogsCenter;
import todolist.commons.core.UnmodifiableObservableList;
import todolist.commons.events.model.ToDoListChangedEvent;
import todolist.commons.util.CollectionUtil;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.Task;
import todolist.model.task.UniqueTaskList;
import todolist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the to-do list data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    // @@author A0143648Y
    private final ToDoList todoList;
    private FilteredList<ReadOnlyTask> filteredFloats;
    private FilteredList<ReadOnlyTask> filteredTasks;
    private FilteredList<ReadOnlyTask> filteredEvents;

    /**
     * Initializes a ModelManager with the given ToDoList and userPrefs.
     */
    public ModelManager(ReadOnlyToDoList todoList, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(todoList, userPrefs);

        logger.fine("Initializing with to-do list: " + todoList + " and user prefs " + userPrefs);

        this.todoList = new ToDoList(todoList);
        filteredTasks = new FilteredList<>(this.todoList.getFilteredTasks());
        filteredFloats = new FilteredList<>(this.todoList.getFilteredFloats());
        filteredEvents = new FilteredList<>(this.todoList.getFilteredEvents());
    }

    // @@
    public ModelManager() {
        this(new ToDoList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyToDoList newData) {
        todoList.resetData(newData);
        indicateToDoListChanged();
    }

    @Override
    public ReadOnlyToDoList getToDoList() {
        return todoList;
    }

    @Override
    public String getTagListToString() {
        return todoList.getTagListToString();
    }

    /** Raises an event to indicate the model has changed */
    private void indicateToDoListChanged() {
        raise(new ToDoListChangedEvent(todoList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        todoList.removeTask(target);
        indicateToDoListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        todoList.addTask(task);
        updateFilteredListToShowAll();
        indicateToDoListChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int todoListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        todoList.updateTask(todoListIndex, editedTask);
        indicateToDoListChanged();
    }

    // =========== Filtered Task List Accessors
    // =============================================================
    // @@author A0143648Y
    @Override
    public int getTotalFilteredTaskListSize() {
        return getFilteredTaskList().size() + getFilteredEventList().size() + getFilteredFloatList().size();
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredEventList() {
        return new UnmodifiableObservableList<>(filteredEvents);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatList() {
        return new UnmodifiableObservableList<>(filteredFloats);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getListFromChar(Character type) {
        switch (type) {

        case Task.DEADLINE_CHAR:
            return getFilteredTaskList();

        case Task.EVENT_CHAR:
            return getFilteredEventList();

        case Task.FLOAT_CHAR:
            return getFilteredFloatList();
        }
        return getFilteredTaskList();
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
        filteredFloats.setPredicate(null);
        filteredEvents.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
        filteredFloats.setPredicate(expression::satisfies);
        filteredEvents.setPredicate(expression::satisfies);
    }

    // ========== Inner classes/interfaces used for filtering
    // =================================================

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

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        // @@author A0143648Y
        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> hasContainedKeyword(task.getTitle().title, keyword)
                            || hasContainedKeyword(task.getStartTimeString(), keyword)
                            || hasContainedKeyword(task.getEndTimeString(), keyword)
                            || hasContainedKeyword(task.getDescriptionString(), keyword)
                            || hasContainedKeyword(task.getVenueString(), keyword))
                    .findAny().isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    private boolean hasContainedKeyword(String searchMe, String findMe) {
        int searchMeLength = searchMe.length();
        int findMeLength = findMe.length();
        boolean foundIt = false;
        for (int i = 0; i <= (searchMeLength - findMeLength); i++) {
            if (searchMe.regionMatches(true, i, findMe, 0, findMeLength)) {
                foundIt = true;
                break;
            }
        }
        return foundIt;
    }

}
