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
import seedu.task.model.task.Deadline;
import seedu.task.model.task.Information;
import seedu.task.model.task.PriorityLevel;
import seedu.task.model.task.TaskName;

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
     * Parses a {@code Optional<String> taskName} into an {@code Optional<TaskName>} if {@code taskName} is present.
     */
    public static Optional<TaskName> parseTaskName(Optional<String> name) throws IllegalValueException {
        assert name != null;
        return name.isPresent() ? Optional.of(new TaskName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> deadline} into an {@code Optional<Deadline>} if {@code deadline} is present.
     */
    public static Optional<Deadline> parseDeadline(Optional<String> deadline) throws IllegalValueException {
        assert deadline != null;
        return deadline.isPresent() ? Optional.of(new Deadline(deadline.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> information} into an {@code Optional<Information>}
     * if {@code information} is present.
     */
    // Keep, just rename
    public static Optional<Information> parseInfo(Optional<String> information) throws IllegalValueException {
        assert information != null;
        return information.isPresent() ? Optional.of(new Information(information.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> priorityLevel} into an {@code Optional<PriorityLevel>}
     * if {@code priorityLevel} is present.
     */
    // Convert to ANY_INFO
    public static Optional<PriorityLevel> parsePriorityLevel(Optional<String> priorityLevel)
            throws IllegalValueException {
        assert priorityLevel != null;
        return priorityLevel.isPresent() ? Optional.of(new PriorityLevel(priorityLevel.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code UniqueTagList}.
     */
    // keep
    public static UniqueTagList parseTags(Collection<String> tags) throws IllegalValueException {
        assert tags != null;
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return new UniqueTagList(tagSet);
    }
}
