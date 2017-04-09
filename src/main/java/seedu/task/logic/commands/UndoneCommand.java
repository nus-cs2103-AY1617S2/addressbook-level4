package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0139975J
public class UndoneCommand extends Command {

    public static final String COMMAND_WORD_1 = "undone";
    public static final String COMMAND_WORD_2 = "ud";

    public static final String MESSAGE_USAGE = COMMAND_WORD_1
            + ": the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD_1 + " 1";

    public static final String MESSAGE_UNDONE_TASK_SUCCESS = "UnDone Task: %1$s";

    public final int targetIndex;

    public UndoneCommand(int targetIndex) {
        this.targetIndex = targetIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {

        ReadOnlyTask updatedTaskDone = getTaskFromIndex(targetIndex);
        try {
            model.unDoneTask(targetIndex);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }


        return new CommandResult(String.format(MESSAGE_UNDONE_TASK_SUCCESS, updatedTaskDone));
    }

}
