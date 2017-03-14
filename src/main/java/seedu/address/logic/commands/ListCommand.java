package seedu.address.logic.commands;

import java.util.Date;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.logic.parser.DateTimeParser;
import seedu.address.logic.parser.DateTimeParserManager;

/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " [by DEADLINE]";
    private static DateTimeParser dateParser;
    private final Date endDate;
    private final Date startDate;
    private final Boolean isCompleted;

    public ListCommand() {
        isCompleted = null;
        endDate = null;
        startDate = null;
    }

    public ListCommand(String endDate) throws IllegalDateTimeValueException {
        isCompleted = null;
        if (isParsableDate(endDate)) {
            if (endDate.matches("[a-zA-Z]+")) {
                this.endDate = dateParser.parse(endDate + "235959").get(0).getDates().get(0);
            } else {
                this.endDate = dateParser.parse(endDate).get(0).getDates().get(0);
            }
        } else {
            throw new IllegalDateTimeValueException();
        }
        this.startDate = dateParser.parse("today 000000").get(0).getDates().get(0);
    }

    public ListCommand(String startDate, String endDate) throws IllegalDateTimeValueException {
        isCompleted = null;
        if (isParsableDate(startDate) && isParsableDate(endDate)) {
            if (startDate.matches("[a-zA-Z]+")) {
                this.startDate = dateParser.parse(startDate + " 000000").get(0).getDates().get(0);
            } else {
                this.startDate = dateParser.parse(startDate).get(0).getDates().get(0);
            }

            if (endDate.matches("[a-zA-Z]+")) {
                this.endDate = dateParser.parse(endDate + " 235959").get(0).getDates().get(0);
            } else {
                this.endDate = dateParser.parse(endDate).get(0).getDates().get(0);
            }
        } else {
            throw new IllegalDateTimeValueException();
        }
    }

    public ListCommand(Boolean isCompleted) {
        this.isCompleted = isCompleted;
        startDate  = null;
        endDate = null;
    }

    @Override
    public CommandResult execute() {
        if (endDate != null && startDate != null) {
            model.updateFilteredTaskList(startDate, endDate);
        } else if (isCompleted != null) {
            model.updateFilteredTaskList(isCompleted);
        } else {
            model.updateFilteredListToShowAll();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public boolean isParsableDate(String dateTime) {
        dateParser = new DateTimeParserManager();
        return dateParser.parse(dateTime).size() > 0;
    }
}
