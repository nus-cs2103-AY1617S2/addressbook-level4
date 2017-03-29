package seedu.opus.logic.parser;

import static seedu.opus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.joestelmach.natty.DateGroup;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.logic.commands.Command;
import seedu.opus.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.opus.logic.commands.IncorrectCommand;
import seedu.opus.logic.commands.ScheduleCommand;
import seedu.opus.model.task.DateTime;

//@@author A0126345J
/**
 * Parses input arguments and creates a new ScheduleCommand object
 */
public class ScheduleCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleCommand
     * and returns an ScheduleCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;

        Optional<DateTime> startTime = Optional.empty();
        Optional<DateTime> endTime = Optional.empty();
        String[] preambleFields = args.trim().split(" ", 2);

        if (preambleFields.length < 2) {
            return new IncorrectCommand(ScheduleCommand.MESSAGE_NOT_SCHEDULED);
        }

        Optional<Integer> index = ParserUtil.parseIndex(preambleFields[0]);
        Optional<DateGroup> dateGroup = DateTimeParser.parseDateGroup(preambleFields[1]);

        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }

        if (!dateGroup.isPresent()) {
            return new IncorrectCommand(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
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

            startTime.ifPresent(sTime -> editTaskDescriptor.setStartTime(Optional.of(sTime)));
            endTime.ifPresent(eTime -> editTaskDescriptor.setEndTime(Optional.of(eTime)));

        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        return new ScheduleCommand(index.get(), editTaskDescriptor);
    }

}
