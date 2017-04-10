package seedu.doit.logic.parser;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.IncorrectCommand;
import seedu.doit.logic.commands.SortCommand;
//@@author A0146809W
/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements CommandParser {
    private static final String SORT_VALIDATION_REGEX = "(priority)|(deadline)|(start time)|(end time)|(name)";
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    @Override
    public Command parse(String args) {
        if (args.trim().matches(SORT_VALIDATION_REGEX)) {
            return new SortCommand(args.trim());
        } else {
            return new IncorrectCommand(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}
