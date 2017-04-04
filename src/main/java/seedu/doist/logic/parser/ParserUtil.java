package seedu.doist.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.commons.util.StringUtil;
import seedu.doist.model.tag.Tag;
import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.Description;
import seedu.doist.model.task.Priority;

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

    //@@author A0147980U
    /**
     * Returns an list of integers separated using space in the input string
     * hyphen can be used to represent a range of indices
     */
    public static List<Optional<Integer>> parseIndices(String command) {
        ArrayList<Optional<Integer>> indices = new ArrayList<Optional<Integer>>();
        String[] commandStringComponents = command.trim().split(" +");
        for (String component : commandStringComponents) {
            String[] ends = component.split("-+");
            if (ends.length == 2 && !"".equals(ends[0]) && !"".equals(ends[1])) {
                indices.addAll(parseIndexRange(ends));
            } else {
                indices.add(parseIndex(component));
            }
        }
        return indices;
    }

    /**
     * @param twoEnds : A String array containing 2 elements,
     *                  representing the start and the end of the range (both included)
     * @return a list of Optional Integers in the range
     */
    public static List<Optional<Integer>> parseIndexRange(String[] twoEnds) {
        assert twoEnds.length == 2 && !"".equals(twoEnds[0]) && !"".equals(twoEnds[1]);
        ArrayList<Optional<Integer>> indices = new ArrayList<Optional<Integer>>();
        int start = parseIndex(twoEnds[0]).get();
        int end = parseIndex(twoEnds[1]).get();
        for (int i = start; i <= end; i++) {
            indices.add(Optional.of(i));
        }
        return indices;
    }

    /**
     * Returns an array of integers separated using space in the input string
     */
    public static int[] parseStringToIntArray(String string) {
        List<Optional<Integer>> optionalIndices = ParserUtil.parseIndices(string);
        for (Optional<Integer> optionalIndex : optionalIndices) {
            if (!optionalIndex.isPresent()) {
                return null;
            }
        }
        int[] indices = new int[optionalIndices.size()];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = optionalIndices.get(i).get().intValue();
        }
        return indices;
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
     * Parses a {@code Optional<String> desc} into an {@code Optional<Description>} if {@code desc} is present.
     */
    public static Optional<Description> parseDesc(Optional<String> desc) throws IllegalValueException {
        assert desc != null;
        return desc.isPresent() ? Optional.of(new Description(desc.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> priority} into an {@code Optional<Priority>} if {@code priority} is present.
     */
    public static Optional<Priority> parsePriority(Optional<String> priorityRawValue) throws IllegalValueException {
        assert priorityRawValue != null;
        return priorityRawValue.isPresent() ? Optional.of(new Priority(priorityRawValue.get())) : Optional.empty();
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
     * Parses {@code <String> tagsParameterString} into an {@code UniqueTagList}.
     */
    public static UniqueTagList parseTagsFromString(String tagsParameterString) throws IllegalValueException {
        assert tagsParameterString != null;
        String[] extractedTags = tagsParameterString.trim().split(" ");
        return ParserUtil.parseTags(Arrays.asList(extractedTags));
    }

    /**
     * Parses {@code <String> parameterString} into an {@code ArrayList<String>}.
     */
    public static ArrayList<String> getParameterKeysFromString(String parameterString) {
        ArrayList<String> parameterKeys = new ArrayList<String>();
        LinkedList<String> parametersList = new LinkedList<String>(Arrays.asList(parameterString.split("\\\\")));
        parametersList.poll();  // remove the first item, which is an empty string
        for (String parameterPair : parametersList) {
            String parameterKey = "\\" + parameterPair.split(" ")[0];
            parameterKeys.add(parameterKey);
        }
        return parameterKeys;
    }
}





