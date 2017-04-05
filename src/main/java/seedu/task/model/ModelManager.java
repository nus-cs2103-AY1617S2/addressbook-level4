package seedu.task.model;

import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.model.TaskListChangedEvent;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskList taskList;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final Stack<TaskList> undoStack;
    private final Stack<TaskList> redoStack;

    /**
     * Initializes a ModelManager with the given taskList and userPrefs.
     */
    public ModelManager(ReadOnlyTaskList taskList, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskList, userPrefs);

        logger.fine("Initializing with address book: " + taskList + " and user prefs " + userPrefs);

        this.taskList = new TaskList(taskList);
        filteredTasks = new FilteredList<>(this.taskList.getTaskList());
        this.undoStack = new Stack<TaskList>();
        this.redoStack = new Stack<TaskList>();
    }

    public ModelManager() {
        this(new TaskList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskList newData) {
        taskList.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskList getTaskList() {
        return taskList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new TaskListChangedEvent(taskList));
    }

    //@@author A0113795Y
    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        TaskList update = new TaskList(this.taskList);
        undoStack.push(update);
        while (!redoStack.empty()) {
            redoStack.pop();
        }
        this.taskList.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        TaskList update = new TaskList(this.taskList);
        undoStack.push(update);
        while (!redoStack.empty()) {
            redoStack.pop();
        }
        this.taskList.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    @Override
    public void updateTask(int filteredPersonListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int addressBookIndex = filteredTasks.getSourceIndex(filteredPersonListIndex);
        TaskList update = new TaskList(this.taskList);
        undoStack.push(update);
        while (!redoStack.empty()) {
            redoStack.pop();
        }
        this.taskList.updateTask(addressBookIndex, editedTask);
        indicateAddressBookChanged();
    }

    @Override
    public void undo() {
        TaskList temp = undoStack.peek();
        redoStack.push(new TaskList(this.taskList));
        this.taskList.resetData(temp);
        undoStack.pop();
    }

    @Override
    public void redo() {
        TaskList temp = redoStack.peek();
        undoStack.push(new TaskList(this.taskList));
        this.taskList.resetData(temp);
        redoStack.pop();
    }

    @Override
    public Stack<TaskList> getUndoStack() {
        return this.undoStack;
    }
    //@@author
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

        //@@author A0164212U
        /**
         * @param task
         * internally sets task.occurrenceIndexList for occurrences that match given keywords for task
         * @return true if keywords are present in the given task
         */
        @Override
        public boolean run(ReadOnlyTask task) {
            boolean isValid = false;
            ArrayList<Integer> occurrenceIndexList = new ArrayList<Integer>();
            for (int i = 0; i < task.getOccurrences().size(); i++) {
                final int finalIndex = i;
                if (
                        (nameKeyWords.stream()
                                .filter(keyword -> StringUtil.containsWordIgnoreCase(
                                        task.getDescription().description, keyword))
                                .findAny()
                                .isPresent()) ||
                        (nameKeyWords.stream()
                                .filter(keyword -> StringUtil.containsWordIgnoreCase(
                                        task.getPriority().value, keyword))
                                .findAny()
                                .isPresent()) ||
                        (nameKeyWords.stream()
                                .filter(keyword -> StringUtil.containsWordIgnoreCase(
                                        task.getOccurrences().get(finalIndex).getStartTiming().value, keyword))
                                .findAny()
                                .isPresent()) ||
                        (nameKeyWords.stream()
                                .filter(keyword -> StringUtil.containsWordIgnoreCase(
                                        task.getOccurrences().get(finalIndex).getEndTiming().value, keyword))
                                .findAny()
                                .isPresent())) {
                    occurrenceIndexList.add(i);
                    isValid = true;
                }
            }
            task.setOccurrenceIndexList(occurrenceIndexList);
            return isValid;
        }
        //@@author

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
