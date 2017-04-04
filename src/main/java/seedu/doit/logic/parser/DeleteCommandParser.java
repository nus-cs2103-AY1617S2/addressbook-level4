//@@author A0146809W
package seedu.doit.logic.parser;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;
import java.util.Set;

import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.DeleteCommand;
import seedu.doit.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements CommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    @Override
    public Command parse(String args) {

        Set<Integer> indexes = ParserUtil.parseIndexes(args);
        if (indexes.isEmpty()) {
            return new IncorrectCommand(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(indexes);
    }

}
