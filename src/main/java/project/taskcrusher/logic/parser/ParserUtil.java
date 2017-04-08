package project.taskcrusher.logic.parser;

import java.util.ArrayList;
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

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.commons.util.StringUtil;
import project.taskcrusher.model.event.Location;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.shared.Priority;
import project.taskcrusher.model.tag.Tag;
import project.taskcrusher.model.tag.UniqueTagList;
import project.taskcrusher.model.task.Deadline;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes
 */
public class ParserUtil {

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    /**
     * Returns the specified index in the {@code command} if it is a positive
     * unsigned integer Returns an {@code Optional.empty()} otherwise.
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
        // TODO: take care of number format exception
        return Optional.of(Integer.parseInt(index));

    }

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
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if
     * {@code name} is present.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        assert name != null;
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>}
     * if {@code phone} is present.
     */
    public static Optional<Priority> parsePriority(Optional<String> priority) throws IllegalValueException {
        assert priority != null;
        return priority.isPresent() ? Optional.of(new Priority(priority.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>}
     * if {@code phone} is present.
     */
    public static Optional<Location> parseLocation(Optional<String> location) throws IllegalValueException {
        assert location != null;
        return location.isPresent() ? Optional.of(new Location(location.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> address} into an
     * {@code Optional<Address>} if {@code address} is present.
     */
    public static Optional<Description> parseDescription(Optional<String> address) throws IllegalValueException {
        assert address != null;
        return address.isPresent() ? Optional.of(new Description(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> deadline} into an
     * {@code Optional<Deadline>} if {@code deadline} is present.
     */
    public static Optional<Deadline> parseDeadline(Optional<String> deadline) throws IllegalValueException {
        assert deadline != null;
        return deadline.isPresent() ? Optional.of(new Deadline(deadline.get())) : Optional.empty();
    }

    // @@author A0163962X
    /**
     * Parses a {@code Optional<String> deadline} into an
     * {@code Optional<Deadline>} if {@code deadline} is present.
     */
    public static Optional<List<Timeslot>> parseTimeslots(Optional<String> timeslots) throws IllegalValueException {
        assert timeslots != null;

        if (timeslots.isPresent()) {

            if (timeslots.get().equals(Timeslot.NO_TIMESLOT)) {
                throw new IllegalValueException(Timeslot.MESSAGE_TIMESLOT_DNE);
            }

            // TODO again, could refactor this somewhere
            String[] timeslotsAsStrings = timeslots.get().split("\\s+or\\s+");
            List<Timeslot> timeslotsParsed = new ArrayList<>();
            for (String t : timeslotsAsStrings) {
                String[] dates = t.split("\\s+to\\s+");
                if (dates.length != 2) {
                    throw new IllegalValueException(Timeslot.MESSAGE_TIMESLOT_PAIRS);
                }
                timeslotsParsed.add(new Timeslot(dates[0], dates[1]));
            }

            return Optional.of(timeslotsParsed);

        } else {
            return Optional.empty();
        }
    }

    /**
     *
     * @param argsTokenizer
     * @param prefix
     * @param defaultValue
     * @return
     */
    public static String setValue(ArgumentTokenizer argsTokenizer, ArgumentTokenizer.Prefix prefix,
            String defaultValue) {
        String value = defaultValue;
        Optional<String> rawValue = argsTokenizer.getValue(prefix);
        if (rawValue.isPresent()) {
            value = rawValue.get();
        }
        return value;
    }

    /**
     *
     * @param preamble
     * @param defaultValue
     * @return
     */
    public static String setValue(Optional<String> preamble, String defaultValue) {
        String value = defaultValue;
        if (preamble.isPresent()) {
            value = preamble.get();
        }
        return value;
    }

    /**
     *
     * @param datesToParse
     * @return
     * @throws IllegalValueException
     */
    public static List<Timeslot> parseAsTimeslots(String datesToParse) throws IllegalValueException {

        if (datesToParse.equals(Timeslot.NO_TIMESLOT)) {
            throw new IllegalValueException(Timeslot.MESSAGE_TIMESLOT_DNE);
        }
        String[] timeslotsAsStrings = datesToParse.split("\\s+or\\s+");
        List<Timeslot> timeslots = new ArrayList<>();
        for (String t : timeslotsAsStrings) {
            String[] dates = t.split("\\s+to\\s+");
            if (dates.length == 1) {
                timeslots.add(new Timeslot(dates[0], dates[0]));
            } else if (dates.length != 2) {
                throw new IllegalValueException(Timeslot.MESSAGE_TIMESLOT_PAIRS);
            } else {
                timeslots.add(new Timeslot(dates[0], dates[1]));
            }
        }

        return timeslots;
    }

    /**
     *
     * @param datesToParse
     * @return
     * @throws IllegalValueException
     */
    public static List<Timeslot> parseForList(String datesToParse) throws IllegalValueException {

        if (datesToParse.equals(Timeslot.NO_TIMESLOT)) {
            throw new IllegalValueException(Timeslot.MESSAGE_TIMESLOT_DNE);
        }
        String[] timeslotsAsStrings = datesToParse.split("\\s+or\\s+");
        List<Timeslot> timeslots = new ArrayList<>();
        for (String t : timeslotsAsStrings) {
            String[] dates = t.split("\\s+to\\s+");
            if (dates.length == 1) {
                timeslots.add(Timeslot.constructTimeslotFromEndDate(dates[0]));
            } else if (dates.length != 2) {
                throw new IllegalValueException(Timeslot.MESSAGE_TIMESLOT_PAIRS);
            } else {
                timeslots.add(new Timeslot(dates[0], dates[1]));
            }
        }

        return timeslots;
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
