package seedu.taskmanager.logic.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.logic.commands.exceptions.CommandException;

// @@author A0140032E
/**
 * Finds and lists all tasks in task manager which fall in the date(s) range
 * requirements.
 */
public class FindDateCommand extends Command {

    private boolean isRange;
    private Date startDateRange, endDateRange;

    private static final SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");

    public static final String COMMAND_WORD = "findbydate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all non-floating tasks which is within "
            + "the specified date or date range and displays them as a list with index numbers.\n"
            + "Parameters: DATE [to DATE]\n" + "Example: " + COMMAND_WORD + " 12/03/2017\n" + COMMAND_WORD
            + "10/03/2017 to 15/03/2017";
    public static final String MESSAGE_INVALID_RANGE = "Invalid date range. "
            + "Either provide a single date or a starting date to ending date";
    public static final String MESSAGE_SUCCESS = "Finding tasks ";

    public FindDateCommand(String date) throws IllegalValueException {
        isRange = false;
        try {
            startDateRange = new Date(sdfInput.parse(date).getTime());
        } catch (ParseException e) {
            Parser parser = new Parser();
            List<DateGroup> dateGroups = parser.parse(date);

            if (!dateGroups.isEmpty() && !dateGroups.get(0).getDates().isEmpty()) {
                startDateRange = dateGroups.get(0).getDates().get(0);
            } else {
                throw new IllegalValueException(MESSAGE_INVALID_RANGE);
            }

        }

    }

    public FindDateCommand(String startDate, String endDate) throws IllegalValueException {
        isRange = true;

        try {
            startDateRange = new Date(sdfInput.parse(startDate).getTime());
        } catch (ParseException e) {
            Parser parser = new Parser();
            List<DateGroup> dateGroups = parser.parse(startDate);

            if (!dateGroups.isEmpty() && !dateGroups.get(0).getDates().isEmpty()) {
                startDateRange = dateGroups.get(0).getDates().get(0);
            } else {
                throw new IllegalValueException(MESSAGE_INVALID_RANGE);
            }
        }

        try {
            endDateRange = new Date(sdfInput.parse(endDate).getTime());
        } catch (ParseException e) {
            Parser parser = new Parser();
            List<DateGroup> dateGroups = parser.parse(endDate);

            if (!dateGroups.isEmpty() && !dateGroups.get(0).getDates().isEmpty()) {
                endDateRange = dateGroups.get(0).getDates().get(0);
            } else {
                throw new IllegalValueException(MESSAGE_INVALID_RANGE);
            }
        }

        if (startDateRange.after(endDateRange)) {
            Date temp = startDateRange;
            startDateRange = endDateRange;
            endDateRange = temp;
        }

    }

    @Override
    public CommandResult execute() throws CommandException {
        StringBuilder sb = new StringBuilder(MESSAGE_SUCCESS);
        if (isRange) {
            model.updateFilteredTaskList(startDateRange, endDateRange);
            sb.append(sdfOutput.format(startDateRange));
            sb.append(" to ");
            sb.append(sdfOutput.format(endDateRange));
        } else {
            model.updateFilteredTaskList(startDateRange);
            sb.append("on ");
            sb.append(sdfOutput.format(startDateRange));
        }
        sb.append("\n");
        return new CommandResult(sb.toString() + getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
// @@author
