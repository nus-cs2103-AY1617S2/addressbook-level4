package seedu.taskit.logic.parser;

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

import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.commons.util.StringUtil;
import seedu.taskit.model.tag.Tag;
import seedu.taskit.model.tag.UniqueTagList;
import seedu.taskit.model.task.Date;
import seedu.taskit.model.task.Priority;
import seedu.taskit.model.task.Title;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
    private static final Pattern PARAMETER_ARGS_FORMAT = Pattern.compile("^[a-zA-Z0-9_]*$");

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

    //@@author A0141872E
    /**
     * Returns the specified parameter in the {@code parameter} if it only contains alphanumeric
     * Returns an {@code Optional.empty()} otherwise.
     */
    public static Optional<String> parseParameter(String parameter) {
        final Matcher matcher = PARAMETER_ARGS_FORMAT.matcher(parameter.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(parameter);

    }//@@author

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
     * Splits an argument string into ordered fields.
     * @return A list of size {@code numFields} where the first element is the index, second element is
     *         the fieldword and last element is the update information,
     *         {@code Optional.empty()} otherwise.
     */
     public static List<Optional<String>> splitArgument(String args, int numFields) {
         return Arrays.stream(args.split("\\s+", numFields))
                 .map(Optional::ofNullable)
                 .collect(Collectors.toList());
     }

    /**
     * Parses a {@code Optional<String> title} into an {@code Optional<Title>} if {@code title} is present.
     */
    public static Optional<Title> parseTitle(Optional<String> title) throws IllegalValueException {
        assert title != null;
        return title.isPresent() ? Optional.of(new Title(title.get())) : Optional.empty();
    }

    // @@author A0163996J
    /**
     * Parses a {@code Optional<String> Date} into an {@code Optional<Date>} if {@code date} is present.
     */
    public static Optional<Date> parseDate(Optional<String> date) throws IllegalValueException {
        assert date != null;
        if (date.isPresent()) {
            if (date.get().equals("none") || date.get().equals("null")) {
                return Optional.of(new Date());
            }
            else {
                return Optional.of(new Date(date.get()));
            }
        } else {
            return Optional.empty();
        }
    }

    // @@author A0163996J
    /**
     * Parses a {@code Optional<String> Priority} into an {@code Optional<Priority>} if {@code priority} is present.
     */
    public static Optional<Priority> parsePriority(Optional<String> priority) throws IllegalValueException {
        assert priority != null;
        return priority.isPresent() ? Optional.of(new Priority(priority.get())) : Optional.empty();
    }

    // @@author
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
