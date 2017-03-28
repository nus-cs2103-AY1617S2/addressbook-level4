package seedu.task.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private FilteredList<ReadOnlyTask> filteredTasks;
    private FilteredList<ReadOnlyTask> completedTasks;
    /**
     * Initializes a ModelManager with the given task manager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);
        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        completedTasks = new FilteredList<>(this.taskManager.getCompletedTaskList());
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int addressBookIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(addressBookIndex, editedTask);
        indicateAddressBookChanged();
    }

    //@@author A0139161J
    @Override
    public void insertTasktoIndex(int indexToBeRestored, Task deletedTask)
            throws DuplicateTaskException {
        taskManager.addTaskToIndex(indexToBeRestored, deletedTask);
    }

    @Override
    public void loadList(ObservableList<ReadOnlyTask> list) throws DuplicateTaskException {
        taskManager.setTasks(list);
    }

    @Override
    public ObservableList<ReadOnlyTask> getCompletedTaskList() {
        return new UnmodifiableObservableList<>(completedTasks);
    }

    @Override
    public void completeTask(Task t) throws DuplicateTaskException, TaskNotFoundException {
        taskManager.transferTaskToComplete(t);
    }

    @Override
    public void uncompleteTask(Task t) throws DuplicateTaskException, TaskNotFoundException {
        taskManager.transferTaskFromComplete(t);
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

    @Override
    public void updateUpcomingTaskList() {
        Date currentDate = new Date();
        String textDate1 = new SimpleDateFormat("d-MMM-yyyy").format(currentDate);
        String textDate2 = new SimpleDateFormat("dd-MMM-yyyy").format(currentDate);

        Predicate<? super ReadOnlyTask> pred  = s -> s.getDate().toString().equals(textDate1)
            || s.getDate().toString().equals(textDate2);
        filteredTasks.setPredicate(pred);
    }

    //@@author A0139322L
    @Override
    public void updateFilteredTagTaskList(String tagName) throws IllegalValueException {
        Tag tag;
        tag = new Tag(tagName);

        Predicate<? super ReadOnlyTask> pred = s -> s.getTags().contains(tag);
        filteredTasks.setPredicate(pred);
    }
    //@@author

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
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getTaskName().taskName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
