package seedu.tasklist.model;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.ocpsoft.prettytime.shade.org.apache.commons.lang.time.DateUtils;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.tasklist.commons.core.ComponentManager;
import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.commons.events.model.TaskListChangedEvent;
import seedu.tasklist.commons.exceptions.DataConversionException;
import seedu.tasklist.commons.util.CollectionUtil;
import seedu.tasklist.commons.util.StringUtil;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.task.DeadlineTask;
import seedu.tasklist.model.task.EventTask;
import seedu.tasklist.model.task.ReadOnlyDeadlineTask;
import seedu.tasklist.model.task.ReadOnlyEventTask;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList;
import seedu.tasklist.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.tasklist.storage.Storage;

/**
 * Represents the in-memory model of the task list data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskList taskList;
    private final Storage storage;

    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final FilteredList<ReadOnlyTask> todaysTasks;
    private final FilteredList<ReadOnlyTask> tomorrowsTasks;
    private Stack<Pair> undoStack;
    private Stack<Pair> redoStack;
    public String userInput;
//@@author A0141993X
    /**
     * Initializes a ModelManager with the given taskList and userPrefs.
     */
    public ModelManager(ReadOnlyTaskList taskList, Storage storage, UserPrefs userPref) {
        super();
        assert !CollectionUtil.isAnyNull(taskList, storage);
        assert userPref != null;

        logger.fine("Initializing with task list: " + taskList + " and user prefs " + userPref);

        this.taskList = new TaskList(taskList);
        this.storage = storage;
        filteredTasks = new FilteredList<>(this.taskList.getTaskList());
        todaysTasks = new FilteredList<>(this.taskList.getTaskList());
        updateTodaysTaskList();
        tomorrowsTasks = new FilteredList<>(this.taskList.getTaskList());
        updateTomorrowsTaskList();

        this.undoStack = new Stack<Pair>();
        this.redoStack = new Stack<Pair>();
    }

    public ModelManager(Storage storage) {
        this(new TaskList(), storage, new UserPrefs());
    }
//@@author
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
        TaskList taskListToPush = new TaskList(taskList);
        undoStack.push(new Pair(taskListToPush, userInput));
        taskList.removeTask(target);
        indicateTaskListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        TaskList taskListToPush = new TaskList(taskList);
        undoStack.push(new Pair(taskListToPush, userInput));
        taskList.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskListChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;
        TaskList taskListToPush = new TaskList(taskList);
        undoStack.push(new Pair(taskListToPush, userInput));
        int taskListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskList.updateTask(taskListIndex, editedTask);
        indicateTaskListChanged();
    }

    //@@author A0139747N
    @Override
    public String setPreviousState() throws EmptyStackException {
        if (undoStack.empty()) {
            throw new EmptyStackException();
        }


        Pair previousState = undoStack.pop();

        redoStack.push(new Pair(new TaskList(taskList), previousState.getUserInput()));

        taskList.resetData(previousState.getList());
        updateFilteredListToShowAll();

        return previousState.getUserInput();
    }

    @Override
    public String setNextState() throws EmptyStackException {
        if (redoStack.empty()) {
            throw new EmptyStackException();
        }
        Pair nextState = redoStack.pop();

        undoStack.push(new Pair(new TaskList(taskList), nextState.getUserInput()));

        taskList.resetData(nextState.getList());
        updateFilteredListToShowAll();

        return nextState.getUserInput();
    }

    @Override
    public void setUserInput(String input) {
        userInput = input;
    }

    @Override
    /**
     * Generates a Pair object with the task list, and a null task, since clear command does not involve a certain task.
     */
    public void enableUndoForClear() {
        Pair current = new Pair(new TaskList(taskList), userInput);
        undoStack.push(current);
    }

