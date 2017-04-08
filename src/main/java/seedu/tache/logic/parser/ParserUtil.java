package seedu.tache.logic.parser;

import static seedu.tache.logic.parser.CliSyntax.PARAMETER_END_DATE;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_END_DATE_2;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_END_DATE_3;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_END_TIME;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_END_TIME_2;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_END_TIME_3;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_NAME;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_NAME_2;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_START_DATE;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_START_DATE_2;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_START_DATE_3;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_START_TIME;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_START_TIME_2;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_START_TIME_3;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_TAG;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_TAG_2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.commons.util.StringUtil;
import seedu.tache.model.tag.Tag;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.DateTime;
import seedu.tache.model.task.Name;


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
     * Returns a new Set populated by all elements in the given list of strings
     * Returns an empty set if the given {@code Optional} is empty,
     * or if the list contained in the {@code Optional} is empty
     */
    public static Set<String> toSet(Optional<List<String>> list) {
        List<String> elements = list.orElse(Collections.emptyList());
        return new HashSet<>(elements);
    }

    /**
    * Splits a preamble string into ordered fields.
    * @return A list of size {@code numFields} where the ith element is the ith field value if specified in
    *         the input, {@code Optional.empty()} otherwise.
    */
    public static List<Optional<String>> splitPreamble(String preamble, int numFields) {
        return Arrays.stream(Arrays.copyOf(preamble.split("\\s+", numFields), numFields))
                .map(Optional::ofNullable)
                .collect(Collectors.toList());
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        assert name != null;
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
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
     * Returns True if input is a valid date
     * Returns False otherwise.
     */
    public static boolean isValidDate(String input) {
        final Matcher matcher = FORMAT_DATE.matcher(input.trim());
        return matcher.matches();
    }

    /**
     * Returns True if input is a valid time
     * Returns False otherwise.
     */
    public static boolean isValidTime(String input) {
        final Matcher matcher = FORMAT_TIME.matcher(input.trim());
        return matcher.matches();
    }

    /**
     * Returns True if input is a valid duration
     * Returns False otherwise.
     */
    public static boolean isValidDuration(String input) {
        final Matcher matcher = FORMAT_DURATION.matcher(input.trim());
        return matcher.matches();
    }

    /**
     * Returns True if input is a valid name
     * Returns False otherwise.
     */
    public static boolean isValidName(String input) {
        final Matcher matcher = FORMAT_NAME.matcher(input.trim());
        return matcher.matches();
    }

    /**
     * Returns True if input is a valid parameter
     * Returns False otherwise.
     */
    public static boolean isValidParameter(String input) {
        switch (input) {
        case PARAMETER_NAME:
        case PARAMETER_NAME_2:
        case PARAMETER_START_DATE:
        case PARAMETER_START_DATE_2:
        case PARAMETER_START_DATE_3:
        case PARAMETER_END_DATE:
        case PARAMETER_END_DATE_2:
        case PARAMETER_END_DATE_3:
        case PARAMETER_START_TIME:
        case PARAMETER_START_TIME_2:
        case PARAMETER_START_TIME_3:
        case PARAMETER_END_TIME:
        case PARAMETER_END_TIME_2:
        case PARAMETER_END_TIME_3:
        case PARAMETER_TAG:
        case PARAMETER_TAG_2:
            return true;
        default:
            return false;
        }
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
     * Checks if the given string exists in the strArray. Check is case insensitive
     * @param s String to check
     * @param strArray Array of Strings to check against
     * @return true if s is in strArray, false otherwise
     */
    public static boolean isFoundIn(String s, String[] strArray) {
        for (String str: strArray) {
            if (s.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

}
