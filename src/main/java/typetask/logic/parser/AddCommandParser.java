package typetask.logic.parser;

import static typetask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static typetask.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_DATE;
import static typetask.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_END_DATE;
import static typetask.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_START_DATE;
import static typetask.commons.core.Messages.MESSAGE_INVALID_START_AND_END_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_END_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static typetask.logic.parser.CliSyntax.PREFIX_START_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import typetask.commons.exceptions.IllegalValueException;
import typetask.logic.commands.AddCommand;
import typetask.logic.commands.Command;
import typetask.logic.commands.IncorrectCommand;
//@@author A0139926R
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {
    private final int floatingTask = 0;
    private final int deadlineTaskWithTime = 1;
    private final int deadlineTaskWithDate = 2;
    private final int eventTask = 3;
    private final int invalidEvent = 4;
    //private final int priorityTask = 5;
    private final int day = 0;
    private final int month = 1;
    private final int dayDate = 2;
    private final int time = 3;
    private final int year = 5;
    private final int dateFromUser = 0;
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DATE, PREFIX_TIME, PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_PRIORITY);
        argsTokenizer.tokenize(args);
        try {
            int taskType = checkTaskType(argsTokenizer);
            return getCorrectAddCommand(argsTokenizer, taskType);

        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    private Command getCorrectAddCommand(ArgumentTokenizer argsTokenizer, int taskType) throws IllegalValueException {
        if (taskType == eventTask) {
            List<Date> startDate = getDate(argsTokenizer.getValue(PREFIX_START_DATE).get());
            List<Date> endDate = getDate(argsTokenizer.getValue(PREFIX_END_DATE).get());
            if (!checkValidDateFormat(startDate)) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_DATE_FORMAT_FOR_START_DATE));
            }
            if (!checkValidDateFormat(endDate)) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_DATE_FORMAT_FOR_END_DATE));
            }
            if (checkValidSchedule(startDate, endDate)) {
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        getDateString(startDate),
                        getDateString(endDate)
                        );
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_START_AND_END_DATE));
            }
        } else if (taskType == deadlineTaskWithDate) {
            List<Date> deadline = getDate(argsTokenizer.getValue(PREFIX_DATE).get());
            if (!checkValidDateFormat(deadline)) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_DATE_FORMAT_FOR_DATE));
            }
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    getDateString(deadline)
                    );
        } else if (taskType == deadlineTaskWithTime) {
            List<Date> deadline = getDate(argsTokenizer.getValue(PREFIX_TIME).get());
            if (!checkValidDateFormat(deadline)) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_DATE_FORMAT_FOR_DATE));
            }
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    getDateString(deadline)
                    );
        } else if (taskType == invalidEvent) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } else {
            return new AddCommand(
                    argsTokenizer.getPreamble().get()
                    );
        }
    }
    //@@author A0139926R
    public int checkTaskType(ArgumentTokenizer argsTokenizer) {
        if (argsTokenizer.getValue(PREFIX_START_DATE).isPresent() &&
                argsTokenizer.getValue(PREFIX_END_DATE).isPresent()) {
            return eventTask;
        } else if (argsTokenizer.getValue(PREFIX_DATE).isPresent()) {
            return deadlineTaskWithDate;
        } else if (argsTokenizer.getValue(PREFIX_TIME).isPresent()) {
            return deadlineTaskWithTime;
        } else if (argsTokenizer.getValue(PREFIX_START_DATE).isPresent() ||
                argsTokenizer.getValue(PREFIX_END_DATE).isPresent()) {
            return invalidEvent;
        } else {
            return floatingTask;
        }
    }
    //@@author A0139926R
    public List<Date> getDate(String date) {
        assert date != null;
        List<Date> dates = DateParser.parse(date);
        return dates;
    }
    public String getDateString(List<Date> dates) {
        String finalizedDate;
        String nattyDate = dates.get(dateFromUser).toString();
        String[] splitDate = nattyDate.split(" ");
        finalizedDate = splitDate[day] + " " + splitDate[month] + " " + splitDate[dayDate] +
                " " + splitDate[year] + " " + splitDate[time];
        return finalizedDate;
    }
    //Compares the start date and end date
    public boolean checkValidSchedule(List<Date> startDate, List<Date> endDate) {
        boolean isValidDate = false;
        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            Date sDate = startDate.get(dateFromUser);
            Date eDate = endDate.get(dateFromUser);
            isValidDate = checkValidEventDate(sDate, eDate);
        }
        return isValidDate;
    }
    //Checks for valid start and end date
    private boolean checkValidEventDate(Date startDate, Date endDate) {
        boolean isValidDate = false;
        if (startDate.before(endDate)) {
            isValidDate = true;
        }
        return isValidDate;
    }
    private boolean checkValidDateFormat(List<Date> date) {
        boolean isValidDate = false;
        if (!date.isEmpty()) {
            isValidDate = true;
        }
        return isValidDate;
    }
}
