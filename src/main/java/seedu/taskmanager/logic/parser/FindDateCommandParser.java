package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmanager.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.regex.Matcher;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.FindDateCommand;
import seedu.taskmanager.logic.commands.IncorrectCommand;

// @@author A0140032E
/**
 * Parses input arguments and creates a new FindDateCommand object
 */
public class FindDateCommandParser {
    private static final int MIN_NO_OF_DATES = 1;
    private static final int MAX_NO_OF_DATES = 2;

    public Command parse(String args) {

        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDateCommand.MESSAGE_USAGE));
        }
        args = args.trim();
        String[] dates = args.split(" to ");
        if (dates.length < MIN_NO_OF_DATES || dates.length > MAX_NO_OF_DATES) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDateCommand.MESSAGE_USAGE));
        }
        try {
            if (dates.length == 1) {
                return new FindDateCommand(dates[0]);
            } else {
                return new FindDateCommand(dates[0], dates[1]);
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
// @@author
