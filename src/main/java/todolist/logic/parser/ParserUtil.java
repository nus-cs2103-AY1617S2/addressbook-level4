package todolist.logic.parser;

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

import javafx.util.Pair;
import todolist.commons.exceptions.IllegalValueException;
import todolist.commons.util.StringUtil;
import todolist.model.tag.Tag;
import todolist.model.tag.UniqueTagList;
import todolist.model.task.Description;
import todolist.model.task.EndTime;
import todolist.model.task.StartTime;
import todolist.model.task.Task;
import todolist.model.task.Title;
import todolist.model.task.UrgencyLevel;
import todolist.model.task.Venue;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes
 */
public class ParserUtil {

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    // @@ A0143648Y
    /**
     * Returns the specified index in the {@code command} if it is a positive
     * unsigned integer Returns an {@code Optional.empty()} otherwise.
     */
    public static Optional<Pair<Character, Integer>> parseIndex(String command) {
        final Matcher matcher = INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");

        if (!isValidIndex(index)) {
            return Optional.empty();
        }
        return parseCorrectIndex(index);

    }

    public static Optional<Pair<Character, Integer>> parseCorrectIndex(String index) {
        Character taskType;
        int taskNumber;
        if (StringUtil.isUnsignedInteger(index)) {
            taskType = Task.DEADLINE_CHAR;
            taskNumber = Integer.parseInt(index);
        } else {
            taskType = index.charAt(0);
            taskNumber = Integer.parseInt(index.substring(1));
        }

        return Optional.of(new Pair<Character, Integer>(taskType, taskNumber));

    }

    private static boolean isValidIndex(String index) {
        if (StringUtil.isUnsignedInteger(index)) {
            return true;
        } else {
            char taskType = index.charAt(0);
            if (taskType != Task.DEADLINE_CHAR && taskType != Task.EVENT_CHAR && taskType != Task.FLOAT_CHAR) {
                return false;
            } else {
                if (StringUtil.isUnsignedInteger(index.substring(1))) {
                    return true;
                } else {
                    return false;
                }
            }
        }

    }

    // @@
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
     * Parses a {@code Optional<String> name} into an {@code Optional<Title>} if
     * {@code name} is present.
     */
    public static Optional<Title> parseTitle(Optional<String> name) throws IllegalValueException {
        assert name != null;
        return name.isPresent() ? Optional.of(new Title(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Venue>}
     * if {@code phone} is present.
     */
    public static Optional<Venue> parseVenue(Optional<String> venue) throws IllegalValueException {
        assert venue != null;
        return venue.isPresent() ? Optional.of(new Venue(venue.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> address} into an
     * {@code Optional<EndTime>} if {@code address} is present.
     */
    public static Optional<EndTime> parseEndTime(Optional<String> address) throws IllegalValueException {
        assert address != null;
        return address.isPresent() ? Optional.of(new EndTime(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an
     * {@code Optional<StartTime>} if {@code email} is present.
     */
    public static Optional<StartTime> parseStartTime(Optional<String> startTime) throws IllegalValueException {
        assert startTime != null;
        return startTime.isPresent() ? Optional.of(new StartTime(startTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an
     * {@code Optional<StartTime>} if {@code email} is present.
     */
    public static Optional<UrgencyLevel> parseUrgencyLevel(Optional<String> urgencyLevel) throws IllegalValueException {
        assert urgencyLevel != null;
        return urgencyLevel.isPresent() ? Optional.of(new UrgencyLevel(urgencyLevel.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an
     * {@code Optional<StartTime>} if {@code email} is present.
     */
    public static Optional<Description> parseDescription(Optional<String> description) throws IllegalValueException {
        assert description != null;
        return description.isPresent() ? Optional.of(new Description(description.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code UniqueTagList}.
     */
    public static UniqueTagList parseTags(Collection<String> tags) throws IllegalValueException {
        assert tags != null;
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagTitle : tags) {
            tagSet.add(new Tag(tagTitle));
        }
        return new UniqueTagList(tagSet);
    }
}
