package seedu.address.logic.parser;

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

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.label.Label;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Recurrence;
import seedu.address.model.task.Title;

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
    public static Optional<Title> parseName(Optional<String> name) throws IllegalValueException {
        assert name != null;
        return name.isPresent() ? Optional.of(new Title(name.get())) : Optional.empty();
    }


    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     */
    public static Optional<Deadline> parseDeadline(Optional<String> deadline)
            throws IllegalValueException, IllegalDateTimeValueException {
        assert deadline != null;
        return deadline.isPresent() ? Optional.of(new Deadline(deadline.get())) : Optional.empty();
    }


    /**
     * Parses {@code Collection<String> labels} into an {@code UniqueTagList}.
     */
    public static UniqueLabelList parseLabels(Collection<String> labels) throws IllegalValueException {
        assert labels != null;
        final Set<Label> labelSet = new HashSet<>();
        for (String labelName : labels) {
            labelSet.add(new Label(labelName));
        }
        return new UniqueLabelList(labelSet);
    }

    public static Optional<Recurrence> parseRecurrence(Optional<String> recurrence)
            throws IllegalValueException {
        assert recurrence != null;
        return recurrence.isPresent() ? Optional.of(new Recurrence(recurrence.get())) : Optional.empty();
    }
}
