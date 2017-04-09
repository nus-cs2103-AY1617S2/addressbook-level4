package seedu.today.logic.parser;

import static seedu.today.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.today.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;

import seedu.today.logic.commands.Command;
import seedu.today.logic.commands.FindCommand;
import seedu.today.logic.commands.IncorrectCommand;

//@@author A0144422R
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
            return new IncorrectCommand(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        // keywords delimited by whitespace
        final String key = matcher.group("keywords");
        final String[] keywords = key.split("\\s+", 2);
        if (keywords.length == 0) {
            return new IncorrectCommand(NO_ARGUMENT_ERROR);
        }
        final Set<String> keywordSet;
        List<DateGroup> dates;
        if (keywords[0].equals(CliSyntax.FIND_DEADLINE)) {
            dates = new PrettyTimeParser().parseSyntax(ParserUtil.correctDateFormat(key));
            if (dates.get(0).getText().equals(keywords[1].trim())) {
                return new FindCommand(null, dates, null);
            }
        }
        keywordSet = new HashSet<>(Arrays.asList(key.split("\\s+")));
        dates = new PrettyTimeParser().parseSyntax(key);
        return new FindCommand(keywordSet, dates, keywordSet);
    }

}
