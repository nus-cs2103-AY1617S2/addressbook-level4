package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskboss.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.HashMap;
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

    private static final String TYPE_KEYWORDS = "keywords";
    private static final String TYPE_START_DATE = "startDate";
    private static final String TYPE_END_DATE = "endDate";

    private static final HashMap<String, String> oneWordDays = new HashMap<String, String>();

    //@@author A0147990R
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws IllegalValueException
     */
    public Command parse(String args) throws IllegalValueException {

        String findType = parseType(args);
        initOneWordDay();

        if (findType.equals(EMPTY_STRING)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        Prefix inputPrefix = getInputPrefix(findType);

        try {
            ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(inputPrefix);
            argsTokenizer.tokenize(args);
            String keywords;
            String updatedKeywords;

            if (findType.equals(TYPE_KEYWORDS)) {
                keywords = argsTokenizer.getPreamble().get();
            } else {
                keywords = argsTokenizer.getValue(inputPrefix).get();
            }

            // only parse if input is (not only integers and not a single word) or (contains time)
            // so that user can also search for numeral day_of_month/year
            if ((findType.equals(TYPE_START_DATE) || findType.equals(TYPE_END_DATE)) &&
                    (keywords.replaceAll(DIGITS, EMPTY_STRING).length() > 0 || hasAmOrPm(keywords))) {
                updatedKeywords = parseFindDates(keywords);
            } else {
                updatedKeywords = keywords;
            }

            final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(updatedKeywords.trim());
            if (!matcher.matches()) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            return new FindCommand(findType, updatedKeywords);

        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

    }

    //@@author A0143157J
    /**
     * Returns a Natty-parsed String that represents the date that the keywords suggest.
     * Three possible cases:
     * 1. User only enters time (e.g 9am / 5.30pm)
     * 2. User only enters month (e.g feb / december)
     * 3. User enters a date regardless of the presence of time
     * @throws IllegalValueException
     */
    private String parseFindDates(String keywords) throws IllegalValueException {
        DateTime parsedFormattedDateTime = new DateTime(keywords);

        // user only enters time
        if (parsedFormattedDateTime.isDateInferred() &&
                !parsedFormattedDateTime.isTimeInferred()) {

            String extractedKeywords = parsedFormattedDateTime.value
                    .substring(INDEX_TIME_START_POSITION);
            keywords = extractedKeywords;

        // user only enters month
        } else if (!keywords.trim().contains(WHITESPACE) &&
                !oneWordDays.containsKey(keywords.toLowerCase())) {

            String extractedKeywords = parsedFormattedDateTime.value
                  .substring(INDEX_MONTH_START_POSITION, INDEX_MONTH_END_POSITION + 1);
            keywords = extractedKeywords;

        // user enters date with or without time
        } else {
            keywords = parsedFormattedDateTime.value;
        }
        return keywords;
    }

    /**
     * Returns true if keyword is a {@code String} time
     */
    private boolean hasAmOrPm(String keyword) {
        return (keyword.toLowerCase().contains("am") ||
                keyword.toLowerCase().contains("pm"));
    }

    /**
     * Helper function for parseFindDates().
     * Initializes the {@code oneWordDays} HashMap so that we can distinguish
     * in O(1) time if a one-word keyword is a natural language day
     * or a month
     */
    private void initOneWordDay() {
        oneWordDays.put("today", "today");
        oneWordDays.put("tomorrow", "tomorrow");
        oneWordDays.put("tmr", "tomorrow");
        oneWordDays.put("yesterday", "yesterday");
        oneWordDays.put("ytd", "yesterday");
        oneWordDays.put("now", "today");
    }

    //@@author A0147990R
    /**
     * Get the find type of user input
     */
    private String parseType(String args) {
        String input = args.trim();
        if (input.equals(EMPTY_STRING)) {
            return EMPTY_STRING;
        } else if (input.length() >= 3 &&
                input.substring(0, 3).equals("sd/")) {
            return TYPE_START_DATE;
        } else if (input.length() >= 3 &&
                input.substring(0, 3).equals("ed/")) {
            return TYPE_END_DATE;
        } else {
            return TYPE_KEYWORDS;
        }
    }

    /**
     * Get the input prefix according to the find type
     * @return PREFIX_START_DATE or PREFIX_END_DATE
     */
    private Prefix getInputPrefix(String findType) {
        if (findType.equals(TYPE_START_DATE)) {
            return PREFIX_START_DATE;
        } else {
            return PREFIX_END_DATE;
        }
    }

}
