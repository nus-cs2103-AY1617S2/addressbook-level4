package seedu.address.logic.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes
 */
public class ParserUtil {
    private static final Pattern INDEX_ARGS_FORMAT = Pattern
            .compile("(?<targetIndex>[TFCtfc][0-9]+)");

    /**
     * Returns the specified index in the {@code command} if it is a positive
     * unsigned integer Returns an {@code Optional.empty()} otherwise.
     */
    public static Optional<String> parseIndex(String command) {
        final Matcher matcher = INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }
        String index = matcher.group("targetIndex");
        return Optional.of(index);

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
     * Parses {@code Collection<String> tags} into an {@code UniqueTagList}.
     */
    public static UniqueTagList parseTags(Collection<String> tags)
            throws IllegalValueException {
        assert tags != null;
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return new UniqueTagList(tagSet);
    }

    /**
     * change DD(-/)MM(-/)YYYY to MM/DD/YYYY
     */
    public static String correctDateFormat(String original) {
        assert original != null;
        original = original.trim();
        Pattern pattern = Pattern.compile(
                "(?<=^|\\s)(\\d{1,2})(-|/)(\\d{1,2})(-|/)(\\d{2,4})(?=$|\\s)");
        Matcher matcher = pattern.matcher(original);
        original = matcher.replaceAll("$3/$1/$5");
        if (original.endsWith(" later") || original.endsWith(" ago")) {
            original = original.substring(0, original.lastIndexOf(" "));
        }
        return original;
    }
}
