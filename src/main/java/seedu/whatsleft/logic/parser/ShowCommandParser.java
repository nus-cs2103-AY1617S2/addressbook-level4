package seedu.whatsleft.logic.parser;

import static seedu.whatsleft.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.whatsleft.logic.commands.Command;
import seedu.whatsleft.logic.commands.IncorrectCommand;
import seedu.whatsleft.logic.commands.ShowCommand;

//@@author A0121668A
/**
 * Parses input arguments and creates a new ShowCommand object
 */
public class ShowCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowCommand
     * and returns an ShowCommand object for execution.
     */

    public Command parse(String args) {

        String status = ParserUtil.parseStatus(args);

        if (!status.equalsIgnoreCase("com") && !status.equalsIgnoreCase("pend") && !status.equals("")) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
        }

        return new ShowCommand(status.toLowerCase());
    }
}
