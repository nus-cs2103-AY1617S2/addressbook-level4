package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ViewCommand;


/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     */
    public Command parse(String args) {
        String typeOfList = args.trim().toLowerCase();

        if (!ViewCommand.isValidCommand(typeOfList)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        return new ViewCommand(typeOfList);
    }

}

