//@@author A0139221N
package seedu.tasklist.logic.parser;

import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tasklist.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.tasklist.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.tasklist.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
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
    private boolean isByStatus = false;
    @SuppressWarnings("serial")
    private final Set<String> completed = new HashSet<String>() {{ add("completed"); }};
    @SuppressWarnings("serial")
    private final Set<String> notCompleted = new HashSet<String>() {{ add("not"); add("completed"); }};
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_TAG, PREFIX_STATUS);
        argsTokenizer.tokenize(args);
        Optional<String> keywordsHolder;
        keywordsHolder = argsTokenizer.getValue(PREFIX_TAG);
        isByTags = true;
        if (!keywordsHolder.isPresent()) {
            keywordsHolder = argsTokenizer.getValue(PREFIX_STATUS);
            isByStatus = true;
            isByTags = false;
        }
        if (!keywordsHolder.isPresent()) {
            keywordsHolder = Optional.of(args.trim());
            isByStatus = false;
            isByTags = false;
        }
        assert keywordsHolder.isPresent();
        Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(keywordsHolder.get().trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        if (isByTags) {
            return new FindCommand(keywordSet).isByTags();
        } else if (isByStatus) {
            if ((keywordSet.size() > 2 || keywordSet.size() < 1)
                && (!keywordSet.equals(completed) || !keywordSet.equals(notCompleted))) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            } else {
                return new FindCommand(keywordSet).isByStatus();
            }
        } else {
            return new FindCommand(keywordSet);
        }
    }
}
