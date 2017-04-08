package seedu.today.logic.commands;

import seedu.today.logic.commands.exceptions.CommandException;

/**
 * Represents an incorrect command. Upon execution, throws a CommandException
 * with feedback to the user.
 */
public class IncorrectCommand extends Command {

    private final String feedbackToUser;

    public IncorrectCommand(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(feedbackToUser);
    }

}
