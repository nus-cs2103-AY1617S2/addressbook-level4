package seedu.whatsleft.logic.commands;

import seedu.whatsleft.logic.commands.exceptions.CommandException;
//@@author A0148038A
/**
 * Represents an incorrect command. Upon execution, throws a CommandException with feedback to the user.
 */
public class IncorrectCommand extends Command {

    public final String feedbackToUser;

    public IncorrectCommand(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.storePreviousCommand("");
        throw new CommandException(feedbackToUser);
    }

}

