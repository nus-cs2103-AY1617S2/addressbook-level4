package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.RenameTagCommand;

//@@author A0093999Y
/**
 * Parses input arguments and creates a new RenameTagCommand object
 */
public class RenameTagCommandParser {

    private static final int OLDTAG_INDEX = 0;
    private static final int NEWTAG_INDEX = 1;
    private static final int NUM_ARGUMENTS = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * RenameTagCommand and returns an RenameTagCommand object for execution.
     */
    public Command parse(String args) {
        // TODO: refactor into some kind of tokenizers
        String[] tagNames = args.trim().split(" ");
        try {
            if (tagNames.length != NUM_ARGUMENTS) {
                // TODO: Name this exception, unchecked
                throw new Exception(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameTagCommand.MESSAGE_USAGE));
            }
            return new RenameTagCommand(tagNames[OLDTAG_INDEX], tagNames[NEWTAG_INDEX]);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (Exception e) {
            return new IncorrectCommand(e.getMessage());
        }
    }
}
