package seedu.watodo.logic.parser;

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

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.commons.util.StringUtil;
import seedu.watodo.model.tag.Tag;
import seedu.watodo.model.tag.UniqueTagList;
import seedu.watodo.model.task.DateTime;
import seedu.watodo.model.task.Description;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    public static final String WHITESPACE = " ";
    public static final int INDEX_FIRST_ARG = 0;
    public static final int INDEX_SECOND_ARG = 1;
    public static final int NUMFIELD_SPLIT_BY_WHITESPACE = 2;
    public static final String EXTRACT_ARGS_REGEX = "\\s*" + "%1$s" + "\\s*" + "%2$s" + "\\s*";

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
    public static Optional<Description> parseDescription(Optional<String> name) throws IllegalValueException {
        assert name != null;
        return name.isPresent() ? Optional.of(new Description(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> dateTime} into an {@code Optional<DateTime>} if {@code dateTime} is present.
     */
    public static Optional<DateTime> parseDateTime(Optional<String> dateTime) throws IllegalValueException {
        assert dateTime != null;
        return dateTime.isPresent() ? Optional.of(new DateTime(dateTime.get())) : Optional.empty();
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

}

