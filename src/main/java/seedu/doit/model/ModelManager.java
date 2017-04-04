//@@author A0139399J
package seedu.doit.model;

import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.doit.commons.core.CommandSettings;
import seedu.doit.commons.core.ComponentManager;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.commons.events.model.TaskManagerChangedEvent;
import seedu.doit.commons.exceptions.EmptyTaskManagerStackException;
import seedu.doit.commons.util.CollectionUtil;
import seedu.doit.logic.commands.exceptions.CommandExistedException;
import seedu.doit.logic.commands.exceptions.NoSuchCommandException;
import seedu.doit.model.comparators.EndTimeComparator;
import seedu.doit.model.comparators.PriorityComparator;
import seedu.doit.model.comparators.StartTimeComparator;
import seedu.doit.model.comparators.TaskNameComparator;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.Task;
import seedu.doit.model.item.UniqueTaskList;
import seedu.doit.model.item.UniqueTaskList.DuplicateTaskException;
import seedu.doit.model.item.UniqueTaskList.TaskNotFoundException;
import seedu.doit.model.predicates.AlwaysTruePredicate;
import seedu.doit.model.predicates.DescriptionPredicate;
import seedu.doit.model.predicates.DonePredicate;
import seedu.doit.model.predicates.EndTimePredicate;
import seedu.doit.model.predicates.NamePredicate;
import seedu.doit.model.predicates.PriorityPredicate;
import seedu.doit.model.predicates.StartTimePredicate;
import seedu.doit.model.predicates.TagPredicate;

