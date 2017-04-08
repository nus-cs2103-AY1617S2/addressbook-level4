package seedu.watodo.logic.commands;

import java.util.Stack;

import seedu.watodo.commons.core.UnmodifiableObservableList;
import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.exceptions.CommandException;
import seedu.watodo.model.task.ReadOnlyTask;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.UniqueTaskList;
import seedu.watodo.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.watodo.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0141077L-reused
/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) [MORE_INDICES]...\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DELETE_UNDO_FAIL = "Could not undo delete due to duplicate."; //TODO merv to use

    private static final String MESSAGE_INCOMPLETE_EXECUTION = "Not all tasks sucessfully deleted.";
    public static final String MESSAGE_INDEX_OUT_OF_BOUNDS = "The task index provided is out of bounds.";
    public static final String MESSAGE_DELETE_TASK_SUCCESSFUL = "Task #%1$d deleted: %2$s";
    private static final String MESSAGE_DELETE_TASK_UNSUCCESSFUL = "Task #%1$d unsuccessfully deleted.";
    public static final String MESSAGE_STATUS_ALREADY_DONE = "The task status is already set to Done.";

    private int[] filteredTaskListIndices;
    private ReadOnlyTask taskToDelete;

    private Stack< Task > deletedTaskList;

    public DeleteCommand(int[] args) {
        this.filteredTaskListIndices = args;
        changeToZeroBasedIndexing();
        deletedTaskList = new Stack< Task >();
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
                deleteTaskAtIndex(filteredTaskListIndices[i], lastShownList);
                compiledExecutionMessage.append(String.format(MESSAGE_DELETE_TASK_SUCCESSFUL,
                        filteredTaskListIndices[i] + 1, this.taskToDelete) + '\n');

            } catch (IllegalValueException | TaskNotFoundException e) {
                // Moves on to next index even if current index execution is unsuccessful. CommandException thrown later
                executionIncomplete = true;
                e.printStackTrace();
                compiledExecutionMessage.append(String.format(MESSAGE_DELETE_TASK_UNSUCCESSFUL,
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
        this.taskToDelete = null;
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

    private void deleteTaskAtIndex(int currIndex, UnmodifiableObservableList<ReadOnlyTask> lastShownList)
            throws UniqueTaskList.TaskNotFoundException {
        this.taskToDelete = getTaskToDelete(currIndex, lastShownList);
        deleteTask(taskToDelete);
        storeTasksForUndo(taskToDelete);
    }

    private ReadOnlyTask getTaskToDelete(int currIndex, UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        return lastShownList.get(currIndex);
    }

    private void deleteTask(ReadOnlyTask taskToDelete) throws UniqueTaskList.TaskNotFoundException {
        model.deleteTask(taskToDelete);
    }

    private void storeTasksForUndo(ReadOnlyTask taskToDelete) {
        this.deletedTaskList.push(new Task(taskToDelete));
    }

    //@@author A0139845R-reused
    @Override
    public void unexecute() {
        assert model != null;

        try {
            while (!deletedTaskList.isEmpty()) {
                model.addTask(deletedTaskList.pop());
            }
            model.updateFilteredListToShowAll();

        } catch (DuplicateTaskException e) {

        }
    }

    @Override
    public void redo() {
        assert model != null;

        try {
            model.updateFilteredListToShowAll();
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException e) {

        }
    }

    //@@author

    @Override
    public String toString() {
        return COMMAND_WORD;
    }

}
