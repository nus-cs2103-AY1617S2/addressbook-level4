package seedu.address.logic.commands;

import java.util.Date;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;

//@@author A0162877N
/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String DEFAULT_STARTTIME = "00:00:00";
    public static final String DEFAULT_ENDTIME = "23:59:59";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " [by DEADLINE] | [from STARTDATE to ENDDATE]";
    private final String endDate;
    private final String startDate;
    private final Boolean isCompleted;

    public ListCommand() {
        isCompleted = null;
        endDate = "";
        startDate = "";
    }

    public ListCommand(Boolean isCompleted) {
        this.isCompleted = isCompleted;
        endDate = "";
        startDate = "";

    }

    public ListCommand(String endDate) throws IllegalDateTimeValueException {
        isCompleted = null;
        this.endDate = endDate;
        this.startDate = "";
    }

    public ListCommand(String startDate, String endDate) throws IllegalDateTimeValueException {
        isCompleted = null;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public CommandResult execute() {
        try {
            executeListCommandLogic();
        } catch (IllegalDateTimeValueException e) {
            e.printStackTrace();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public void executeListCommandLogic() throws IllegalDateTimeValueException {
        if ("".equals(startDate) && isParsableDate(endDate)) { // by prefix is used by user
            executeListEndDate();
        } else if (isParsableDate(startDate) && isParsableDate(endDate)) { // from and to prefix is used by user
            executeListDateRange();
        } else if (isCompleted != null) {
            model.updateFilteredTaskList(isCompleted);
        } else if ("".equals(startDate) && "".equals(endDate)) {
            model.updateFilteredListToShowAll();
        } else {
            throw new IllegalDateTimeValueException();
        }
    }

    public void executeListDateRange() {
        Date start = null;
        Date end = null;

        if (dtParser.parse(endDate).get(0).isTimeInferred()) {
            end = dtParser.parse(endDate + " " + DEFAULT_ENDTIME).get(0).getDates().get(0);
        } else {
            end = dtParser.parse(endDate).get(0).getDates().get(0);
        }

        if (dtParser.parse(startDate).get(0).isTimeInferred()) {
            start = dtParser.parse(startDate + " " + DEFAULT_STARTTIME).get(0).getDates().get(0);
        } else {
            start = dtParser.parse(startDate).get(0).getDates().get(0);
        }

        if (end.before(start)) {
            model.updateFilteredTaskList(end, start);
        } else {
            model.updateFilteredTaskList(start, end);
        }
    }

    public void executeListEndDate() {
        Date start = null;
        Date end = null;
        if (dtParser.parse(endDate).get(0).isTimeInferred()) {
            end = dtParser.parse(endDate + " " + DEFAULT_ENDTIME).get(0).getDates().get(0);
        } else {
            end = dtParser.parse(endDate).get(0).getDates().get(0);
        }

        start = dtParser.parse("today " + DEFAULT_STARTTIME).get(0).getDates().get(0);
        model.updateFilteredTaskList(start, end);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public boolean isParsableDate(String dateTime) {
        return !dtParser.parse(dateTime).isEmpty();
    }

    @Override
    public boolean isMutating() {
        return false;
    }
}
