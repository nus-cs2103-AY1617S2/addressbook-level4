//@@author A0163744B
package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;

/**
 * Marks a task identified using it's last displayed index from the task list as uncomplete.
 */
public class UncompleteCommand extends CompletionCommand {

    public static final String COMMAND_WORD = "uncomplete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the task identified "
            + "by the index number used in the last task listing as uncomplete. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNCOMPLETE_TASK_SUCCESS = "Task: %1$s marked uncomplete";

    public static final String MESSAGE_TASK_ALREADY_UNCOMPLETE = "Task already marked uncomplete.";

    public static final boolean SHOULD_MARK_TASK_COMPLETE = false;

    public UncompleteCommand(int targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute() throws CommandException {
        return executeCompletion(
                SHOULD_MARK_TASK_COMPLETE, MESSAGE_TASK_ALREADY_UNCOMPLETE, MESSAGE_UNCOMPLETE_TASK_SUCCESS
        );
    }
}
