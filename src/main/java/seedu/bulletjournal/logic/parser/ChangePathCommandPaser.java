package seedu.bulletjournal.logic.parser;

import static seedu.bulletjournal.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bulletjournal.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.bulletjournal.logic.commands.Command;
import seedu.bulletjournal.logic.commands.FindCommand;
import seedu.bulletjournal.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ChangePathCommandPaser {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangePathCommand
     * and returns an ChangePathCommand object for execution.
     */
    public Command parse(String args) {
        return new ChangePathCommand(args);
    }

}
