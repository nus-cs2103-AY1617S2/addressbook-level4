package seedu.doit.logic.parser;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.IncorrectCommand;
import seedu.doit.logic.commands.SortCommand;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class SortCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        if (args.equals("priority")) {
            return new SortCommand(args);
        } else if (args.equals("deadline")) {
            return new SortCommand(args);
        } else if (args.equals("starttime")) {
            return new SortCommand(args);
        } else {
            return new IncorrectCommand(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}
