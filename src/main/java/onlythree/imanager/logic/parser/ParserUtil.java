package onlythree.imanager.logic.parser;

import java.time.ZonedDateTime;
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

import onlythree.imanager.commons.exceptions.IllegalValueException;
import onlythree.imanager.commons.util.StringUtil;
import onlythree.imanager.logic.DateTimeUtil;
import onlythree.imanager.model.tag.Tag;
import onlythree.imanager.model.tag.UniqueTagList;
import onlythree.imanager.model.task.Deadline;
import onlythree.imanager.model.task.Name;
import onlythree.imanager.model.task.StartEndDateTime;
import onlythree.imanager.model.task.exceptions.InvalidDurationException;
import onlythree.imanager.model.task.exceptions.PastDateTimeException;

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
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        assert name != null;
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
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

    //@@author A0140023E
    /**
     * Parses a {@code Optional<String>} into an {@code Optional<Deadline>} if {@code deadline} is present.
     */
    public static Optional<Deadline> parseNewDeadline(Optional<String> deadline)
            throws PastDateTimeException, IllegalValueException {

        assert deadline != null;

        if (!deadline.isPresent()) {
            return Optional.empty();
        }

        ZonedDateTime parsedDateTime = DateTimeUtil.parseDateTimeString(deadline.get());
        return Optional.of(new Deadline(parsedDateTime));
    }

    /**
     * Parses a {@code Optional<String>} into an {@code Optional<Deadline>} relative to
     * {@code previousDeadline} if {@code rawDeadline} is present.
     */
    public static Optional<Deadline> parseEditedDeadline(Optional<String> rawDeadline, Deadline previousDeadline)
            throws PastDateTimeException, IllegalValueException {

        assert rawDeadline != null && previousDeadline != null;

        if (!rawDeadline.isPresent()) {
            return Optional.empty();
        }

        ZonedDateTime parsedDateTime =
                DateTimeUtil.parseEditedDateTimeString(rawDeadline.get(), previousDeadline.getDateTime());
        return Optional.of(new Deadline(parsedDateTime));
    }

    /**
     * Parses parameters {@code Optional<String> startDateTimeString} and {@code Optional<String> endDateTimeString}
     * into an {@code Optional<StartEndDateTime>} if they are present.
     */
    public static Optional<StartEndDateTime> parseNewStartEndDateTime(Optional<String> startDateTimeString,
            Optional<String> endDateTimeString)
            throws PastDateTimeException, InvalidDurationException, IllegalValueException {

        assert startDateTimeString != null && endDateTimeString != null;

        if (!startDateTimeString.isPresent() || !endDateTimeString.isPresent()) {
            return Optional.empty();
        }

        ZonedDateTime startDateTime = DateTimeUtil.parseDateTimeString(startDateTimeString.get());
        ZonedDateTime endDateTime = DateTimeUtil.parseDateTimeString(endDateTimeString.get());

        StartEndDateTime startEndDateTime = new StartEndDateTime(startDateTime, endDateTime);

        return Optional.of(startEndDateTime);
    }

    /**
     * Parses parameters {@code Optional<String> startDateTimeString} and
     * {@code Optional<String> endDateTimeString} relative to {@code previousStartEndDateTime} into an
     * {@code Optional<StartEndDateTime>} if the parameters are present.
     */
    public static Optional<StartEndDateTime> parseEditedStartEndDateTime(Optional<String> startDateTimeString,
            Optional<String> endDateTimeString, StartEndDateTime previousStartEndDateTime)
            throws PastDateTimeException, InvalidDurationException, IllegalValueException {

        assert startDateTimeString != null && endDateTimeString != null;

        if (!startDateTimeString.isPresent() || !endDateTimeString.isPresent()) {
            return Optional.empty();
        }

        ZonedDateTime startDateTime =
                DateTimeUtil.parseEditedDateTimeString(startDateTimeString.get(),
                                                       previousStartEndDateTime.getStartDateTime());
        ZonedDateTime endDateTime =
                DateTimeUtil.parseEditedDateTimeString(endDateTimeString.get(),
                        previousStartEndDateTime.getEndDateTime());

        StartEndDateTime startEndDateTime = new StartEndDateTime(startDateTime, endDateTime);

        return Optional.of(startEndDateTime);
    }
}
