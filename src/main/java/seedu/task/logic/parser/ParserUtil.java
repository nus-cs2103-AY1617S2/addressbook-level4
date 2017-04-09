package seedu.task.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Deadline;
import seedu.task.model.task.Information;
import seedu.task.model.task.PriorityLevel;
import seedu.task.model.task.TaskName;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static boolean isEvent = false;
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
     * Parses a {@code Optional<String> taskName} into an {@code Optional<TaskName>} if {@code taskName} is present.
     */
    public static Optional<TaskName> parseTaskName(Optional<String> name) throws IllegalValueException {
        assert name != null;
        return name.isPresent() ? Optional.of(new TaskName(name.get())) : Optional.empty();
    }
    //@@author A0139161J-reused
    /**
     * Parses a {@code Optional<String> deadline} into an {@code Optional<Deadline>} if {@code deadline} is present.
     */
    public static Optional<Deadline> parseDeadline(Optional<String> deadline) throws IllegalValueException {
        assert deadline != null;
        if (deadline.isPresent() && deadline.get().equals("")) {
            return deadline.isPresent() ? Optional.of(new Deadline("")) : Optional.empty();
        }
        Parser parser = new Parser();
        String fromDate = new String("");
        String fromTime = null;
        String toDate = new String("");
        String toTime = null;
        if (deadline.isPresent()) {
            String deadlineString = deadline.toString();
            List <DateGroup> groups = parser.parse(deadlineString);
            List dates = null;
            int line;
            int column;
            String matchingValue;
            String syntaxTree;
            Map parseMap;
            boolean isRecurring;
            Date recursUntil;

            for (DateGroup group: groups) {
                dates = group.getDates();
                line = group.getLine();
                column = group.getPosition();
                matchingValue = group.getText();
                syntaxTree = group.getSyntaxTree().toStringTree();
                parseMap = group.getParseLocations();
                isRecurring = group.isRecurring();
                recursUntil = group.getRecursUntil();
            }

            if (dates != null) {
                fromDate = dates.get(0).toString();
                fromTime = getTime(fromDate);
                if (dates.size() != 1) {
                    toDate = dates.get(1).toString();
                    toTime = getTime(toDate);
                    isEvent = true;
                }
            }
            StringTokenizer st = new StringTokenizer(fromDate);
            List<String> listDeadline = new ArrayList<String>();
            while (st.hasMoreTokens()) {
                listDeadline.add(st.nextToken());
            }
            List<String> endOfEvent = new ArrayList<String>();
            if (isEvent) {
                st = new StringTokenizer(toDate);
                while (st.hasMoreTokens()) {
                    endOfEvent.add(st.nextToken());
                }
            }
            StringBuilder deadlineStringBuilder = new StringBuilder();
            deadlineStringBuilder.append(listDeadline.get(2) + "-" + listDeadline.get(1)
                + "-" + listDeadline.get(5) + " @ " + fromTime);
            if (isEvent) {
                deadlineStringBuilder.append(" to " + endOfEvent.get(2) + "-" + endOfEvent.get(1)
                    + "-" + endOfEvent.get(5) + " @ " + toTime);
            }
            fromDate = deadlineStringBuilder.toString();
        }
        return deadline.isPresent() ? Optional.of(new Deadline(fromDate)) : Optional.empty();
    }
    //@@author

    /**
     * Parses a {@code Optional<String> information} into an {@code Optional<Information>}
     * if {@code information} is present.
     */
    public static Optional<Information> parseInfo(Optional<String> information) throws IllegalValueException {
        assert information != null;
        return information.isPresent() ? Optional.of(new Information(information.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> priorityLevel} into an {@code Optional<PriorityLevel>}
     * if {@code priorityLevel} is present.
     */
    public static Optional<PriorityLevel> parsePriorityLevel(Optional<String> priorityLevel)
            throws IllegalValueException {
        assert priorityLevel != null;
        return priorityLevel.isPresent() ? Optional.of(new PriorityLevel(priorityLevel.get())) : Optional.empty();
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
    //@@author A0139161J-reused
    /* Returns String in format of hh:mm:ss
     * Precond: dateTime string formed by NattyParser required as input
     */
    public static String getTime(String dateTime) {
        List<String> output = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(dateTime);
        List<String> list = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        st = new StringTokenizer(list.get(3), ":");
        while (st.hasMoreTokens()) {
            output.add(st.nextToken());
        }
        return new String(output.get(0) + ":" + output.get(1));
    }
}
