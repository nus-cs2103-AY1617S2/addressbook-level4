package seedu.tache.logic.parser;

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

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.commons.util.StringUtil;
import seedu.tache.model.tag.Tag;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.Date;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.Time;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
    private static final Pattern NAME_FORMAT = Pattern.compile("^\".+\"");
    private static final Pattern DATE_FORMAT = Pattern.compile("^[0-3]?[0-9]/[0-1]?[0-9]/"
                                                               + "(?:[0-9]{2})?[0-9]{2}$|^[0-3]?[0-9]-[0-1]?[0-9]-"
                                                               + "(?:[0-9]{2})?[0-9]{2}$|^[0-3]{1}[0-9]{1}[0-1]{1}"
                                                               + "[0-9]{1}(?:[0-9]{2})?[0-9]{2}$");
    private static final Pattern TIME_FORMAT = Pattern.compile("^[0-2][0-9][0-5][0-9]|^([0-1][0-2]|[0-9])"
                                                               + "([.][0-5][0-9])?\\s?(am|pm){1}");
    private static final Pattern DURATION_FORMAT = Pattern.compile("^\\d+\\s?((h|hr|hrs)|(m|min|mins))");

    public static final int TYPE_TASK = 0;
    public static final int TYPE_DETAILED_TASK = 1;

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
            if (!StringUtil.hasSpecialCharactes(index) && !index.equals("0")) {
                return Optional.of(toAlphabeticReverse(index.toUpperCase()));
            } else {
                return Optional.empty();
            }
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

    /**
     * Returns True if input is a valid date
     * Returns False otherwise.
     */
    public static boolean isValidDate(String input) {
        final Matcher matcher = DATE_FORMAT.matcher(input.trim());
        return matcher.matches();
    }

    /**
     * Returns True if input is a valid time
     * Returns False otherwise.
     */
    public static boolean isValidTime(String input) {
        final Matcher matcher = TIME_FORMAT.matcher(input.trim());
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * Returns True if input is a valid duration
     * Returns False otherwise.
     */
    public static boolean isValidDuration(String input) {
        final Matcher matcher = DURATION_FORMAT.matcher(input.trim());
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * Returns True if input is a valid name
     * Returns False otherwise.
     */
    public static boolean isValidName(String input) {
        final Matcher matcher = NAME_FORMAT.matcher(input.trim());
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

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

    /**
     * Returns data type based on the input
     */
    public static Object determineType(String input) throws IllegalValueException {
        if (isValidDate(input)) {
            return new Date(input);
        } else if (isValidTime(input)) {
            return new Time(input);
        } else if (isValidName(input)) {
            return new Name(input);
        }
        throw new IllegalValueException("Invalid Input");
    }

    /**
     * Returns the first time String encountered
     */
    public static String parseTime(String input) throws IllegalValueException {
        String[] inputs = input.split(" ");
        for (String candidate : inputs) {
            Matcher matcher = TIME_FORMAT.matcher(candidate.trim());
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
            Matcher matcher = DATE_FORMAT.matcher(candidate.trim());
            if (matcher.lookingAt()) {
                return matcher.group();
            }
        }
        throw new IllegalValueException("Invalid Input");
    }
    /**
     * Returns the corresponding integer value of the String entered
     */
    private static int toAlphabeticReverse(String input) {
        char lastCharacter = input.charAt(input.length() - 1);
        int index = lastCharacter - 'A' + 1;
        for (int i = input.length() - 1; i > 0; i--) {
            index += 26;
        }
        return index;
    }

    /**
     * Returns the type of index (Integer/Alphabet) based on the command
     * Returns the value of TYPE_TASK if index is a integer
     * Returns the value of TYPE_DETAILED_TASK if index is a alphabet
     */
    public static Optional<Integer> determineIndexType(String command) {
        final Matcher matcher = INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.of(TYPE_DETAILED_TASK);
        }
        return Optional.of(TYPE_TASK);

    }
}
