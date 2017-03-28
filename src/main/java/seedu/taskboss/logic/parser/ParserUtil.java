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
import seedu.taskboss.model.task.Recurrence;
import seedu.taskboss.model.task.Recurrence.Frequency;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {

    private static final String ALL_WHITESPACE = "\\s+";
    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
    private static final Pattern SORT_TYPE_ARGS_FORMAT = Pattern.compile("(?<sortType>.+)");

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

    //@@author A0143157J
    /**
     * Returns the specified sort type in the {@code command} if it is a valid sort type
     * Returns an {@code Optional.empty()} otherwise.
     */
    public static Optional<String> parseSortType(String command) {
        final Matcher matcher = SORT_TYPE_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String sortType = matcher.group("sortType");
        if (sortType == null) {
            return Optional.empty();
        }
        return Optional.of(sortType);
    }

    /**
     * Splits {@code command} by whitespace as delimiter into a {@code String[]}
     * Returns the {@code String[]}.
     */
    public static String[] parseRenameCategory(String command) {
        String trimmedCommand = command.trim();
        String[] categories = trimmedCommand.split(ALL_WHITESPACE);
        return categories;
    }

    //@@author
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

    //@@author A0143157J
    /**
     * Parses an {@code Optional<String>} dateTime into an {@code Optional<DateTime>}
     * if {@code dateTime} is present.
     * @throws IllegalValueException
     */
    public static Optional<DateTime> parseDateTime(Optional<String> dateTime) throws IllegalValueException {
        assert dateTime != null;

        if (dateTime.isPresent()) {
            DateTimeParser dateParser = new DateTimeParser();
            DateTime dt = dateParser.parseDate(dateTime.toString().trim());
            return Optional.of(dt);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Parses a {@code Optional<String> frequency} into an {@code Optional<Recurrence>}
     * if {@code recurrence} is present.
     */
    public static Optional<Recurrence> parseRecurrence(Optional<String> frequency) throws
        IllegalValueException, IllegalArgumentException {
        assert frequency != null;
        return frequency.isPresent() ? Optional.of(new Recurrence(Frequency
                .valueOf(frequency.get().toUpperCase().trim()))) : Optional.empty();
    }

    //@@author
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
