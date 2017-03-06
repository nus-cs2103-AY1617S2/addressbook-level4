package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditLabelCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author A0140042A
/**
* Parses input arguments and creates a new EditLabelCommand object
*/
public class EditLabelCommandParser {

    private static final String DELIMITER = " ";
    private static final int ARGUMENT_NEW_LABEL_INDEX = 1;
    private static final int ARGUMENT_LABEL_TO_CHANGE_INDEX = 0;
    private static final int ARGUMENT_LENGTH = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditLabelCommand and returns an EditLabelCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer();
        argsTokenizer.tokenize(args);

        try {
            String[] arguments = argsTokenizer.getPreamble().get().split(DELIMITER);
            if (arguments.length != ARGUMENT_LENGTH) {
                throw new CommandException(EditLabelCommand.MESSAGE_USAGE);
            }

            return new EditLabelCommand(arguments[ARGUMENT_LABEL_TO_CHANGE_INDEX], arguments[ARGUMENT_NEW_LABEL_INDEX]);
        } catch (IllegalValueException e) {
            return new IncorrectCommand(String.format(EditLabelCommand.MESSAGE_LABEL_INVALID));
        } catch (Exception e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLabelCommand.MESSAGE_USAGE));
        }
    }
}
