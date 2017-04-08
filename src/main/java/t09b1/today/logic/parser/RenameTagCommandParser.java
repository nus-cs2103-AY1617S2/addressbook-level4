package t09b1.today.logic.parser;

import static t09b1.today.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import t09b1.today.commons.exceptions.IllegalValueException;
import t09b1.today.logic.commands.Command;
import t09b1.today.logic.commands.IncorrectCommand;
import t09b1.today.logic.commands.RenameTagCommand;

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
                throw new Exception(MESSAGE_INVALID_COMMAND_FORMAT);
            }
            return new RenameTagCommand(tagNames[OLDTAG_INDEX], tagNames[NEWTAG_INDEX]);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (Exception e) {
            return new IncorrectCommand(e.getMessage());
        }
    }
}
