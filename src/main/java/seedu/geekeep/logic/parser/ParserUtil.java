package seedu.geekeep.logic.parser;

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

import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.commons.util.StringUtil;
import seedu.geekeep.model.tag.Tag;
import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.DateTime;
import seedu.geekeep.model.task.Description;
import seedu.geekeep.model.task.Title;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    /**
     * Parses a {@code Optional<String> endDateTime} into an {@code Optional<DateTime>} if {@code endDateTime} is
     * present. Returns null if {@code Optional<String> endDateTime} is an empty string.
     */
    public static Optional<DateTime> parseEndDateTime(Optional<String> endDateTime) throws IllegalValueException {
        assert endDateTime != null;
        if (endDateTime.isPresent() && endDateTime.get().equals("")) {
            return null;
        }
        return endDateTime.isPresent() ? Optional.of(new DateTime(endDateTime.get())) : Optional.empty();
    }

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
     * Parses a {@code Optional<String> location} into an {@code Optional<Location>} if {@code location} is present.
     */
    public static Optional<Description> parseLocation(Optional<String> location) throws IllegalValueException {
        assert location != null;
        return location.isPresent() ? Optional.of(new Description(location.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> startDateTime} into an {@code Optional<DateTime>} if {@code startDateTime} is
     * present. Returns an {@code Optional.empty()} otherwise.
     */
    public static Optional<DateTime> parseStartDateTime(Optional<String> startDateTime) throws IllegalValueException {
        assert startDateTime != null;
        if (startDateTime.isPresent() && startDateTime.get().equals("")) {
            return null;
        }
        return startDateTime.isPresent() ? Optional.of(new DateTime(startDateTime.get())) : Optional.empty();
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
     * Parses a {@code Optional<String> title} into an {@code Optional<Title>} if {@code title} is present.
     */
    public static Optional<Title> parseTitle(Optional<String> title) throws IllegalValueException {
        assert title != null;
        return title.isPresent() ? Optional.of(new Title(title.get())) : Optional.empty();
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
     * Returns a new Set populated by all elements in the given list of strings
     * Returns an empty set if the given {@code Optional} is empty,
     * or if the list contained in the {@code Optional} is empty
     */
    public static Set<String> toSet(Optional<List<String>> list) {
        List<String> elements = list.orElse(Collections.emptyList());
        return new HashSet<>(elements);
    }
}
