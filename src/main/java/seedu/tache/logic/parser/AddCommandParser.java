package seedu.tache.logic.parser;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tache.logic.parser.CliSyntax.RECURRENCE_IDENTIFIER_PREFIX;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.logic.commands.AddCommand;
import seedu.tache.logic.commands.Command;
import seedu.tache.logic.commands.IncorrectCommand;
import seedu.tache.logic.parser.AddCommandParser.PossibleDateTime.DateTimeType;
import seedu.tache.model.recurstate.RecurState.RecurInterval;

//@@author A0150120H
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    public static final String START_DATE_IDENTIFIER = "from";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        Set<String> tagSet = new HashSet<String>();
        String[] taskTag = args.split(AddCommand.TAG_SEPARATOR);
        if (taskTag.length == 0) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } else if (taskTag.length > 1) {
            for (String tag: taskTag[1].trim().split(" ")) {
                tagSet.add(tag);
            }
        }

        String taskWithoutTags = taskTag[0];
        Stack<PossibleDateTime> possibleDateTimes = parseDateTimeRecurrenceIdentifiers(taskWithoutTags);
        DateTimeProperties filteredDateTimes = filterPossibleDateTime(possibleDateTimes);
        PossibleDateTime startDateTime = filteredDateTimes.start;
        PossibleDateTime endDateTime = filteredDateTimes.end;
        PossibleDateTime recurInterval = filteredDateTimes.recurrence;

        if (endDateTime == null && startDateTime != null) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } else if (endDateTime == null && startDateTime == null) {
            try {
                return new AddCommand(taskWithoutTags, Optional.empty(), Optional.empty(), tagSet, Optional.empty());
            } catch (IllegalValueException ex) {
                return new IncorrectCommand(ex.getMessage());
            }
        } else if (startDateTime == null && recurInterval != null) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } else {
            String taskName = taskWithoutTags;

            Optional<String> endDateTimeStr = Optional.of(endDateTime.data);
            taskName = ParserUtil.removeLast(taskName, endDateTime.data);

            Optional<String> startDateTimeStr = Optional.empty();
            if (startDateTime != null) {
                startDateTimeStr = Optional.of(startDateTime.data);
                taskName = ParserUtil.removeLast(taskName, startDateTime.data);
            }

            Optional<RecurInterval> parsedRecurInterval = Optional.empty();
            if (recurInterval != null) {
                parsedRecurInterval = Optional.of(recurInterval.recurInterval);
                taskName = ParserUtil.removeLast(taskName, recurInterval.data);
            }

            try {
                return new AddCommand(taskName, startDateTimeStr, endDateTimeStr, tagSet, parsedRecurInterval);
            } catch (IllegalValueException ex) {
                return new IncorrectCommand(ex.getMessage());
            }
        }
    }

    /**
     * Looks for all possible date/time strings based on identifiers
     * @param input String to parse
     * @return Stack of PossibleDateTime objects, each representing a possible date/time string
     */
    private static Stack<PossibleDateTime> parseDateTimeRecurrenceIdentifiers(String input) {
        String[] inputs = input.split(" ");
        int currentIndex = 0;
        Stack<PossibleDateTime> result = new Stack<PossibleDateTime>();
        PossibleDateTime current = new PossibleDateTime(new String(), PossibleDateTime.INVALID_INDEX,
                                                        DateTimeType.UNKNOWN);
        for (int i = 0; i < inputs.length; i++) {
            String word = inputs[i];
            if (ParserUtil.isStartDateIdentifier(word)) {
                result.push(current);
                current = new PossibleDateTime(word, currentIndex, DateTimeType.START);
            } else if (ParserUtil.isEndDateIdentifier(word)) {
                result.push(current);
                current = new PossibleDateTime(word, currentIndex, DateTimeType.END);
            } else if (ParserUtil.isRecurrencePrefix(word)) {
                result.push(current);
                current = new PossibleDateTime(word, currentIndex, DateTimeType.RECURRENCE_PREFIX);
            } else if (ParserUtil.isRecurrenceDaily(word)) {
                result.push(current);
                result.push(new PossibleDateTime(word, currentIndex, DateTimeType.RECURRENCE, RecurInterval.DAY));
                current = new PossibleDateTime(new String(), PossibleDateTime.INVALID_INDEX, DateTimeType.UNKNOWN);
            } else if (ParserUtil.isRecurrenceWeekly(word)) {
                result.push(current);
                result.push(new PossibleDateTime(word, currentIndex, DateTimeType.RECURRENCE, RecurInterval.WEEK));
                current = new PossibleDateTime(new String(), PossibleDateTime.INVALID_INDEX, DateTimeType.UNKNOWN);
            } else if (ParserUtil.isRecurrenceMonthly(word)) {
                result.push(current);
                result.push(new PossibleDateTime(word, currentIndex, DateTimeType.RECURRENCE, RecurInterval.MONTH));
                current = new PossibleDateTime(new String(), PossibleDateTime.INVALID_INDEX, DateTimeType.UNKNOWN);
            } else if (ParserUtil.isRecurrenceYearly(word)) {
                result.push(current);
                result.push(new PossibleDateTime(word, currentIndex, DateTimeType.RECURRENCE, RecurInterval.YEAR));
                current = new PossibleDateTime(new String(), PossibleDateTime.INVALID_INDEX, DateTimeType.UNKNOWN);
            } else {
                current.appendDateTime(word);
            }
            currentIndex += word.length() + 1;
        }
        result.push(current);
        return result;
    }

    /**
     * Class to describe a date/time String that was found
     *
     */
    static class PossibleDateTime {
        static enum DateTimeType { START, END, UNKNOWN, RECURRENCE, RECURRENCE_PREFIX };

        int startIndex;
        String data;
        DateTimeType type;
        RecurInterval recurInterval;

        static final int INVALID_INDEX = -1;

        PossibleDateTime(String data, int index, DateTimeType type) {
            this.startIndex = index;
            this.type = type;
            this.data = data;
        }

        PossibleDateTime(String data, int index, DateTimeType type, RecurInterval recurInterval) {
            this(data, index, type);
            this.recurInterval = recurInterval;
        }

        void appendDateTime(String data) {
            this.data += " " + data;
        }
    }

    static class DateTimeProperties {
        PossibleDateTime start;
        PossibleDateTime end;
        PossibleDateTime recurrence;

        DateTimeProperties(PossibleDateTime start, PossibleDateTime end, PossibleDateTime recurrence) {
            this.start = start;
            this.end = end;
            this.recurrence = recurrence;
        }
    }

    /**
     * Filters all possible date/time strings into just 1 of each type. This method selects PossibleDateTimes that
     * can be parsed and appears last in the String
     * @param input String to parse
     * @return A DateTimeProperties object encapsulating the PossibleDateTime objects filtered
     */
    private static DateTimeProperties filterPossibleDateTime(Stack<PossibleDateTime> dateTimeStack) {
        PossibleDateTime recurInterval = null;
        PossibleDateTime startDateTime = null;
        PossibleDateTime endDateTime = null;
        while (!dateTimeStack.isEmpty()) {
            PossibleDateTime current = dateTimeStack.pop();
            if (current.type == DateTimeType.RECURRENCE && recurInterval == null) {
                recurInterval = current;
            } else if (current.type == DateTimeType.RECURRENCE_PREFIX && recurInterval == null) {
                try {
                    current.recurInterval = ParserUtil.parseStringToRecurInterval(
                                            current.data.replaceFirst(RECURRENCE_IDENTIFIER_PREFIX, ""));
                    recurInterval = current;
                } catch (IllegalValueException ex) {
                    continue;
                }
            } else if (!ParserUtil.canParse(current.data)) {
                continue;
            } else if (current.type == DateTimeType.END && endDateTime == null) {
                endDateTime = current;
            } else if (current.type == DateTimeType.START && startDateTime == null) {
                startDateTime = current;
            }
        }
        return new DateTimeProperties(startDateTime, endDateTime, recurInterval);
    }

}
