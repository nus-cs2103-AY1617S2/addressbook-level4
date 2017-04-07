//@@author A0139539R
package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;

public class ShowAliasCommand extends Command {

    public static final String COMMAND_WORD = "aliases";
    public static final String MESSAGE_SUCCESS = "Listed all aliases:\n%1$s";

    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult(String.format(MESSAGE_SUCCESS, model.getCommandMapString()));
    }
}
