package seedu.task.logic.parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.NattyDateUtil;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.CompletionStatus;
import seedu.task.model.task.EndTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.StartTime;

//@@author A0146789H
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
     * Parses a {@code String name} into a {@code Optional<Name>} if {@code name} is present.
     *
     * @param name
     * @return
     * @throws IllegalValueException
     */
    public static Optional<Name> parseName(String name) throws IllegalValueException {
        assert name != null;
        String tempName = name.equals("") ? null : name;
        return parseName(Optional.ofNullable(tempName));
    }

    /**
     * Parses a {@code Optional<String> completionStatus} into an
     * {@code Optional<CompletionStatus>} if {@code completionStatus} is present.
     */
    public static Optional<CompletionStatus> parseCompletionStatus(Optional<String> completionStatus)
            throws IllegalValueException {
        assert completionStatus != null;
        return completionStatus.isPresent() ?
                Optional.of(new CompletionStatus(Boolean.valueOf(completionStatus.get()))) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> startDate} into an {@code Optional<StartTime>} if {@code startDate} is present.
     */
    public static Optional<StartTime> parseStartTime(Optional<Date> startDate) throws IllegalValueException {
        assert startDate != null;
        return startDate.isPresent() ? Optional.of(new StartTime(startDate.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String startDate} into an {@code Optional<StartTime>} if {@code startDate} is present.
     */
    public static Optional<StartTime> parseStartTime(String startDate) throws IllegalValueException {
        String processedDate = Optional.ofNullable(startDate).orElse("");
        Date parsedDate = NattyDateUtil.parseSingleDate(processedDate);
        return parseStartTime(Optional.ofNullable(parsedDate));
    }

    /**
     * Parses a {@code Optional<String> endDate} into an {@code Optional<EndTime>} if {@code endDate} is present.
     */
    public static Optional<EndTime> parseEndTime(Optional<Date> endDate) throws IllegalValueException {
        assert endDate != null;
        return endDate.isPresent() ? Optional.of(new EndTime(endDate.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String endDate} into an {@code Optional<EndTime>} if {@code endDate} is present.
     */
    public static Optional<EndTime> parseEndTime(String endDate) throws IllegalValueException {
        String processedDate = Optional.ofNullable(endDate).orElse("");
        Date parsedDate = NattyDateUtil.parseSingleDate(processedDate);
        return parseEndTime(Optional.ofNullable(parsedDate));
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
     * Parses {@code Collection<String> tags} into an {@code Optional<UniqueTagList>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<UniqueTagList>} containing zero tags.
     */
    public static Optional<UniqueTagList> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Split the tags string into a set.
     * Example: "#one #two" into ["one", "two"]
     *
     * @param tagsString
     * @return
     */
    public static Set<String> parseTagStringToSet(String tagsString) {
        Set<String> tagSet = new HashSet<String>();
        for (String i : tagsString.split("\\s+")) {
            if (i.length() > 1) {
                tagSet.add(i.substring(1));
            }
        }
        return tagSet;
    }
}
