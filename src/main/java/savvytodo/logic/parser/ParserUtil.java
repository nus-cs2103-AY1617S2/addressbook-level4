package savvytodo.logic.parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.commons.util.StringUtil;
import savvytodo.model.category.Category;
import savvytodo.model.category.UniqueCategoryList;
import savvytodo.model.task.DateTime;
import savvytodo.model.task.Description;
import savvytodo.model.task.Location;
import savvytodo.model.task.Name;
import savvytodo.model.task.Priority;
import savvytodo.model.task.Recurrence;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {

    private static final int SIZE_OF_DATE_TIME_INPUT = 1;
    private static final int ARRAY_FIELD_2 = 1;
    private static final int ARRAY_FIELD_1 = 0;

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    /**
     * Returns the specified index in the {@code command} if it is a positive unsigned integer
     * Returns an {@code Optional.empty()} otherwise.
     */
    public static Optional<Integer> parseIndex(String command) {
        final Matcher matcher = INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    //@@author A0140016B
    /**
     * Returns int[] if the String is parsed <br>
     * Returns a int[] populated by all elements in the given string
     * Returns a int[] if the given {@code Optional} is empty,
     * or if the int[] contained in the {@code Optional} is empty
     */
    public static Optional<int[]> parseMultipleInteger(String indicesString) {
        boolean parseError = false;

        String trimmedIndicesString = indicesString.trim();
        String[] indicesStringArray = null;
        int[] indicesArray = null;
        try {
            indicesStringArray = trimmedIndicesString.split(StringUtil.WHITESPACE_REGEX);
            indicesArray = Arrays.stream(indicesStringArray).mapToInt(Integer::parseInt).toArray();

            for (String index : indicesStringArray) {
                if (!StringUtil.isUnsignedInteger(index)) {
                    parseError = true;
                    break;
                }
            }
        } catch (NumberFormatException ex) {
            parseError = true;
        }

        if (parseError) {
            return Optional.empty();
        }
        return Optional.of(indicesArray);
    }
    //@@author A0140016B

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
        return Arrays.stream(Arrays.copyOf(preamble.split(StringUtil.WHITESPACE_REGEX, numFields), numFields))
                .map(Optional::ofNullable)
                .collect(Collectors.toList());
    }

    //@@author A0140016B
    /**
     * Extract a {@code Optional<String> dateTime} into an {@code String[]} if {@code dateTime} is present.
     */
    public static String[] getDateTimeFromArgs(Optional<String> dateTime) {
        assert dateTime != null;
        if (dateTime.isPresent()) {
            String [] dateTimeValues = dateTime.get().split(DateTime.DATETIME_STRING_CONNECTOR);
            if (dateTimeValues.length == SIZE_OF_DATE_TIME_INPUT) {
                return new String[] {StringUtil.EMPTY_STRING, dateTimeValues[ARRAY_FIELD_1]};
            } else {
                return dateTime.get().split(DateTime.DATETIME_STRING_CONNECTOR);
            }
        } else {
            return DateTime.DEFAULT_VALUES;
        }
    }

    /**
     * Extract a {@code Optional<String> recurrence} into an {@code String[]} if {@code recurrence} is present.
     */
    public static String[] getRecurrenceFromArgs(Optional<String> recurrence) {
        assert recurrence != null;
        return recurrence.isPresent() ? recurrence.get().split(StringUtil.WHITESPACE_REGEX) : Recurrence.DEFAULT_VALUES;
    }
    //@@author A0140016B

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        assert name != null;
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> priority} into an {@code Optional<Priority>} if {@code priority} is present.
     */
    public static Optional<Priority> parsePriority(Optional<String> priority) throws IllegalValueException {
        assert priority != null;
        return priority.isPresent() ? Optional.of(new Priority(priority.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> location} into an {@code Optional<Location>} if {@code location} is present.
     */
    public static Optional<Location> parseLocation(Optional<String> location) throws IllegalValueException {
        assert location != null;
        return location.isPresent() ? Optional.of(new Location(location.get())) : Optional.empty();
    }

    //@@author A0140016B
    /**
     * Parses a {@code Optional<String> dateTime} into an {@code Optional<DateTime>}
     * if {@code dateTime} is present.
     */
    public static Optional<DateTime> parseDateTime(Optional<String> dateTime) throws IllegalValueException {
        assert dateTime != null;
        if (dateTime.isPresent()) {
            String [] dateTimeValues = dateTime.get().split(DateTime.DATETIME_STRING_CONNECTOR);
            if (dateTimeValues.length == SIZE_OF_DATE_TIME_INPUT) {
                return Optional.of(new DateTime(StringUtil.EMPTY_STRING, dateTimeValues[ARRAY_FIELD_1]));
            } else {
                return Optional.of(new DateTime(dateTimeValues[ARRAY_FIELD_1], dateTimeValues[ARRAY_FIELD_2]));
            }
        } else {
            return Optional.empty();
        }
    }

    /**
     * Parses a {@code Optional<String> recurrence} into an {@code Optional<Recurrence>}
     * if {@code recurrence} is present.
     */
    public static Optional<Recurrence> parseRecurrence(Optional<String> recurrence) throws IllegalValueException {
        assert recurrence != null;
        if (recurrence.isPresent()) {
            String [] recurValues = recurrence.get().split(StringUtil.WHITESPACE_REGEX);
            return Optional
                    .of(new Recurrence(recurValues[ARRAY_FIELD_1], Integer.parseInt(recurValues[ARRAY_FIELD_2])));
        } else {
            return Optional.empty();
        }
    }
    //@@author A0140016B

    /**
     * Parses a {@code Optional<String> description} into an {@code Optional<Description>}
     * if {@code description} is present.
     */
    public static Optional<Description> parseDescription(Optional<String> description) throws IllegalValueException {
        assert description != null;
        return description.isPresent() ? Optional.of(new Description(description.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> categories} into an {@code UniqueCategoryList}.
     */
    public static UniqueCategoryList parseCategories(Collection<String> categories) throws IllegalValueException {
        assert categories != null;
        final Set<Category> categorySet = new HashSet<>();
        for (String categoryName : categories) {
            categorySet.add(new Category(categoryName));
        }
        return new UniqueCategoryList(categorySet);
    }
}
