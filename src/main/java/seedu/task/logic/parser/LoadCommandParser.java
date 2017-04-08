package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.PATH_ARGS_FORMAT;

import java.util.regex.Matcher;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.LoadCommand;

public class LoadCommandParser {
    /**
     * Parses the given {@code String} in the context of the PathCommand and returns a PathCommand
     * object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = PATH_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String path = matcher.group("keyword");
        return new LoadCommand(path);
    }

}
