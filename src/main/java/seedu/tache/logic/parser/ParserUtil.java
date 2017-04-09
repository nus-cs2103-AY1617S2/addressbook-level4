package seedu.tache.logic.parser;

import static seedu.tache.logic.parser.CliSyntax.PARAMETER_END_DATE;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_END_TIME;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_NAME;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_RECUR_INTERVAL;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_RECUR_STATUS;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_START_DATE;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_START_TIME;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_TAG;

import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.commons.util.StringUtil;
import seedu.tache.model.recurstate.RecurState;
import seedu.tache.model.recurstate.RecurState.RecurInterval;
import seedu.tache.model.tag.Tag;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.DateTime;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {
    //@@author A0139925U
    private static final Pattern FORMAT_INDEX_ARGS = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern FORMAT_NAME = Pattern.compile("^\".+\"");
    private static final Pattern FORMAT_DATE = Pattern.compile("^[0-3]?[0-9]/[0-1]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"
                                                               + "|^[0-3]?[0-9]-[0-1]?[0-9]-(?:[0-9]{2})?[0-9]{2}$"
                                                               + "|^[0-3]{1}[0-9]{1}[0-1]{1}[0-9]{1}"
                                                               + "(?:[0-9]{2})?[0-9]{2}$");
    private static final Pattern FORMAT_TIME = Pattern.compile("^[0-2][0-9][0-5][0-9]|^([0-1][0-2]|[0-9])"
                                                               + "([.][0-5][0-9])?\\s?(am|pm){1}");
    private static final Pattern FORMAT_DURATION = Pattern.compile("^\\d+\\s?((h|hr|hrs)|(m|min|mins))");
    //@@author

    //@@author A0150120H
    static enum DateTimeType { START, END, UNKNOWN };
    public static final String[] START_DATE_IDENTIFIER = {"from"};
    public static final String[] END_DATE_IDENTIFIER = {"to", "on", "by", "before"};
    //@@author

    /**
     * Returns the specified index in the {@code command} if it is a positive unsigned integer
     * Returns an {@code Optional.empty()} otherwise.
     */
    public static Optional<Integer> parseIndex(String command) {
        final Matcher matcher = FORMAT_INDEX_ARGS.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses {@code Collection<String> tags} into an {@code UniqueTagList}.
     */
    public static UniqueTagList parseTags(Collection<String> tags) throws IllegalValueException {
        assert tags != null;
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return new UniqueTagList(tagSet);
    }
    //@@author A0139925U
    /**
     * Returns True if input is a valid parameter
     * Returns False otherwise.
     */
    public static boolean isValidParameter(String input) {
        return ParserUtil.isFoundIn(input, PARAMETER_NAME, PARAMETER_START_DATE, PARAMETER_END_DATE,
                PARAMETER_START_TIME, PARAMETER_END_TIME, PARAMETER_TAG,
                PARAMETER_RECUR_INTERVAL, PARAMETER_RECUR_STATUS);
    }

    /**
     * Parses a string into a valid RecurInterval enum reference
     * @param input String to parse
     * @return valid RecurInterval enum reference if input is valid, throws IllegalValueException otherwise
     */
    public static RecurInterval parseStringToRecurInterval(String input) throws IllegalValueException {
        RecurInterval stringToEnum = null;
        switch (input.trim().toLowerCase()) {
        case "none":
            stringToEnum = RecurInterval.NONE;
            break;
        case "day":
            stringToEnum = RecurInterval.DAY;
            break;
        case "week":
            stringToEnum = RecurInterval.WEEK;
            break;
        case "month":
            stringToEnum = RecurInterval.MONTH;
            break;
        case "year":
            stringToEnum = RecurInterval.YEAR;
            break;
        }
        if (stringToEnum == null) {
            throw new IllegalValueException(RecurState.MESSAGE_RECUR_INTERVAL_CONSTRAINTS);
        }
        return stringToEnum;
    }
    //@@author
    /**
     * Returns number of date parameters in input.
     */
    public static boolean hasDate(String input) {
        try {
            parseDate(input);
            return true;
        } catch (IllegalValueException ex) {
            return false;
        }
    }

    /**
     * Returns number of time parameters in input.
     */
    public static boolean hasTime(String input) {
        try {
            parseTime(input);
            return true;
        } catch (IllegalValueException ex) {
            return false;
        }
    }
    //@@author A0150120H
    /**
     * Returns the first time String encountered
     */
    public static String parseTime(String input) throws IllegalValueException {
        String[] inputs = input.split(" ");
        for (String candidate : inputs) {
            Matcher matcher = FORMAT_TIME.matcher(candidate.trim());
            if (matcher.lookingAt()) {
                return matcher.group();
            }
        }
        throw new IllegalValueException("Invalid Input");
    }

    /**
     * Returns the first date String encountered
     */
    public static String parseDate(String input) throws IllegalValueException {
        String[] inputs = input.split(" ");
        for (String candidate : inputs) {
            Matcher matcher = FORMAT_DATE.matcher(candidate.trim());
            if (matcher.lookingAt()) {
                return matcher.group();
            }
        }
        throw new IllegalValueException("Invalid Input");
    }

    /**
     * Checks if the given String is a start date identifier
     * @param s String to check
     * @return true if it's a start date identifier, false otherwise
     */
    public static boolean isStartDateIdentifier(String s) {
        return isFoundIn(s, START_DATE_IDENTIFIER);
    }

    /**
     * Checks if the given String is an end date identifier
     * @param s String to check
     * @return true if it's a start date identifier, false otherwise
     */
    public static boolean isEndDateIdentifier(String s) {
        return isFoundIn(s, END_DATE_IDENTIFIER);
    }

    /**
     * Looks for all possible date/time strings based on identifiers
     * @param input String to parse
     * @return Deque of PossibleDateTime objects, each representing a possible date/time string
     */
    public static Deque<PossibleDateTime> parseDateTimeIdentifiers(String input) {
        String[] inputs = input.split(" ");
        Deque<PossibleDateTime> result = new LinkedList<PossibleDateTime>();
        PossibleDateTime current = new PossibleDateTime(PossibleDateTime.INVALID_INDEX, DateTimeType.UNKNOWN);
        for (int i = 0; i < inputs.length; i++) {
            String word = inputs[i];
            if (isStartDateIdentifier(word)) {
                result.push(current);
                current = new PossibleDateTime(i, DateTimeType.START);
            } else if (isEndDateIdentifier(word)) {
                result.push(current);
                current = new PossibleDateTime(i, DateTimeType.END);
            } else {
                current.appendDateTime(i, word);
            }
        }
        result.push(current);
        return result;
    }

    /**
     * Class to describe a date/time String that was found
     *
     */
    static class PossibleDateTime {
        int startIndex;
        int endIndex;
        String data;
        DateTimeType type;

        static final int INVALID_INDEX = -1;

        PossibleDateTime(int index, DateTimeType type) {
            this.startIndex = index;
            this.endIndex = index;
            this.type = type;
            this.data = new String();
        }

        void appendDateTime(int index, String data) {
            this.data += " " + data;
            this.endIndex = index;
        }
    }

    public static boolean canParse(String s) {
        return DateTime.canParse(s);
    }

    public static boolean isTime(String s) {
        return DateTime.isTime(s);
    }

    public static boolean isDate(String s) {
        return DateTime.isDate(s);
    }
    /**
     * Checks if the given string exists in any of the arrays in strArrays. Check is case insensitive
     * @param s String to check
     * @param strArrays Arrays of Strings to check against
     * @return true if s is in strArray, false otherwise
     */
    public static boolean isFoundIn(String s, String[]... strArrays) {
        for (String[] strArray: strArrays) {
            for (String str: strArray) {
                if (s.equalsIgnoreCase(str)) {
                    return true;
                }
            }
        }
        return false;
    }

}
