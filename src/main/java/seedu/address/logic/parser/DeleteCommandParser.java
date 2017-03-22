package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteLabelCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author A0140042A
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {

    private static final String DELIMITER = " ";
    private static final int ARGUMENT_LABEL_TO_DELETE_INDEX = 0;
    private static final int ARGUMENT_LENGTH = 1;
    private static final String REGEX_INDEX = "^[0-9\\+\\-][0-9]*$";

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer();
        argsTokenizer.tokenize(args);

        try {
            String[] arguments = argsTokenizer.getPreamble().get().split(DELIMITER);
            if (arguments.length != ARGUMENT_LENGTH) {
                throw new CommandException(DeleteCommand.MESSAGE_USAGE);
            }

            String parameter = arguments[ARGUMENT_LABEL_TO_DELETE_INDEX];
            if (parameter.matches(REGEX_INDEX)) { //matches 0-9 or + or - for the first character
                return tryParseAsIndex(args);
            } else {
                return new DeleteLabelCommand(arguments[ARGUMENT_LABEL_TO_DELETE_INDEX]);
            }
        } catch (Exception e) { }
        return new IncorrectCommand(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    /**
     * Try parsing arguments as index
     */
    private Command tryParseAsIndex(String args) {
        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (index.isPresent()) {
            return new DeleteCommand(index.get());
        } else {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

}
