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

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Instruction;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {
    //@@author A0139539R
    /**
     * Defines a regex format for comparator names in a specified order
     */
    private static final Pattern COMPARATOR_NAME_ARGS_FORMAT = Pattern.compile(
            "(?<comparatorName>("
            + ListCommand.COMPARATOR_NAME_DATE
            + "|"
            + ListCommand.COMPARATOR_NAME_PRIORITY
            + "|"
            + ListCommand.COMPARATOR_NAME_TITLE
            + ")?)"
            );
    //@@author
    private static final Pattern LISTNAME_INDEX_ARGS_FORMAT = Pattern.compile(
            "(?<listName>("
            + Task.TASK_NAME_FLOATING
            + "|"
            + Task.TASK_NAME_NON_FLOATING
            + "|"
            + Task.TASK_NAME_COMPLETED
            + ")?)"
            + "(\\s?)(?<targetIndex>[0-9]+)"
            );
    /**
     * Returns the specified index in the {@code command} if it is a positive unsigned integer
     * Returns an {@code Optional.empty()} otherwise.
     */
    public static Optional<Integer> parseIndex(String command) {
        final Matcher matcher = LISTNAME_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }
    //@@author A0144813J
    /**
     * Returns the specified task list name in the {@code command} if it is an alpha-non-numeric string
     * that corresponds to a valid task list name.
     * Returns "invalid" otherwise.
     */
    public static Optional<String> parseListName(String command) {
        final Matcher matcher = LISTNAME_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String listName = matcher.group("listName").isEmpty() ? Task.TASK_NAME_NON_FLOATING : matcher.group("listName");
        return Optional.of(listName);

    }
    //@@author
    //@@author A0139539R
    /**
     * Returns the specified task comparator name in the {@code command} if it is an alpha-non-numeric string
     * that corresponds to a valid task comparator name.
     * Returns "invalid" otherwise.
     */
    public static Optional<String> parseComparatorName(String command) {
        final Matcher matcher = COMPARATOR_NAME_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String comparatorName = matcher.group("comparatorName").isEmpty()
                ? "priority"
                : matcher.group("comparatorName");
        return Optional.of(comparatorName);

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
     * Parses a {@code Optional<String> title} into an {@code Optional<Title>} if {@code title} is present.
     */
    public static Optional<Title> parseTitle(Optional<String> title) throws IllegalValueException {
        assert title != null;
        return title.isPresent() ? Optional.of(new Title(title.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<Date>} if {@code date} is present.
     */
    public static Optional<Deadline> parseDate(Optional<String> date) throws IllegalValueException {
        assert date != null;
        return date.isPresent() ? Optional.of(new Deadline(date.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> instruction} into an {@code Optional<Instruction>} if {@code instruction}
     * is present.
     */
    public static Optional<Instruction> parseInstruction(Optional<String> instruction) throws IllegalValueException {
        assert instruction != null;
        return instruction.isPresent() ? Optional.of(new Instruction(instruction.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> priority} into an {@code Optional<Priority>} if {@code priority} is present.
     */
    public static Optional<Priority> parsePriority(Optional<String> priority) throws IllegalValueException {
        assert priority != null;
        return priority.isPresent() ? Optional.of(new Priority(priority.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code UniqueTagList}.
     */
    public static UniqueTagList parseTags(Collection<String> tags) throws IllegalValueException {
        assert tags != null;
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagTitle : tags) {
            tagSet.add(new Tag(tagTitle));
        }
        return new UniqueTagList(tagSet);
    }
}
