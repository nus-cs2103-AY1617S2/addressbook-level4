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

//@@author A0141077L
/**
 * Marks a task identified using its last displayed index from the task manager
 * as completed.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the status of the task identified to Done, "
            + "using the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) [MORE_INDICES]...\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    private static final String MESSAGE_INCOMPLETE_EXECUTION = "Not all tasks sucessfully marked.";
    public static final String MESSAGE_INDEX_OUT_OF_BOUNDS = "The task index provided is out of bounds.";
    public static final String MESSAGE_MARK_TASK_SUCCESSFUL = "Task #%1$d completed: %2$s";
    private static final String MESSAGE_MARK_TASK_UNSUCCESSFUL = "Task #%1$d unsuccessfully marked as complete.";
    public static final String MESSAGE_STATUS_AlREADY_DONE = "The task status is already set to Done.";

    private int[] filteredTaskListIndices;
  private ReadOnlyTask taskToMark;
  private Task markedTask;

    private Stack< Task > taskToMarkList;
    private Stack< Task > markedTaskList;

    //private int indexForUndoMark;
    //private Task unmarkedTaskForUndoMark;

    public MarkCommand(int[] args) {
        this.filteredTaskListIndices = args;
        changeToZeroBasedIndexing();
        taskToMarkList = new Stack< Task >();
        markedTaskList = new Stack< Task >();
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
                markTaskAtIndex(filteredTaskListIndices[i], lastShownList);
                compiledExecutionMessage.append(
                        String.format(MESSAGE_MARK_TASK_SUCCESSFUL, filteredTaskListIndices[i]+1, this.taskToMark) + '\n');

            } catch (IllegalValueException | CommandException e) {
                // Moves on to next index even if execution of current index is unsuccessful. CommandException thrown later.
                executionIncomplete = true;
                e.printStackTrace();
                compiledExecutionMessage.append(String.format(MESSAGE_MARK_TASK_UNSUCCESSFUL, filteredTaskListIndices[i]+1)
                        + '\n' + e.getMessage() + '\n');
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
        this.taskToMark = null;
        this.markedTask = null;
    }

    private boolean multipleExectutions(int[] filteredTaskListIndices) {
        return (filteredTaskListIndices.length > 1) ? true : false;
    }

    private void checkIndexIsWithinBounds(int currIndex, UnmodifiableObservableList<ReadOnlyTask> lastShownList) throws IllegalValueException {
        if (currIndex >= lastShownList.size()) {
            throw new IllegalValueException(MESSAGE_INDEX_OUT_OF_BOUNDS);
        }
    }

    private void markTaskAtIndex(int currIndex, UnmodifiableObservableList<ReadOnlyTask> lastShownList)
            throws CommandException, UniqueTaskList.DuplicateTaskException {
        this.taskToMark = getTaskToMark(currIndex, lastShownList);
        this.markedTask = createMarkedCopyOfTask(this.taskToMark);

        storeUnmarkedTaskForUndo(this.taskToMark, this.markedTask);

        updateTaskListAtIndex(currIndex, markedTask);
    }

    private ReadOnlyTask getTaskToMark(int currIndex, UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        return lastShownList.get(currIndex);
    }


    private Task createMarkedCopyOfTask(ReadOnlyTask taskToMark) throws CommandException {
        assert taskToMark != null;

        checkCurrentTaskStatusIsUndone(taskToMark);
        Task markedTask = createMarkedTask(taskToMark);
        return markedTask;
    }

    private void checkCurrentTaskStatusIsUndone(ReadOnlyTask taskToMark) throws CommandException {
        if (taskToMark.getStatus() == TaskStatus.DONE) {
            throw new CommandException(MESSAGE_STATUS_AlREADY_DONE);
        }
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToMark} but with TaskStatus changed to Done
     * Assumes TaskStatus is not currently Done.
     */
    private Task createMarkedTask(ReadOnlyTask taskToMark) {
        Task markedTask = new Task(taskToMark);
        markedTask.setStatus(TaskStatus.DONE);
        return markedTask;
    }

    private void updateTaskListAtIndex(int currIndex, Task markedTask) throws UniqueTaskList.DuplicateTaskException{
        model.updateTask(currIndex, markedTask);
    }

    private void storeUnmarkedTaskForUndo(ReadOnlyTask taskToMark, Task markedTask) {
        //this.indexForUndoMark = currIndex;

        //this.unmarkedTaskForUndoMark = new Task(taskToMark);
        this.taskToMarkList.push(new Task(taskToMark));
        this.markedTaskList.push(markedTask);
    }

    //@@author A0139845R

    @Override
    public void unexecute() {
        try {
            model.updateFilteredListToShowAll();
            while (!taskToMarkList.isEmpty()) {
                model.deleteTask(markedTaskList.pop());
                model.addTask(taskToMarkList.pop());
            }

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
