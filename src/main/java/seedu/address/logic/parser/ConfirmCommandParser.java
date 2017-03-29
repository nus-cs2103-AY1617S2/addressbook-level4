package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ConfirmCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author A0162877N
/**
* Parses input arguments and creates a new ConfirmCommand object
*/
public class ConfirmCommandParser extends Parser {

    private static final String DELIMITER = " ";
    private static final int ARGUMENT_LENGTH = 2;
    private static final String REGEX_INDEX = "^[0-9]*";

    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer();
        argsTokenizer.tokenize(args);
        try {
            String[] arguments = argsTokenizer.getPreamble().get().trim().split(DELIMITER);
            if (arguments.length != ARGUMENT_LENGTH) {
                throw new CommandException(ConfirmCommand.MESSAGE_USAGE);
            }

            if (arguments[0].matches(REGEX_INDEX)
                    && arguments[1].matches(REGEX_INDEX)) { //matches 0-9 for the first 2 characters
                int filteredTaskListIndex = tryParseAsIndex(arguments[0]);
                int bookingSlotIndex = tryParseAsIndex(arguments[1]);
                if (filteredTaskListIndex > 0 && bookingSlotIndex > 0) {
                    return new ConfirmCommand(filteredTaskListIndex, bookingSlotIndex);
                } else {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfirmCommand.MESSAGE_USAGE));
                }
            } else {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfirmCommand.MESSAGE_USAGE));
            }
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfirmCommand.MESSAGE_USAGE));
        } catch (Exception e) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TASKS_DISPLAYED_INDEX);
        }
    }

    /**
     * Try parsing arguments as index
     */
    private int tryParseAsIndex(String args) {
        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (index.isPresent()) {
            return index.get();
        } else {
            return -1;
        }
    }
}
