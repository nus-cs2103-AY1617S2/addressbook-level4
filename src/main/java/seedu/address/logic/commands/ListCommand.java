package seedu.address.logic.commands;

import java.util.Date;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.logic.LogicManager;

/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "LIST";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " [BY DEADLINE] | [FROM STARTDATE TO ENDDATE]";
    public static final String DATE_VALIDATION_REGEX = "[a-zA-Z]+";
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
        Date start;
        Date end;
        if ("".equals(startDate) && isParsableDate(endDate)) {
            if (endDate.matches(DATE_VALIDATION_REGEX)) {
                end = dtParser.parse(endDate + " 235959").get(0).getDates().get(0);
            } else {
                end = dtParser.parse(endDate).get(0).getDates().get(0);
            }
            start = dtParser.parse("today 000000").get(0).getDates().get(0);

            saveCurrentState();
            model.updateFilteredTaskList(start, end);
        } else if (isParsableDate(startDate) && isParsableDate(endDate)) {
            if (startDate.matches(DATE_VALIDATION_REGEX)) {
                start = dtParser.parse(startDate + " 000000").get(0).getDates().get(0);
            } else {
                start = dtParser.parse(startDate).get(0).getDates().get(0);
            }

            if (endDate.matches(DATE_VALIDATION_REGEX)) {
                end = dtParser.parse(endDate + " 235959").get(0).getDates().get(0);
            } else {
                end = dtParser.parse(endDate).get(0).getDates().get(0);
            }

            saveCurrentState();
            if (end.before(start)) {
                model.updateFilteredTaskList(end, start);
            } else {
                model.updateFilteredTaskList(start, end);
            }
        } else if (isCompleted != null) {
            model.updateFilteredTaskList(isCompleted);
        } else if ("".equals(startDate) && "".equals(endDate)) {
            saveCurrentState();
            model.updateFilteredListToShowAll();
        } else {
            throw new IllegalDateTimeValueException();
        }
    }

    /**
     * Save the data in task manager if command is mutating the data
     */
    public void saveCurrentState() {
        if (isMutating()) {
            try {
                LogicManager.undoCommandHistory.addStorageHistory(model.getRawTaskManager().getImmutableTaskList(),
                        model.getRawTaskManager().getImmutableLabelList());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
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
