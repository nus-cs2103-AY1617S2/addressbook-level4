package seedu.watodo.logic.commands;

import java.util.Stack;

import seedu.watodo.commons.core.UnmodifiableObservableList;
import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.exceptions.CommandException;
import seedu.watodo.model.task.ReadOnlyTask;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.TaskStatus;
import seedu.watodo.model.task.UniqueTaskList;
import seedu.watodo.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.watodo.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0141077L-reused
/**
 * Marks a task identified using it's last displayed index from the task manager
 * as undone.
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the status of the task identified to Undone, "
            + "using the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) [MORE_INDICES]...\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    private static final String MESSAGE_INCOMPLETE_EXECUTION = "Not all tasks sucessfully marked.";
    public static final String MESSAGE_INDEX_OUT_OF_BOUNDS = "The task index provided is out of bounds.";
    public static final String MESSAGE_UNMARK_TASK_SUCCESSFUL = "Task #%1$d marked undone: %2$s";
    private static final String MESSAGE_UNMARK_TASK_UNSUCCESSFUL = "Task #%1$d unsuccessfully marked as undone.";
    public static final String MESSAGE_STATUS_ALREADY_UNDONE = "The task status is already set to Undone.";


    private int[] filteredTaskListIndices;
    private ReadOnlyTask taskToUnmark;
    private Task unmarkedTask;

    //private int indexForUndoUnmark;
    //private Task markedTaskForUndoUnmark;
    private Stack< Task > taskToUnmarkList;
    private Stack< Task > unmarkedTaskList;


    public UnmarkCommand(int[] args) {
        this.filteredTaskListIndices = args;
        changeToZeroBasedIndexing();
        taskToUnmarkList = new Stack< Task >();
        unmarkedTaskList = new Stack< Task >();
    }


    /** Converts filteredTaskListIndex from one-based to zero-based. */
    private void changeToZeroBasedIndexing() {
        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            assert filteredTaskListIndices[i] > 0;
            filteredTaskListIndices[i] = filteredTaskListIndices[i] - 1;
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        final StringBuilder compiledExecutionMessage = new StringBuilder();
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        boolean executionIncomplete = false;

        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            clearClassTaskVariables();
            try {
                checkIndexIsWithinBounds(filteredTaskListIndices[i], lastShownList);
                unmarkTaskAtIndex(filteredTaskListIndices[i], lastShownList);
                storeUnmarkedTaskForUndo(filteredTaskListIndices[i], taskToUnmark, unmarkedTask);
                compiledExecutionMessage.append(String.format(MESSAGE_UNMARK_TASK_SUCCESSFUL,
                        filteredTaskListIndices[i] + 1, this.taskToUnmark) + '\n');

            } catch (IllegalValueException | CommandException e) {
                // Moves on to next index even if current index execution is unsuccessful. CommandException thrown later
                executionIncomplete = true;
                e.printStackTrace();
                compiledExecutionMessage.append(String.format(MESSAGE_UNMARK_TASK_UNSUCCESSFUL,
                        filteredTaskListIndices[i] + 1) + '\n' + e.getMessage() + '\n');
            }
        }

        if (executionIncomplete) {
            if (multipleExectutions(filteredTaskListIndices)) {
                compiledExecutionMessage.insert(0, MESSAGE_INCOMPLETE_EXECUTION + '\n');
            }
            throw new CommandException(compiledExecutionMessage.toString());
        }

        return new CommandResult(compiledExecutionMessage.toString());
    }

    private void clearClassTaskVariables() {
        this.taskToUnmark = null;
        this.unmarkedTask = null;
    }

    private boolean multipleExectutions(int[] filteredTaskListIndices) {
        return (filteredTaskListIndices.length > 1) ? true : false;
    }

    private void checkIndexIsWithinBounds(int currIndex, UnmodifiableObservableList<ReadOnlyTask> lastShownList)
            throws IllegalValueException {
        if (currIndex >= lastShownList.size()) {
            throw new IllegalValueException(MESSAGE_INDEX_OUT_OF_BOUNDS);
        }
    }

    private void unmarkTaskAtIndex(int currIndex, UnmodifiableObservableList<ReadOnlyTask> lastShownList)
            throws CommandException, UniqueTaskList.DuplicateTaskException {
        this.taskToUnmark = getTaskToUnmark(currIndex, lastShownList);
        this.unmarkedTask = createUnmarkedCopyOfTask(this.taskToUnmark);

        updateTaskListAtIndex(currIndex, unmarkedTask);
    }

    private ReadOnlyTask getTaskToUnmark(int currIndex, UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        return lastShownList.get(currIndex);
    }


    private Task createUnmarkedCopyOfTask(ReadOnlyTask taskToUnmark) throws CommandException {
        assert taskToUnmark != null;

        checkCurrentTaskStatusIsDone(taskToUnmark);
        Task unmarkedTask = createUnmarkedTask(taskToUnmark);
        return unmarkedTask;
    }

    private void checkCurrentTaskStatusIsDone(ReadOnlyTask taskToUnmark) throws CommandException {
        if (taskToUnmark.getStatus() == TaskStatus.UNDONE) {
            throw new CommandException(MESSAGE_STATUS_ALREADY_UNDONE);
        }
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToMark} but with TaskStatus changed to Done
     * Assumes TaskStatus is not currently Done.
     */
    private Task createUnmarkedTask(ReadOnlyTask taskToUnmark) {
        Task unmarkedTask = new Task(taskToUnmark);
        unmarkedTask.setStatus(TaskStatus.UNDONE);
        return unmarkedTask;
    }

    private void updateTaskListAtIndex(int currIndex, Task unmarkedTask) throws UniqueTaskList.DuplicateTaskException {
        model.updateTask(currIndex, unmarkedTask);
    }

    private void storeUnmarkedTaskForUndo(int currIndex, ReadOnlyTask taskToUnmark, Task unmarkedTask) {
        //this.indexForUndoUnmark = currIndex;
        //this.markedTaskForUndoUnmark = new Task(taskToUnmark);
        this.taskToUnmarkList.push(new Task(taskToUnmark));
        this.unmarkedTaskList.push(unmarkedTask);
    }

    //@@author A0139845R

    @Override
    public void unexecute() {
        try {
            while (!unmarkedTaskList.isEmpty()) {
                model.deleteTask(unmarkedTaskList.pop());
                model.addTask(taskToUnmarkList.pop());
            }
            model.updateFilteredListToShowAll();

        } catch (DuplicateTaskException e) {

        } catch (TaskNotFoundException e) {

        }
    }

    @Override
    public void redo() {
        try {
            model.updateFilteredListToShowAll();
            this.execute();
        } catch (CommandException e) {
        }
    }
    //@@author

    @Override
    public String toString() {
        return COMMAND_WORD;
    }

}
