package seedu.address.logic.parser;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Address;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Email;
import seedu.address.model.task.Name;
import seedu.address.model.task.Phone;
import seedu.address.model.task.StartEndDateTime;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");


    private static Parser dateTimeParser = new com.joestelmach.natty.Parser(TimeZone.getDefault()); // TODO timezones
    // TODO decide if this is the right class
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ISO_DATE_TIME;

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
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        assert phone != null;
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        assert address != null;
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        assert email != null;
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
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

    public static Optional<Deadline> parseDeadline(Optional<String> deadline) throws IllegalValueException {
        assert deadline != null;
        return deadline.isPresent() ? Optional.of(new Deadline(parseDateTimeString(deadline.get()))) : Optional.empty();
    }

    public static Optional<StartEndDateTime> parseStartEndDateTime(Optional<String> startDateTime,
            Optional<String> endDateTime) throws IllegalValueException {
        assert startDateTime != null && endDateTime != null;

        if (!startDateTime.isPresent() || !endDateTime.isPresent()) {
            // TODO If it is malformed should we just ignore or should we do further checks and throw an error?
            return Optional.empty();
        }

        StartEndDateTime startEndDateTime = new StartEndDateTime(parseDateTimeString(startDateTime.get()),
                                                                 parseDateTimeString(endDateTime.get()));

        return Optional.of(startEndDateTime);
    }

    // TODO notice the inconsistencies of the parsing class, maybe need to change this
    /**
     * Parses Date strings into a {@code ZonedDateTime}.
     */
    public static ZonedDateTime parseDateTimeString(String dateTime) throws IllegalValueException {
        List<DateGroup> groups = dateTimeParser.parse(dateTime);
        for (DateGroup group : groups) {
            List<Date> dates = group.getDates();
            if (dates.size() > 0) {
                // TODO comment Avoid old Date class where possible format
                Instant instant = dates.get(0).toInstant();
                ZoneId zoneId = ZoneId.systemDefault();

                // TODO use a ZonedDateTime so user can see time in his timezone, perhaps
                // Instant can be used where possible and only when reading input and output from user
                // we use ZonedDateTime
                ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);
                return zonedDateTime;
            }
        }
        throw new IllegalValueException(dateTime + "TODO is not a valid date.");
    }

}
