package seedu.onetwodo.logic.parser;

import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.logic.commands.Command;
import seedu.onetwodo.logic.commands.IncorrectCommand;

//@@author A0135739W
/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * and returns an ClearCommand object for execution.
     */
    public Command parse(String args) {
        String argsTrimmed = args.trim();
        switch (argsTrimmed.toLowerCase()) {
        case "":
        case "all":
            return new ClearCommand("all");

        case "done":
            return new ClearCommand("done");

        default:
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        }
    }
}
