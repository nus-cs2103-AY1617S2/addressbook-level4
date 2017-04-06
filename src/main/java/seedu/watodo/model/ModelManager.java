package seedu.watodo.model;

import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.transformation.FilteredList;
import seedu.watodo.commons.core.ComponentManager;
import seedu.watodo.commons.core.LogsCenter;
import seedu.watodo.commons.core.UnmodifiableObservableList;
import seedu.watodo.commons.events.model.TaskListChangedEvent;
import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.commons.util.CollectionUtil;
import seedu.watodo.commons.util.StringUtil;
import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.ListDeadlineCommand;
import seedu.watodo.logic.commands.ListDoneCommand;
import seedu.watodo.logic.commands.ListEventCommand;
import seedu.watodo.logic.commands.ListFloatCommand;
import seedu.watodo.logic.commands.ListUndoneCommand;
import seedu.watodo.model.task.DateTime;
import seedu.watodo.model.task.ReadOnlyTask;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.UniqueTaskList;
import seedu.watodo.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private Stack< Command > commandHistory;
    private Stack< Command > undoneHistory;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.commandHistory = new Stack< Command >();
        this.undoneHistory = new Stack< Command >();
        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskListChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredByTypesTaskList(ListUndoneCommand.ARGUMENT);
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
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
    public void updateFilteredByNameTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    @Override
    public void updateFilteredByTagsTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new TagQualifier(keywords)));
    }

    @Override
    public void updateFilteredByDatesTaskList(DateTime start, DateTime end) throws IllegalValueException {
        updateFilteredTaskList(new PredicateExpression(new DateQualifier(start, end)));
    }

    @Override
    public void updateFilteredByTypesTaskList(String type) {
        updateFilteredTaskList(new PredicateExpression(new TypeQualifier(type)));
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
            assert nameKeyWords != null;
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(
                        task.getDescription().fullDescription, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    //@@author A0139872R-reused
    private class TagQualifier implements Qualifier {
        private Set<String> tagKeyWords;
        private String tags;

        TagQualifier(Set<String> tagKeyWords) {
            assert tagKeyWords != null;
            this.tagKeyWords = tagKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            tags = task
                .getTags()
                .asObservableList()
                .stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.joining(" "));
            String status = task.getStatus().toString();
            return tagKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(tags, keyword))
                    .findAny()
                    .isPresent() &&
                    status.equalsIgnoreCase(ListUndoneCommand.ARGUMENT);
        }

        @Override
        public String toString() {
            return "tag=" + String.join(", ", tagKeyWords);
        }
    }

    //@@author A0139872R
    /**
     * Returns true if the tasks are within the specified range and is undone.
     */
    private class DateQualifier implements Qualifier {
        private DateTime start = null;
        private DateTime end = null;

        DateQualifier(DateTime start, DateTime end) {
            assert start != null || end != null;
            if (start != null) {
                this.start = start;
            }
            if (end != null) {
                this.end = end;
            }
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            String status = task.getStatus().toString();
            if (status.equalsIgnoreCase(ListUndoneCommand.ARGUMENT)) {
                if (task.getEndDate() == null) {
                    return true;
                }
                if (start == null) {
                    return end.isLater(task.getEndDate());
                }
                if (end == null) {
                    task.getStartDate().isLater(start);
                }
                return end.isLater(task.getEndDate()) && task.getStartDate().isLater(start);
            }
            return false;
        }

        @Override
        public String toString() {
            return "start=" + start.toString() + " end=" + end.toString();
        }
    }

    /**
     * Returns true if the type of the tasks matches the specified type.
     */
    private class TypeQualifier implements Qualifier {
        private String type;

        TypeQualifier(String type) {
            assert type != null;
            this.type = type;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            String status = task.getStatus().toString();
            switch (type) {
            case ListDoneCommand.ARGUMENT:
                if (status.equalsIgnoreCase(ListDoneCommand.ARGUMENT)) {
                    return true;
                } else {
                    return false;
                }
            case ListDeadlineCommand.ARGUMENT:
                if (task.getStartDate() == null && task.getEndDate() != null &&
                    status.equalsIgnoreCase(ListUndoneCommand.ARGUMENT)) {
                    return true;
                } else {
                    return false;
                }
            case ListEventCommand.ARGUMENT:
                if (task.getStartDate() != null && task.getEndDate() != null &&
                    status.equalsIgnoreCase(ListUndoneCommand.ARGUMENT)) {
                    return true;
                } else {
                    return false;
                }
            case ListFloatCommand.ARGUMENT:
                if (task.getStartDate() == null && task.getEndDate() == null &&
                    status.equalsIgnoreCase(ListUndoneCommand.ARGUMENT)) {
                    return true;
                } else {
                    return false;
                }
            case ListUndoneCommand.ARGUMENT:
                if (status.equalsIgnoreCase(type)) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
            }
        }

        @Override
        public String toString() {
            return "type=" + type;
        }
    }

    //@@author A0139845R
    @Override
    public Command getPreviousCommand() {
        if (!commandHistory.isEmpty()) {
            Command commandToReturn = commandHistory.pop();
            undoneHistory.push(commandToReturn);
            return commandToReturn;
        }
        return null;
    }

    @Override
    public void addCommandToHistory(Command command) {
        String cmdWord = command.toString();

        switch (cmdWord) {
        case "add":
        case "clear":
        case "delete":
        case "edit":
        case "mark":
        case "unmark":
            commandHistory.push(command);
        default:
            break;
        }


    }

    /**
     * returns the
     */
    @Override
    public Command getUndoneCommand() {
        if (!undoneHistory.isEmpty()) {
            Command commandToReturn = undoneHistory.pop();
            commandHistory.push(commandToReturn);
            return commandToReturn;
        }
        return null;
    }

    @Override
    public void clearRedo() {
        this.undoneHistory.clear();
    }

    //@@author

}
