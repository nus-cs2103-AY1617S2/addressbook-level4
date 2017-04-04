package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

//@@author A0148038A
/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser {
   /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * and returns an ClearCommand object for execution.
     */
    public Command parse(String args) {
        String type = ParserUtil.parseClearType(args);
        if (!type.equals("") && !type.equals("ev") && !type.equals("ts")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        }

        return new ClearCommand(type);
    }
}
