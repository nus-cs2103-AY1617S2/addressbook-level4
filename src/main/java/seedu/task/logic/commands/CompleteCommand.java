package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;

/**
 * Completes a task identified using it's last displayed index from the task list.
 */
public class CompleteCommand extends CompletionCommand {

    public CompleteCommand(int targetIndex) {
        super(targetIndex);
    }

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the task identified "
            + "by the index number used in the last task listing as complete. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Completed Task: %1$s, congratulations";

    public static final String MESSAGE_TASK_ALREADY_COMPLETED = "You have already completed this task.";

    public static final boolean SHOULD_MARK_TASK_COMPLETE = true;


    @Override
    public CommandResult execute() throws CommandException {
        return executeCompletion(
                SHOULD_MARK_TASK_COMPLETE, MESSAGE_TASK_ALREADY_COMPLETED, MESSAGE_COMPLETE_TASK_SUCCESS
        );
    }

}

