package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.regex.Matcher;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.FindTagCommand;
import seedu.task.logic.commands.IncorrectCommand;

//@@author A0139322L
public class FindTagCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindTagCommand and returns a FindTagCommand object for execution
     */

    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                 String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                               FindTagCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        return new FindTagCommand(args);
    }
}
//@@author
