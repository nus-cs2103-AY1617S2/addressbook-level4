package seedu.taskboss.logic.parser;

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

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.commons.util.StringUtil;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.task.DateTime;
import seedu.taskboss.model.task.Information;
import seedu.taskboss.model.task.Name;
import seedu.taskboss.model.task.PriorityLevel;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {

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
     * Parses a {@code Optional<String> priorityLevel} into an {@code Optional<PriorityLevel>}
     * if {@code priorityLevel} is present.
     */
    public static Optional<PriorityLevel> parsePriorityLevel(Optional<String> priorityLevel)
            throws IllegalValueException {
        assert priorityLevel != null;
        return priorityLevel.isPresent() ? Optional.of(new PriorityLevel(priorityLevel.get())) : Optional.empty();
    }

    public static Optional<DateTime> parseStartDateTime(Optional<String> startDateTime) {
        assert startDateTime != null;

        if (startDateTime.isPresent()) {
            try {
                DateParser dateParser = new DateParser();
                DateTime dt = dateParser.parseStartDate(startDateTime.toString().trim());
                return Optional.of(dt);
            } catch (IllegalValueException ive) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public static Optional<DateTime> parseEndDateTime(Optional<String> endDateTime) {
        assert endDateTime != null;

        if (endDateTime.isPresent()) {
            try {
                DateParser dateParser = new DateParser();
                DateTime dt = dateParser.parseEndDate(endDateTime.toString().trim());
                return Optional.of(dt);
            } catch (IllegalValueException ive) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    /**
     * Parses a {@code String date}
     *
     * @return parsed dateTime in String
     */
    public static String parseStartDate(String startDateTime) throws IllegalValueException {
        assert startDateTime != null;

        try {
            DateParser dateParser = new DateParser();
            DateTime dateTimeParsed = dateParser.parseStartDate(startDateTime.trim());
            return dateTimeParsed.toString();
        } catch (IllegalValueException ive) {
            throw new IllegalValueException(ive.getMessage());
        }

    }

    /**
     * Parses a {@code String date}
     *
     * @return parsed dateTime in String
     */
    public static String parseEndDate(String endDateTime) throws IllegalValueException {
        assert endDateTime != null;

        try {
            DateParser dateParser = new DateParser();
            DateTime dateTimeParsed = dateParser.parseEndDate(endDateTime.trim());
            return dateTimeParsed.toString();
        } catch (IllegalValueException ive) {
            throw new IllegalValueException(ive.getMessage());
        }

    }

    /**
     * Parses a {@code Optional<String> information} into an {@code Optional<Information>}
     * if {@code information} is present.
     */
    public static Optional<Information> parseInformation(Optional<String> information) throws IllegalValueException {
        assert information != null;
        return information.isPresent() ? Optional.of(new Information(information.get())) : Optional.empty();
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
