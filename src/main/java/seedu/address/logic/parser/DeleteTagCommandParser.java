package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.IncorrectCommand;

//@@author A0093999Y
/**
 * Parses input arguments and creates a new DeleteTagCommand object
 */
public class DeleteTagCommandParser {

    private static final int TAG_INDEX = 0;
    private static final int NUM_ARGUMENTS = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteTagCommand and returns an DeleteTagCommand object for execution.
     */
    public Command parse(String args) {
        // TODO: refactor into some kind of tokenizers
        String[] tagNames = args.trim().split(" ");
        try {
            if (tagNames.length != NUM_ARGUMENTS || tagNames[TAG_INDEX].equals("")) {
                // TODO: Name this exception, unchecked
                throw new Exception(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
            }
            return new DeleteTagCommand(tagNames[TAG_INDEX]);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (Exception e) {
            return new IncorrectCommand(e.getMessage());
        }
    }
}
