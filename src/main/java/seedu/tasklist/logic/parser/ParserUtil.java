package seedu.tasklist.logic.parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.commons.util.StringUtil;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.model.task.Priority;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static Hashtable<String, String> flexibleCommands;
    private static Hashtable<String, String> flexiblePrefixes;

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
     * Parses a {@code Optional<String> comment} into an {@code Optional<Comment>} if {@code comment} is present.
     */
    public static Optional<Comment> parseComment(Optional<String> comment) throws IllegalValueException {
        assert comment != null;
        return comment.isPresent() ? Optional.of(new Comment(comment.get())) : Optional.empty();
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

    public static Optional<List<Date>> parseDate(Optional<String> date) throws IllegalValueException {
        assert date != null;
        return date.isPresent() ? Optional.of(DateParser.parse(date.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> priority} into an {@code Optional<priority>} if {@code priority} is present.
     */
    public static Optional<Priority> parsePriority(Optional<String> priority) throws IllegalValueException {
        assert priority != null;
        return priority.isPresent() ? Optional.of(new Priority(priority.get())) : Optional.empty();
    }

    /**
     * Initialises the Hashtable for parsing flexible command words (Hashtable allows duplicate keys).
     * Keys being the acceptable alternatives, values being the legitimate command words (stated in the UserGuide.md)
     *
     */
    public static Hashtable<String, String> initialiseFlexibleCommands() {
        flexibleCommands = new Hashtable<String, String>();
        flexibleCommands.put("insert", "add");
        flexibleCommands.put("create", "add");
        flexibleCommands.put("new", "add");
        flexibleCommands.put("adds", "add");
        flexibleCommands.put("change", "edit");
        flexibleCommands.put("modify", "edit");
        flexibleCommands.put("edits", "edit");
        flexibleCommands.put("remove", "delete");
        flexibleCommands.put("deletes", "delete");
        flexibleCommands.put("cancel", "delete");
        flexibleCommands.put("clean", "clear");
        flexibleCommands.put("locate", "find");
        flexibleCommands.put("arrange", "sort");

        return flexibleCommands;

    }

    /**
     * Initialises the Hashtable for parsing flexible prefixes (Hashtable allows duplicate keys).
     * Keys being the acceptable alternatives, values being the legitimate prefixes (stated in the UserGuide.md)
     *
     */
    public static void initialiseFlexiblePrefixes() {
        flexiblePrefixes = new Hashtable<String, String>();
        //prefixes
        flexiblePrefixes.put("tag/", "t/");
        flexiblePrefixes.put("T/", "t/");
        flexiblePrefixes.put("tags/", "t/");
        flexiblePrefixes.put("comment/", "c/");
        flexiblePrefixes.put("comments/", "c/");
        flexiblePrefixes.put("C/", "c/");
        flexiblePrefixes.put("info/", "c/");
        flexiblePrefixes.put("priority/", "p/");
        flexiblePrefixes.put("urgency/", "p/");
        flexiblePrefixes.put("P/", "p/");
        flexiblePrefixes.put("date/", "d/");
        flexiblePrefixes.put("dates/", "d/");
        flexiblePrefixes.put("D/", "d/");

    }

    /**
     * Returns the Hashtable for flexible commands.
     */
    public static Hashtable<String, String> getFlexibleCommands() {
        return flexibleCommands;
    }



    /**
     * Return the legitimate prefix if the input prefix conforms to one of the listed alternatives.
     * If it does not conform, return the input prefix as it is.
     * If the prefix is an invalid one, the error will be detected during the tokenisation process.
     */
    public static String parseFlexiblePrefix(String input) {
        String result = input;
        Set<String> keys = flexiblePrefixes.keySet();
        for (String key: keys) {
            //locate the case insensitive acceptable alternate prefixes from the input.
            if (Pattern.compile(Pattern.quote(key), Pattern.CASE_INSENSITIVE).matcher(result).find()) {
                result = result.replaceAll("(?i)" + key, flexiblePrefixes.get(key));
            }
        }
        return result;
    }
}

