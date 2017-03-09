package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEINTERVAL;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindCommand and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        
        try {
            if (args.trim().contains("from ")) {
                ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_TIMEINTERVAL);
                argsTokenizer.tokenize(args);
                String[] dateArr = argsTokenizer.getValue(PREFIX_TIMEINTERVAL).get().split(" ");
                List<Date> startDateList = new DateTimeParser().parse(dateArr[0]).get(0).getDates();
                List<Date> endDateList = new DateTimeParser().parse(dateArr[1]).get(0).getDates();
                return new FindCommand(startDateList.get(0), endDateList.get(0));
            }
        } catch (Exception e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

}
