package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {

    public static final String NO_ARGUMENT_ERROR = "Find command found no arguments";
    public static final String DATE_FORMAT_ERROR = "Wrong date time format";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindCommand and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String key = matcher.group("keywords");
        final String[] keywords = key.split("\\s+", 2);
        if (keywords.length == 0) {
            return new IncorrectCommand(NO_ARGUMENT_ERROR);
        }
        final Set<String> keywordSet;
        List<DateGroup> dates;
        switch (keywords[0].trim()) {
        case CliSyntax.FIND_NAME:
            keywordSet = new HashSet<>(Arrays.asList(keywords));
            return new FindCommand(keywordSet, null, null);
        case CliSyntax.FIND_DEADLINE:
            dates = new PrettyTimeParser()
                    .parseSyntax(ParserUtil.correctDateFormat(key));
            if (dates.get(0).getText().equals(keywords[1].trim())) {
                return new FindCommand(null, dates, null);
            }
            break;
        case CliSyntax.FIND_TAG:
            keywordSet = new HashSet<>(Arrays.asList(keywords));
            return new FindCommand(null, null, keywordSet);
        }
        keywordSet = new HashSet<>(Arrays.asList(keywords));
        dates = new PrettyTimeParser().parseSyntax(key);
        return new FindCommand(keywordSet, dates, keywordSet);
    }

}
