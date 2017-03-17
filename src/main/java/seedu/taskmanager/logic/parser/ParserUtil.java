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
import seedu.taskmanager.model.task.Date;
import seedu.taskmanager.model.task.EndTime;
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

    /**
     * Parses a {@code Optional<String> taskname} into an
     * {@code Optional<TaskName>} if {@code taskname} is present.
     */
    public static Optional<TaskName> parseTaskName(Optional<String> taskname) throws IllegalValueException {
	assert taskname != null;
	return taskname.isPresent() ? Optional.of(new TaskName(taskname.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<Date>} if
     * {@code date} is present.
     */
    public static Optional<Date> parseDate(Optional<String> date) throws IllegalValueException {
	assert date != null;
	return date.isPresent() ? Optional.of(new Date(date.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> starttime} into an
     * {@code Optional<StartTime>} if {@code starttime} is present.
     */
    public static Optional<StartTime> parseStartTime(Optional<String> starttime) throws IllegalValueException {
	assert starttime != null;
	return starttime.isPresent() ? Optional.of(new StartTime(starttime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> endtime} into an
     * {@code Optional<EndTime>} if {@code endtime} is present.
     */
    public static Optional<EndTime> parseEndTime(Optional<String> endtime) throws IllegalValueException {
	assert endtime != null;
	return endtime.isPresent() ? Optional.of(new EndTime(endtime.get())) : Optional.empty();
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
