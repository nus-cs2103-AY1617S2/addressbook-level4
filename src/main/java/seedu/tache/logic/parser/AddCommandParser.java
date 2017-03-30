package seedu.tache.logic.parser;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_DELIMITER;

import java.util.Deque;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.logic.commands.AddCommand;
import seedu.tache.logic.commands.Command;
import seedu.tache.logic.commands.IncorrectCommand;
import seedu.tache.logic.parser.ParserUtil.DateTimeType;
import seedu.tache.logic.parser.ParserUtil.PossibleDateTime;

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
            for (String tag: taskTag[1].split(" ")) {
                tagSet.add(tag);
            }
        }

        String taskWithoutTags = taskTag[0];
        Deque<PossibleDateTime> possibleDateTimes = ParserUtil.findDateTime(taskWithoutTags);
        PossibleDateTime startDateTime = null;
        PossibleDateTime endDateTime = null;
        while (!possibleDateTimes.isEmpty()) {
            PossibleDateTime current = possibleDateTimes.pop();

            if (current.type == DateTimeType.END && endDateTime == null) {
                endDateTime = current;
            } else if (current.type == DateTimeType.START && startDateTime == null) {
                startDateTime = current;
            }
        }
        if (endDateTime == null && startDateTime != null) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } else if (endDateTime == null && startDateTime == null) {
            try {
                return new AddCommand(taskWithoutTags, Optional.empty(), Optional.empty(), tagSet);
            } catch (IllegalValueException ex) {
                return new IncorrectCommand(ex.getMessage());
            }
        } else {
            int startOfDateTimeIndex = endDateTime.startIndex;
            if (startDateTime != null) {
                startOfDateTimeIndex = Math.min(startOfDateTimeIndex, startDateTime.startIndex);
            }
            StringBuilder sb = new StringBuilder();
            String[] taskNameSegments = taskWithoutTags.split(" ");
            for (int i = 0; i < startOfDateTimeIndex; i++) {
                sb.append(taskNameSegments[i]).append(" ");
            }
            String taskName = sb.toString();
            Optional<String> endDateTimeStr = Optional.of(endDateTime.data);
            Optional<String> startDateTimeStr;
            if (startDateTime != null) {
                startDateTimeStr = Optional.of(startDateTime.data);
            } else {
                startDateTimeStr = Optional.empty();
            }
            try {
                return new AddCommand(taskName, startDateTimeStr, endDateTimeStr, tagSet);
            } catch (IllegalValueException ex) {
                return new IncorrectCommand(ex.getMessage());
            }
        }
    }

    public Command parseStructured(String args) {
        String[] taskFields = args.split(PARAMETER_DELIMITER);
        Set<String> tagSet = new HashSet<String>();
        if (taskFields.length == 0) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } else {
            String name = taskFields[0];
            Optional<String> startDateTime = Optional.empty();
            Optional<String> endDateTime = Optional.empty();
            for (int i = 1; i < taskFields.length; i++) {
                String currentChunk = taskFields[i];
                if (ParserUtil.hasDate(currentChunk) || ParserUtil.hasTime(currentChunk)) {
                    if (!endDateTime.isPresent()) {
                        endDateTime = Optional.of(taskFields[i]);
                    } else if (!startDateTime.isPresent()) {
                        startDateTime = endDateTime;
                        endDateTime = Optional.of(taskFields[i]);
                    } else {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                AddCommand.MESSAGE_USAGE));
                    }
                } else {
                    tagSet.add(taskFields[i]);
                }
            }
            try {
                return new AddCommand(name, startDateTime, endDateTime, tagSet);
            } catch (IllegalValueException e) {
                // TODO Auto-generated catch block
                return new IncorrectCommand(e.getMessage());
            }
        }
    }

}