/**
 * Represents the in-memory model of the task manager data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private TaskManager taskManager;
    private FilteredList<ReadOnlyTask> filteredTasks;

    private static final TaskManagerStack taskManagerStack = TaskManagerStack.getInstance();

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyItemManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        updateFilteredTasks();
        updateFilteredListToShowAll();

    }

    // @@author A0138909R
    /**
     * Updates the filteredTasks after the taskmanager have changed
     */
    public void updateFilteredTasks() {
        this.filteredTasks = new FilteredList<ReadOnlyTask>(this.taskManager.getTaskList());
    }

    // @@author A0139399J
    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyItemManager newData) {
        this.taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public void resetDataWithoutSaving(ReadOnlyItemManager newData) {
        this.taskManager.resetData(newData);
    }

    @Override
    public void loadData(ReadOnlyItemManager newData) {
        this.resetDataWithoutSaving(newData);
        taskManagerStack.clearRedoStack();
        taskManagerStack.clearUndoStack();
    }

    // @@author A0138909R
    @Override
    public void clearData() {
        logger.info("clears all tasks in model manager");
        taskManagerStack.addToUndoStack(this.getTaskManager());
        taskManagerStack.clearRedoStack();
        this.taskManager.resetData(new TaskManager());
        indicateTaskManagerChanged();
    }

    // @@author A0139399J
    @Override
    public ReadOnlyItemManager getTaskManager() {
        return this.taskManager;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(this.taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        logger.info("delete task in model manager");
        taskManagerStack.addToUndoStack(this.getTaskManager());
        taskManagerStack.clearRedoStack();
        this.taskManager.removeTask(target);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();

    }

    @Override
    public synchronized void deleteTasks(Set<ReadOnlyTask> targets) throws TaskNotFoundException {
        logger.info("delete task(s) in model manager");
        taskManagerStack.addToUndoStack(this.getTaskManager());
        taskManagerStack.clearRedoStack();
        for (ReadOnlyTask target: targets) {
            this.taskManager.removeTask(target);
        }
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        logger.info("add task in model manager");
        taskManagerStack.addToUndoStack(this.getTaskManager());
        taskManagerStack.clearRedoStack();
        this.taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void markTask(int filteredTaskListIndex, ReadOnlyTask taskToDone)
            throws UniqueTaskList.TaskNotFoundException, DuplicateTaskException {
        logger.info("marked a task in model manager as done");
        taskManagerStack.addToUndoStack(this.getTaskManager());
        taskManagerStack.clearRedoStack();
        int taskManagerIndex = this.filteredTasks.getSourceIndex(filteredTaskListIndex);
        this.taskManager.markTask(taskManagerIndex, taskToDone);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void unmarkTask(int filteredTaskListIndex, ReadOnlyTask taskToDone)
            throws UniqueTaskList.TaskNotFoundException, DuplicateTaskException {
        logger.info("marked a task in model manager as done");
        taskManagerStack.addToUndoStack(this.getTaskManager());
        taskManagerStack.clearRedoStack();
        int taskManagerIndex = this.filteredTasks.getSourceIndex(filteredTaskListIndex);
        this.taskManager.unmarkTask(taskManagerIndex, taskToDone);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask) throws DuplicateTaskException {
        assert editedTask != null;
        logger.info("update task in model manager");
        taskManagerStack.addToUndoStack(this.getTaskManager());
        taskManagerStack.clearRedoStack();
        int taskManagerIndex = this.filteredTasks.getSourceIndex(filteredTaskListIndex);
        this.taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
    }

    @Override
    public void sortBy(String sortType) {
        switch (sortType) {
        case "start time":
            this.taskManager.setTaskComparator(new StartTimeComparator());
            break;
        case "end time":
            this.taskManager.setTaskComparator(new EndTimeComparator());
            break;
        case "priority":
            this.taskManager.setTaskComparator(new PriorityComparator());
            break;
        case "name":
            // fallthrough
        default:
            this.taskManager.setTaskComparator(new TaskNameComparator());
            break;
        }
    }

    // @@author A0138909R
    @Override
    public void undo() throws EmptyTaskManagerStackException {
        this.taskManager.resetData(taskManagerStack.loadOlderTaskManager(this.getTaskManager()));
        indicateTaskManagerChanged();
    }

    @Override
    public void redo() throws EmptyTaskManagerStackException {
        this.taskManager.resetData(taskManagerStack.loadNewerTaskManager(this.getTaskManager()));
        indicateTaskManagerChanged();
    }

    @Override
    public void commandSet(String oldCommand, String newCommand)
            throws NoSuchCommandException, CommandExistedException {
        CommandSettings.getInstance().setCommand(oldCommand, newCommand);
        logger.info(CommandSettings.getInstance().toString());
    }

    // @@author A0139399J
    // =========== Filtered Task List Accessors
    // ============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(this.filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        this.filteredTasks.setPredicate(null);
        this.filteredTasks.setPredicate(new DonePredicate(false));
        indicateTaskManagerChanged();
    }

    @Override
    public void updateFilteredListToShowDone() {
        this.filteredTasks.setPredicate(null);
        this.filteredTasks.setPredicate(new DonePredicate(true));
        indicateTaskManagerChanged();
    }

    @Override
    public void updateFilteredTaskList(Set<String> nameKeywords, Set<String> priorityKeywords,
            Set<String> descriptionKeywords, Set<String> tagKeywords, Set<String> startTimekeywords,
            Set<String> endTimekeywords) {
        Predicate<ReadOnlyTask> combined = new AlwaysTruePredicate();

        if (!nameKeywords.isEmpty()) {
            Predicate<ReadOnlyTask> namePredicate = new NamePredicate(nameKeywords);
            combined = combined.and(namePredicate);
        }
        if (!priorityKeywords.isEmpty()) {
            Predicate<ReadOnlyTask> priorityPredicate = new PriorityPredicate(priorityKeywords);
            combined = combined.and(priorityPredicate);
        }
        if (!descriptionKeywords.isEmpty()) {
            Predicate<ReadOnlyTask> descriptionPredicate = new DescriptionPredicate(descriptionKeywords);
            combined = combined.and(descriptionPredicate);
        }
        if (!tagKeywords.isEmpty()) {
            Predicate<ReadOnlyTask> tagPredicate = new TagPredicate(tagKeywords);
            combined = combined.and(tagPredicate);
        }
        if (!startTimekeywords.isEmpty()) {
            Predicate<ReadOnlyTask> startTimePredicate = new StartTimePredicate(startTimekeywords);
            combined = combined.and(startTimePredicate);
        }
        if (!endTimekeywords.isEmpty()) {
            Predicate<ReadOnlyTask> endTimePredicate = new EndTimePredicate(endTimekeywords);
            combined = combined.and(endTimePredicate);
        }

        this.filteredTasks.setPredicate(combined);
    }
}
