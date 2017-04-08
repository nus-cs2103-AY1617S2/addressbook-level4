package seedu.tache.logic.commands;

import seedu.tache.commons.core.Messages;
import seedu.tache.commons.core.UnmodifiableObservableList;
import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.logic.commands.exceptions.CommandException;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.tache.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command implements Undoable {

    public enum TaskType { TypeTask, TypeDetailedTask }

    public static final String COMMAND_WORD = "delete";
    public static final String SHORT_COMMAND_WORD = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_PART_OF_RECURRING_TASK =
            "This task is part of a recurring task and cannot be edited.";
    //@@author A0150120H
    public static final String MESSAGE_DUPLICATE_TASK = "%1$s already exists in the task manager";
    //@@author

    public final int targetIndex;
    public final TaskType type;
    //@@author A0150120H
    private ReadOnlyTask taskToDelete;
    private int originalIndex;
    private boolean commandSuccess;
    //@@author

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.type = TaskType.TypeTask;
        commandSuccess = false;
    }

    public DeleteCommand(int targetIndex, TaskType type) {
        this.targetIndex = targetIndex;
        this.type = type;
        commandSuccess = false;
    }


    @Override
    public CommandResult execute() throws CommandException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToDelete = lastShownList.get(targetIndex - 1);

        try {
            checkPartOfRecurringTask(taskToDelete);
            originalIndex = model.getTaskManager().getTaskList().indexOf(taskToDelete);
            model.deleteTask(taskToDelete);
            commandSuccess = true;
            undoHistory.push(this);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        } catch (IllegalValueException e) {
            return new IncorrectCommand(e.getMessage()).execute();
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));

    }
    //@@author A0139925U
    private void checkPartOfRecurringTask(ReadOnlyTask taskToEdit) throws IllegalValueException {
        if (taskToEdit.getRecurState().isGhostRecurring()) {
            throw new IllegalValueException(MESSAGE_PART_OF_RECURRING_TASK);
        }
    }

    //@@author A0150120H
    @Override
    public boolean isUndoable() {
        return commandSuccess;
    }

    @Override
    public String undo() throws CommandException {
        try {
            assert taskToDelete instanceof Task;

            model.addTask(originalIndex, (Task) taskToDelete);
            return String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);
        } catch (DuplicateTaskException e) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_TASK, taskToDelete));
        }
    }
    //@@author

}
