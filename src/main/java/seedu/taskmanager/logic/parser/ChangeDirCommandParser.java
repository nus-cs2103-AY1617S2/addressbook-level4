package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmanager.logic.parser.CliSyntax.PATH_ARGS_FORMAT;

import java.util.regex.Matcher;

import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.ChangeDirCommand;
import seedu.taskmanager.logic.commands.IncorrectCommand;

public class ChangeDirCommandParser {
    /**
     * Parses the given {@code String} of path in the context of the ChangeDirCommand
     * and returns an ChangeDirCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = PATH_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeDirCommand.MESSAGE_USAGE));
        }
        return new ChangeDirCommand(args.trim());
    }
}
