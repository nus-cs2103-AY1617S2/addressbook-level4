//@@author A0138377U
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class ClearCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * and returns an ClearCommand object for execution.
     */
    public Command parse(String para) {

        para = para.trim();
        if (!"done".equals(para) && !"all".equals(para)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        }

        return new ClearCommand(para);
    }

}
