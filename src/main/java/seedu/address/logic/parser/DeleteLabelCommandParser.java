package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteLabelCommand;
import seedu.address.logic.commands.EditLabelCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author A0140042A
/**
* Parses input arguments and creates a new DeleteLabelCommand object
*/
public class DeleteLabelCommandParser {

    private static final String DELIMITER = " ";
    private static final int ARGUMENT_LABEL_TO_DELETE_INDEX = 0;
    private static final int ARGUMENT_LENGTH = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteLabelCommand and returns a DeleteLabelCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer();
        argsTokenizer.tokenize(args);

        try {
            String[] arguments = argsTokenizer.getPreamble().get().split(DELIMITER);
            if (arguments.length != ARGUMENT_LENGTH) {
                throw new CommandException(EditLabelCommand.MESSAGE_USAGE);
            }

            return new DeleteLabelCommand(arguments[ARGUMENT_LABEL_TO_DELETE_INDEX]);
        } catch (IllegalValueException e) {
            return new IncorrectCommand(String.format(DeleteLabelCommand.MESSAGE_LABEL_INVALID));
        } catch (Exception e) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLabelCommand.MESSAGE_USAGE));
        }
    }
}
