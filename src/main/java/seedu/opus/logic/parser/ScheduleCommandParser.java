package seedu.opus.logic.parser;

import static seedu.opus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.joestelmach.natty.DateGroup;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.logic.commands.Command;
import seedu.opus.logic.commands.EditCommand;
import seedu.opus.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.opus.logic.commands.IncorrectCommand;
import seedu.opus.logic.commands.ScheduleCommand;
import seedu.opus.model.task.DateTime;

/**
 * Parses input arguments and creates a new ScheduleCommand object
 */
public class ScheduleCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;

        Optional<DateTime> startTime = Optional.empty();
        Optional<DateTime> endTime = Optional.empty();
        String[] preambleFields = args.trim().split(" ", 2);
        Optional<Integer> index = Optional.ofNullable(Integer.parseInt(preambleFields[0]));
        Optional<DateGroup> dateGroup = DateTimeParser.parseDateGroup(preambleFields[1]);

        if (!index.isPresent() || !dateGroup.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }

        List<Date> dates = dateGroup.get().getDates();

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {

            // if dates contains two date objects
            if (dates.size() == 2) {
                startTime = Optional.ofNullable(new DateTime(DateTimeParser.convertDateToLocalDateTime(dates.get(0))));
                endTime = Optional.ofNullable(new DateTime(DateTimeParser.convertDateToLocalDateTime(dates.get(1))));
            }

            // if dates contains only one object
            if (dates.size() == 1) {
                endTime = Optional.ofNullable(new DateTime(DateTimeParser.convertDateToLocalDateTime(dates.get(0))));
            }

            if (startTime.isPresent()) {
                editTaskDescriptor.setStartTime(startTime);
            }

            if (endTime.isPresent()) {
                editTaskDescriptor.setEndTime(endTime);
            }

        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new ScheduleCommand(index.get(), editTaskDescriptor);
    }

}
