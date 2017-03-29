package seedu.task.logic.parser;

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

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.Priority;
import seedu.task.model.task.RecurringFrequency;
import seedu.task.model.task.Timing;

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
     * Parses a {@code Optional<String> name} into an {@code Optional<Description>} if {@code name} is present.
     */
    public static Optional<Description> parseDescription(Optional<String> description) throws IllegalValueException {
        assert description != null;
        return description.isPresent() ? Optional.of(new Description(description.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Priority>} if {@code phone} is present.
     */
    public static Optional<Priority> parsePriority(Optional<String> priority) throws IllegalValueException {
        assert priority != null;
        return priority.isPresent() ? Optional.of(new Priority(priority.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Timing>} if {@code email} is present.
     */
    public static Optional<Timing> parseTiming(Optional<String> timing) throws IllegalValueException {
        assert timing != null;
        return timing.isPresent() ? Optional.of(new Timing(timing.get())) : Optional.empty();
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

    public static Optional<RecurringFrequency> parseFrequency(Optional<String> frequency) throws IllegalValueException {
        assert frequency != null;
        return frequency.isPresent() ? Optional.of(new RecurringFrequency(frequency.get())) : Optional.empty();
    }
}
