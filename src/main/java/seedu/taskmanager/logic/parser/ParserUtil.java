package seedu.taskmanager.logic.parser;

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

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.StringUtil;
import seedu.taskmanager.model.category.Category;
import seedu.taskmanager.model.category.UniqueCategoryList;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.EndTime;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.StartTime;
import seedu.taskmanager.model.task.TaskName;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes
 */
public class ParserUtil {

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    /**
     * Returns the specified index in the {@code command} if it is a positive
     * unsigned integer Returns an {@code Optional.empty()} otherwise.
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
     * Returns an empty set if the given {@code Optional} is empty, or if the
     * list contained in the {@code Optional} is empty
     */
    public static Set<String> toSet(Optional<List<String>> list) {
        List<String> elements = list.orElse(Collections.emptyList());
        return new HashSet<>(elements);
    }

    /**
     * Splits a preamble string into ordered fields.
     *
     * @return A list of size {@code numFields} where the ith element is the ith
     *         field value if specified in the input, {@code Optional.empty()}
     *         otherwise.
     */
    public static List<Optional<String>> splitPreamble(String preamble, int numFields) {
        return Arrays.stream(Arrays.copyOf(preamble.split("\\s+", numFields), numFields)).map(Optional::ofNullable)
                .collect(Collectors.toList());
    }

    // @@author A0141102H
    /**
     * Parses a {@code Optional<String> taskName} into an
     * {@code Optional<TaskName>} if {@code taskName} is present.
     */
    public static Optional<TaskName> parseTaskName(Optional<String> taskName) throws IllegalValueException {
        assert taskName != null;
        return taskName.isPresent() ? Optional.of(new TaskName(taskName.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> date} into an
     * {@code Optional<startDate>} if {@code startDate} is present.
     */
    public static Optional<StartDate> parseStartDate(Optional<String> startDate) throws IllegalValueException {
        assert startDate != null;
        return startDate.isPresent() ? Optional.of(new StartDate(startDate.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<Date>} if
     * {@code date} is present.
     */
    public static Optional<EndDate> parseEndDate(Optional<String> endDate) throws IllegalValueException {
        assert endDate != null;
        return endDate.isPresent() ? Optional.of(new EndDate(endDate.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> startTime} into an
     * {@code Optional<StartTime>} if {@code startTime} is present.
     */
    public static Optional<StartTime> parseStartTime(Optional<String> startTime) throws IllegalValueException {
        assert startTime != null;
        return startTime.isPresent() ? Optional.of(new StartTime(startTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> endTime} into an
     * {@code Optional<EndTime>} if {@code endTime} is present.
     */
    public static Optional<EndTime> parseEndTime(Optional<String> endTime) throws IllegalValueException {
        assert endTime != null;
        return endTime.isPresent() ? Optional.of(new EndTime(endTime.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code UniqueTagList}.
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
