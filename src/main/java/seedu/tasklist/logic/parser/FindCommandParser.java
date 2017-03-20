package seedu.tasklist.logic.parser;

import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tasklist.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.tasklist.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.tasklist.logic.commands.Command;
import seedu.tasklist.logic.commands.FindCommand;
import seedu.tasklist.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {
    private boolean isByTags = false;
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_TAG);
        argsTokenizer.tokenize(args);
        Matcher matcher;
        try {
            matcher = KEYWORDS_ARGS_FORMAT.matcher(argsTokenizer.getValue(PREFIX_TAG).get().trim());
            isByTags = true;
        } catch (NoSuchElementException nsee) {
            matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        }
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        if (isByTags) {
            return new FindCommand(keywordSet).isByTags();
        } else {
            return new FindCommand(keywordSet);
        }
    }
}
