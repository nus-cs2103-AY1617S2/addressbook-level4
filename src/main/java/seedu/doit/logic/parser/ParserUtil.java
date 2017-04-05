package seedu.doit.logic.parser;

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
import java.util.stream.IntStream;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.commons.util.StringUtil;
import seedu.doit.model.item.Description;
import seedu.doit.model.item.EndTime;
import seedu.doit.model.item.Name;
import seedu.doit.model.item.Priority;
import seedu.doit.model.item.StartTime;
import seedu.doit.model.tag.Tag;
import seedu.doit.model.tag.UniqueTagList;

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
     * Returns a set of specified indexes in the {@code command} if it is a set of positive unsigned integers
     * Returns an {@code Optional.empty()} otherwise.
     */
    public static Set<Integer> parseIndexes(String args) {
        Pattern indexesPattern = Pattern.compile("((\\d+|\\d+-\\d+)?[ ]*)+");
        Matcher matcher = indexesPattern.matcher(args.trim());

        Set<Integer> emptySet = new HashSet<>();
        Set<Integer> taskNumSet = new HashSet<>();

        if (!matcher.matches()) {
            return emptySet;
        }

        String[] taskNumStringArr = args.split(" ");
        for (String taskNumString : taskNumStringArr) {
            if (taskNumString.matches("\\d+")) {
                taskNumSet.add(Integer.parseInt(taskNumString));
            } else if (taskNumString.matches("\\d+-\\d+")) {
                String[] startAndEndIndexes = taskNumString.split("-");
                int startIndex = Integer.parseInt(startAndEndIndexes[0]);
                int endIndex = Integer.parseInt(startAndEndIndexes[1]);
                taskNumSet.addAll(IntStream.rangeClosed(startIndex, endIndex).boxed().collect(Collectors.toSet()));
            }
        }

        if (taskNumSet.remove(0)) {
            return emptySet;
        }
        return taskNumSet;
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
     *
     * @return A list of size {@code numFields} where the ith element is the ith field value if specified in
     * the input, {@code Optional.empty()} otherwise.
     */
    public static List<Optional<String>> splitPreamble(String preamble, int numFields) {
        return Arrays.stream(Arrays.copyOf(preamble.split("\\s+", numFields), numFields))
            .map(Optional::ofNullable)
            .collect(Collectors.toList());
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>}
     * if {@code name} is present.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        assert name != null;
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> priority} into an {@code Optional<Priority>}
     * if {@code priority} is present.
     */
    public static Optional<Priority> parsePriority(Optional<String> priority) throws IllegalValueException {
        assert priority != null;
        return priority.isPresent() ? Optional.of(new Priority(priority.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> description} into an {@code Optional<Description>}
     * if {@code description} is present.
     */
    public static Optional<Description> parseDescription(Optional<String> description) throws IllegalValueException {
        assert description != null;
        return description.isPresent() ? Optional.of(new Description(description.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> startTime} into an {@code Optional<StartTime>}
     * if {@code startTime} is present.
     */
    public static Optional<StartTime> parseStartTime(Optional<String> startTime) throws IllegalValueException {
        assert startTime != null;
        return startTime.isPresent() ? Optional.of(new StartTime(startTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> deadline} into an {@code Optional<Deadline>}
     * if {@code deadline} is present.
     */
    public static Optional<EndTime> parseDeadline(Optional<String> deadline) throws IllegalValueException {
        assert deadline != null;
        return deadline.isPresent() ? Optional.of(new EndTime(deadline.get())) : Optional.empty();
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
