package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskboss.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_KEYWORD;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.NoSuchElementException;
import java.util.regex.Matcher;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.FindCommand;
import seedu.taskboss.logic.commands.IncorrectCommand;
import seedu.taskboss.logic.parser.ArgumentTokenizer.Prefix;
import seedu.taskboss.model.task.DateTime;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {

    private static final String DIGITS = "\\d";
    private static final String EMPTY_STRING = "";
    private static final String WHITESPACE = " ";

    private static final int INDEX_TIME_START_POSITION = 13;
    private static final int INDEX_MONTH_START_POSITION = 0;
    private static final int INDEX_MONTH_END_POSITION = 2;

    private static final String prefixStartDate = PREFIX_START_DATE.getPrefix();
    private static final String prefixEndDate = PREFIX_END_DATE.getPrefix();
    private static final String prefixKeyword = PREFIX_KEYWORD.getPrefix();

    //@@author A0147990R
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws IllegalValueException
     */
    public Command parse(String args) throws IllegalValueException {

        String inputprefix = parsePrefix(args);
        String pre;

        if (inputprefix.equals(EMPTY_STRING)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        Prefix inputPrefix;
        if (inputprefix.equals(prefixKeyword)) {
            inputPrefix = PREFIX_KEYWORD;
            pre = PREFIX_KEYWORD.toString();
        } else if (inputprefix.equals(prefixStartDate)) {
            inputPrefix = PREFIX_START_DATE;
            pre = PREFIX_START_DATE.toString();
        } else if (inputprefix.equals(prefixEndDate)) {
            inputPrefix = PREFIX_END_DATE;
            pre = PREFIX_END_DATE.toString();
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        try {
            ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(inputPrefix);
            argsTokenizer.tokenize(args);
            String keywords = argsTokenizer.getValue(inputPrefix).get();

            //@@author A0143157J
            // only parse with natty if input is (not only integers and not a single word) or (contains time)
            // so that user can also search for numeral day_of_month/year
            if ((inputPrefix == PREFIX_START_DATE || inputPrefix == PREFIX_END_DATE) &&
                    (keywords.replaceAll(DIGITS, EMPTY_STRING).length() > 0 || hasAmOrPm(keywords))) {

                DateTime parsedFormattedDateTime = new DateTime(keywords);

                // user only enters time
                if (parsedFormattedDateTime.isDateInferred() &&
                        !parsedFormattedDateTime.isTimeInferred()) {
                    String extractedKeywords = parsedFormattedDateTime.value
                            .substring(INDEX_TIME_START_POSITION);
                    keywords = extractedKeywords;
                // user only enters month
                } else if (!keywords.trim().contains(WHITESPACE)) {
                    String extractedKeywords = parsedFormattedDateTime.value
                          .substring(INDEX_MONTH_START_POSITION, INDEX_MONTH_END_POSITION + 1);
                    keywords = extractedKeywords;
                // user enters date with or without time
                } else {
                    keywords = parsedFormattedDateTime.value;
                }
            }

            //@@author A0147990R
            final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(keywords.trim());
            if (!matcher.matches()) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            return new FindCommand(pre, keywords);

        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

    }

    //@@author A0143157J
    // Returns true if keyword is a String time
    private boolean hasAmOrPm(String keyword) {
        return (keyword.contains("am") || keyword.contains("AM") ||
                keyword.contains("pm") || keyword.contains("PM"));
    }

    //@@author A0147990R
    private String parsePrefix(String args) {
        int prefixIndex = args.indexOf("/");
        if (prefixIndex == -1) {
            return EMPTY_STRING;
        } else {
            return args.substring(1, prefixIndex + 1);
        }
    }

}
