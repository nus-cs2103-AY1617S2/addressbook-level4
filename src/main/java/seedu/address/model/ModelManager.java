package seedu.address.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.base.Joiner;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.ToDoAppChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueTaskList;
import seedu.address.model.person.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ToDoApp toDoApp;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    /**
     * Initializes a ModelManager with the given toDoApp and userPrefs.
     */
    public ModelManager(ReadOnlyToDoApp toDoApp, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(toDoApp, userPrefs);

        logger.fine("Initializing with ToDoApp: " + toDoApp + " and user prefs " + userPrefs);

        this.toDoApp = new ToDoApp(toDoApp);
        filteredTasks = new FilteredList<>(this.toDoApp.getTaskList());
    }

    public ModelManager() {
        this(new ToDoApp(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyToDoApp newData) {
        toDoApp.resetData(newData);
        indicateToDoAppChanged();
    }

    @Override
    public ReadOnlyToDoApp getToDoApp() {
        return toDoApp;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateToDoAppChanged() {
        raise(new ToDoAppChangedEvent(toDoApp));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        toDoApp.removeTask(target);
        indicateToDoAppChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        toDoApp.addTask(task);
        updateFilteredListToShowAll();
        indicateToDoAppChanged();
    }

    // @@author A0114395E
    @Override
    public synchronized void addTask(Task task, int idx) throws UniqueTaskList.DuplicateTaskException {
        toDoApp.addTask(task, idx);
        updateFilteredListToShowAll();
        indicateToDoAppChanged();
    }
    // @@author

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int toDoAppIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        toDoApp.updateTask(toDoAppIndex, editedTask);
        indicateToDoAppChanged();
    }

    // =========== Filtered Task List Accessors
    // =============================================================

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
        if (keywords.contains("name")) {
            keywords.remove("name");
            updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
        } else if (keywords.contains("deadline")) {
            keywords.remove("deadline");
            updateFilteredTaskList(new PredicateExpression(new DeadlineQualifier(keywords)));
        } else if (keywords.contains("priority")) {
            keywords.remove("priority");
            updateFilteredTaskList(new PredicateExpression(
                    new PriorityQualifier(Integer.parseInt(Joiner.on(" ").skipNulls().join(keywords)))));
        } else if (keywords.contains("completion")) {
            keywords.remove("completion");
            updateFilteredTaskList(new PredicateExpression(
                    new CompletionQualifier(Joiner.on(" ").skipNulls().join(keywords))));
        }
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    // ========== Inner classes/interfaces used for filtering
    // =================================================

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
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword)).findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    // @@author A0124591H
    private class DeadlineQualifier implements Qualifier {
        private Deadline deadlineKeyDeadline;
        private String deadlineKeyString;

        DeadlineQualifier(Set<String> deadlineKeyInputs) {
            try {
                this.deadlineKeyDeadline = new Deadline(Joiner.on(" ").join(deadlineKeyInputs));
                this.deadlineKeyString = deadlineKeyDeadline.toString();
            } catch (IllegalValueException e) {
            }
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getDeadline().toString() == deadlineKeyString;
        }

        @Override
        public String toString() {
            return "deadline=" + String.join(", ", deadlineKeyDeadline.value);
        }
    }

    // @@author A0124591H
    private class PriorityQualifier implements Qualifier {
        private int priorityNumber;

        PriorityQualifier(int priorityNumber) {
            this.priorityNumber = priorityNumber;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getPriority().value == priorityNumber;
        }

        @Override
        public String toString() {
            return "priority=" + String.join(", ", String.valueOf(priorityNumber));
        }
    }

    // @@author A0124591H
    private class CompletionQualifier implements Qualifier {
        private String completionValue;

        CompletionQualifier(String completionValue) {
            this.completionValue = completionValue;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return String.valueOf(task.getCompletion().value).toLowerCase() == completionValue.toLowerCase();
        }

        @Override
        public String toString() {
            return "completion=" + String.join(", ", completionValue);
        }
    }

}
