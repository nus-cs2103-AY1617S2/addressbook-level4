//@@author A0144813J
package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model.StateLimitReachedException;

/**
 * Redo all state-changing commands in the session.
 */
public class UnrevertCommand extends Command {

    public static final String COMMAND_WORD = "unrevert";

    public static final String MESSAGE_SUCCESS = "Unreverted";

    /**
     * Creates an Unrevert command.
     */
    public UnrevertCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.setAddressBookStateToFrontier();
        } catch (StateLimitReachedException e) {
            throw new CommandException(RedoCommand.MESSAGE_NO_FORWARDS_COMMAND);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

}
