package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.RenameTagCommand;

/**
 * Parses input arguments and creates a new RenameTagCommand object
 */
public class RenameTagCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * RenameTagCommand and returns an RenameTagCommand object for execution.
     */
    public Command parse(String args) {
        // TODO: refactor into some kind of tokenizers
        String[] tagNames = args.trim().split(" ");
        try {
            if (tagNames.length != 2) {
                // TODO: Name this exception, unchecked
                throw new Exception(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameTagCommand.MESSAGE_USAGE));
            }
            return new RenameTagCommand(tagNames[0], tagNames[1]);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (Exception e) {
            return new IncorrectCommand(e.getMessage());
        }
    }
}
