package werkbook.task.logic.parser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.commons.util.StringUtil;
import werkbook.task.model.tag.Tag;
import werkbook.task.model.tag.UniqueTagList;
import werkbook.task.model.task.Description;
import werkbook.task.model.task.EndDateTime;
import werkbook.task.model.task.Name;
import werkbook.task.model.task.StartDateTime;

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
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Returns a new Set populated by all elements in the given list of strings
     * Returns an empty set if the given {@code Optional} is empty, or if the
     * list contained in the {@code Optional} is empty
     */
    public static Set<String> toSet(Optional<List<String>> list) {
        List<String> elements = list.orElse(Collections.emptyList());
        return new LinkedHashSet<>(elements);
    }

    /**
     * Splits a preamble string into ordered fields.
     *
     * @return A list of size {@code numFields} where the ith element is the ith
     *         field value if specified in the input, {@code Optional.empty()}
     *         otherwise.
     */
    public static List<Optional<String>> splitPreamble(String preamble, int numFields) {
        return Arrays.stream(Arrays.copyOf(preamble.split("\\s+", numFields), numFields))
                .map(Optional::ofNullable).collect(Collectors.toList());
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
     * Parses a {@code Optional<String> description} into an
     * {@code Optional<Descripton>} if {@code description} is present.
     */
    public static Optional<Description> parseDescription(Optional<String> description)
            throws IllegalValueException {
        assert description != null;
        return description.isPresent() ? Optional.of(new Description(description.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> endDateTime} into an
     * {@code Optional<EndDateTime>} if {@code endDateTime} is present.
     */
    public static Optional<EndDateTime> createEndDateTime(Optional<String> endDateTime)
            throws IllegalValueException {
        assert endDateTime != null;
        return endDateTime.isPresent() ? Optional.of(new EndDateTime(endDateTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> startDateTime} into an
     * {@code Optional<StartDateTime>} if {@code startDateTime} is present.
     */
    public static Optional<StartDateTime> createStartDateTime(Optional<String> startDateTime)
            throws IllegalValueException {
        assert startDateTime != null;
        return startDateTime.isPresent() ? Optional.of(new StartDateTime(startDateTime.get()))
                : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code UniqueTagList}.
     */
    public static UniqueTagList parseTags(Collection<String> tags) throws IllegalValueException {
        assert tags != null;
        final Set<Tag> tagSet = new LinkedHashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return new UniqueTagList(tagSet);
    }

    // @@author A0162266E
    /**
     * Parses a {@code Optional<String> Path} into an {@code Optional<Path>} if
     * {@code path} is present.
     */
    public static Optional<Path> parsePath(String path) {
        assert path != null;
        return !path.equals("") ? Optional.of(Paths.get(path.trim())) : Optional.empty();
    }

    // @@author
}
