package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.IncorrectCommand;
import seedu.taskmanager.logic.commands.SortCommand;

// @@author A0131278H
/**
 * Parses input arguments and creates a new SortCommand object
 */

public class SortCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * SortCommand and returns an SortCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        try {
            if (!matcher.matches()) {
                // sort by end dates if no keywords are entered
                return new SortCommand(SortCommand.SORT_KEYWORD_OTHERS);
            }

            // keywords delimited by whitespace
            final String[] keywords = matcher.group("keywords").split("\\s+");
            final List<String> keywordList = Arrays.asList(keywords);

            // only the first keyword is processed
            return new SortCommand(keywordList.get(0));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
// @@author