//@@author A0141993X
    @Override
    public synchronized void loadTaskList(String filePath) throws IOException {
        Optional<ReadOnlyTaskList> flexiTaskOptional;
        try {
            flexiTaskOptional = storage.readTaskList(filePath);
            if (!flexiTaskOptional.isPresent()) {
                logger.info("File not found.");
                throw new IOException();
            } else {
                taskList.resetData(flexiTaskOptional.get());
                storage.loadTaskList(filePath);
                updateFilteredListToShowAll();
            }
        } catch (DataConversionException e) {
            logger.warning("Wrong file format.");
        }
    }

    @Override
    public synchronized void saveTaskList(String filePath) throws IOException {
        storage.saveTaskList(taskList, filePath);
        indicateTaskListChanged();
    }
//@@author
    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    //@@author A0143355J
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getTodayTaskList() {
        return new UnmodifiableObservableList<>(todaysTasks);
    }

    //@@author A0143355J
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getTomorrowTaskList() {
        return new UnmodifiableObservableList<>(tomorrowsTasks);
    }

    //@@author A0139221N
    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
        updateTodaysTaskList();
        updateTomorrowsTaskList();
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    @Override
    public void updateFilteredTaskListStatus(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new StatusQualifier(keywords)));
    }

    @Override
    public void updateFilteredTaskListTag(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new TagQualifier(keywords)));
    }

    @Override
    public void updateTodaysTaskList() {
        todaysTasks.setPredicate(isTodayTask());
    }

    private static Predicate<ReadOnlyTask> isTodayTask() {
        return p -> (p.getType().equals(DeadlineTask.TYPE)
                        && DateUtils.isSameDay(((ReadOnlyDeadlineTask) p).getDeadline(), new Date()))
                    || (p.getType().equals(EventTask.TYPE)
                        && DateUtils.isSameDay(((ReadOnlyEventTask) p).getStartDate(), new Date()));
    }

    @Override
    public void updateTomorrowsTaskList() {
        tomorrowsTasks.setPredicate(isTomorrowTask());
    }

    private static Predicate<ReadOnlyTask> isTomorrowTask() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 1);
        Date tomorrow = c.getTime();
        return p -> (p.getType().equals(DeadlineTask.TYPE)
                && DateUtils.isSameDay(((ReadOnlyDeadlineTask) p).getDeadline(), tomorrow))
            || (p.getType().equals(EventTask.TYPE)
                && DateUtils.isSameDay(((ReadOnlyEventTask) p).getStartDate(), tomorrow));
    }

//@@author A0141993X
    /**
     * Sort based on parameter specified
     * @param parameter
     */
    public void sortTaskList(String parameter) {
        assert parameter != null;
        switch (parameter) {
        case "Name":
            taskList.sortByName();
            break;
        case "Priority":
            taskList.sortByPriority();
            break;
        case "Date":
            taskList.sortByDate();
            break;
        default:
            break;
        }
        indicateTaskListChanged();
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
            return StringUtil.containsStartingLettersIgnoreCase(task.getName().fullName, nameKeyWords);
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    //@@author A0139221N
    private class TagQualifier implements Qualifier {
        private Set<String> tagKeyWords;

        public TagQualifier(Set<String> tagKeyWords) {
            this.tagKeyWords = tagKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            ObservableList<Tag> tagList = task.getTags().asObservableList();
            List<String> tagStringList = new Vector<String>();
            for (Tag t : tagList) {
                tagStringList.add(StringUtil.removeSquareBrackets(t.toString()));
            }
            return tagStringList.containsAll(tagKeyWords);
        }

        @Override
        public String toString() {
            return "tag=" + String.join(", ", tagKeyWords);
        }
    }

    private class StatusQualifier implements Qualifier {
        private Set<String> statusKeyWord;
        private String completed = "completed";
        @SuppressWarnings("serial")
        private List<String> notCompleted = new Vector<String>() {{ add("not"); add("completed"); }};

        public StatusQualifier(Set<String> statusKeyWord) {
            this.statusKeyWord = statusKeyWord;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (statusKeyWord.size() == 1) {
                assert statusKeyWord.contains(completed);
                return task.getStatus().value;
            } else if (statusKeyWord.size() == 2) {
                assert statusKeyWord.containsAll(notCompleted);
                return !task.getStatus().value;
            } else {
                return false;
            }
        }
    }
}
