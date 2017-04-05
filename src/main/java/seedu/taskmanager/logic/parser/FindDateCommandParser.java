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
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDateCommand.MESSAGE_USAGE));
        }
        try {
            return new FindDateCommand(args.trim());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
// @@author
