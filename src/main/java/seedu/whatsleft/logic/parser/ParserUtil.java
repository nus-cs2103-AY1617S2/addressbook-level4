package seedu.whatsleft.logic.parser;

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

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.commons.util.StringUtil;
import seedu.whatsleft.model.activity.ByDate;
import seedu.whatsleft.model.activity.ByTime;
import seedu.whatsleft.model.activity.Description;
import seedu.whatsleft.model.activity.EndDate;
import seedu.whatsleft.model.activity.EndTime;
import seedu.whatsleft.model.activity.Location;
import seedu.whatsleft.model.activity.Priority;
import seedu.whatsleft.model.activity.StartDate;
import seedu.whatsleft.model.activity.StartTime;
import seedu.whatsleft.model.tag.Tag;
import seedu.whatsleft.model.tag.UniqueTagList;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetType>[et][vs])\\s(?<targetIndex>.+)",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern INDEX_ALONE_ARG_FORMAT = Pattern.compile("(?<index>.+)");
    private static final Pattern CONFIG_FILEPATH_FORMAT = Pattern.compile("^(.)?(/\\p{Alnum}*)+.xml");
    private static final Pattern INDEX_FREQ_OCCUR_FORMAT = Pattern.compile("(?<index>.+)\\s(?<freq>.+)\\s(?<occur>.+)");

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

    //@@author A0121668A
    /**
     * Returns the specified location in the {@code command}
     * Returns an {@code Optional.empty()} otherwise
     * @param command
     * @return
     */
    public static Optional<String> parseFilePath(String command) {
        final Matcher matcher = CONFIG_FILEPATH_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String filePath = command.trim();
        return Optional.of(filePath);
    }

    //@@author A0110491U
    /**
     * Returns the parsedIndex for recurCommand
     */
    public static Optional<Integer> parseIndexForRec(String command) {
        final Matcher matcher = INDEX_FREQ_OCCUR_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }
        String index = matcher.group("index");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));
    }
    //@@author

    //@@author A0110491U
    /**
     * Returns the parsedFrequency for recurCommand
     */
    public static Optional<String> parseFreqForRec(String command) {
        final Matcher matcher = INDEX_FREQ_OCCUR_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }
        String freq = matcher.group("freq");
        return Optional.of(freq);
    }
    //@@author

    //@@author A0110491U
    /**
     * Returns the parsedOccurance for recurCommand
     */
    public static Optional<Integer> parseOccurForRec(String command) {
        final Matcher matcher = INDEX_FREQ_OCCUR_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }
        String occur = matcher.group("occur");
        if (!StringUtil.isUnsignedInteger(occur)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(occur));
    }

    //@@author A0110491U
    /**
     * Returns the parsedIndex when it is the only argument
     */
    public static Optional<Integer> parseIndexAlone(String command) {
        final Matcher matcher = INDEX_ALONE_ARG_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("index");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }
    //@@author A0124377A
    /**
     * Returns the parsed integer when it is the only argument
     */
    public static Optional<Integer> parseIntegerAlone(String command) {
        final Matcher matcher = INDEX_ALONE_ARG_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("index");
        if (!StringUtil.isSignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));
    }

    /**
     * Returns the parsedType when it is the first of 2 arguments
     */
    public static Optional<String> parseType(String command) {
        final Matcher matcher = INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String type = matcher.group("targetType");
        return Optional.of(type);
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
     * Parses a {@code Optional<String> description} into an {@code Optional<Description>}
     * if {@code description} is present.
     */
    public static Optional<Description> parseDescription(Optional<String> description) throws IllegalValueException {
        assert description != null;
        return description.isPresent() ? Optional.of(new Description(description.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> priority} into an {@code Optional<Priority>} if {@code priority} is present.
     */
    public static Optional<Priority> parsePriority(Optional<String> priority) throws IllegalValueException {
        assert priority != null;
        return priority.isPresent() ? Optional.of(new Priority(priority.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> startTime} into an {@code Optional<StartTime>} if {@code startTime} is present.
     */
    public static Optional<StartTime> parseStartTime(Optional<String> startTime) throws IllegalValueException {
        assert startTime != null;
        return startTime.isPresent() ? Optional.of(new StartTime(startTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> startDate} into an {@code Optional<StartDate>} if {@code startDate} is present.
     */
    public static Optional<StartDate> parseStartDate(Optional<String> startDate) throws IllegalValueException {
        assert startDate != null;
        return startDate.isPresent() ? Optional.of(new StartDate(startDate.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> startTime} into an {@code Optional<StartTime>} if {@code startTime} is present.
     */
    public static Optional<EndTime> parseEndTime(Optional<String> endTime) throws IllegalValueException {
        assert endTime != null;
        return endTime.isPresent() ? Optional.of(new EndTime(endTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> endDate} into an {@code Optional<EndDate>} if {@code endDate} is present.
     */
    public static Optional<EndDate> parseEndDate(Optional<String> endDate) throws IllegalValueException {
        assert endDate != null;
        return endDate.isPresent() ? Optional.of(new EndDate(endDate.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> byTime} into an {@code Optional<ByTime>} if {@code email} is present.
     */
    public static Optional<ByTime> parseByTime(Optional<String> byTime) throws IllegalValueException {
        assert byTime != null;
        return byTime.isPresent() ? Optional.of(new ByTime(byTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> byDate} into an {@code Optional<byDate>} if {@code ByDate} is present.
     */
    public static Optional<ByDate> parseByDate(Optional<String> byDate) throws IllegalValueException {
        assert byDate != null;
        return byDate.isPresent() ? Optional.of(new ByDate(byDate.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> location} into an {@code Optional<Location>} if {@code location} is present.
     */
    public static Optional<Location> parseLocation(Optional<String> location) throws IllegalValueException {
        assert location != null;
        return location.isPresent() ? Optional.of(new Location(location.get())) : Optional.empty();
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

    //@@author A0121668A
    public static String parseStatus(String command) {
        String trimmedCommand = command.trim();
        return trimmedCommand;
    }

    //@@author A0148038A
    public static String parseClearType(String command) {
        String trimmedCommand = command.trim();
        return trimmedCommand;
    }
}
