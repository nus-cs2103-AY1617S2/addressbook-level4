//@@author A0139248X
package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.IncorrectCommand;
import seedu.ezdo.logic.commands.KillCommand;

/**
 * Parses input arguments and creates a new KillCommand object
 */
public class KillCommandParser implements CommandParser {

    private static final String INDEX_ZERO = "0";

    /**
     * Parses the given {@code String} of arguments in the context of the KillCommand
     * and returns a KillCommand object for execution.
     */
    @Override
    public Command parse(String args) {
        final boolean isIndexZero = args.trim().equals(INDEX_ZERO);
        if (args.isEmpty() || isIndexZero) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, KillCommand.MESSAGE_USAGE));
        }
        ArrayList<Integer> indexes = ParserUtil.parseIndexes(args);
        if (indexes.isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, KillCommand.MESSAGE_USAGE));
        }
        return new KillCommand(indexes);
    }
}
