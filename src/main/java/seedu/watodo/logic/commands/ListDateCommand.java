package seedu.watodo.logic.commands;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.task.DateTime;

//@@author A0139872R-reused
/**
 * Lists all tasks scheduled on the range of dates specified in the task manager to the user.
 */
public class ListDateCommand extends ListCommand {

    public static final String MESSAGE_SUCCESS = "Lists all tasks scheduled within the range of dates specified";

    DateTime start = null;
    DateTime end = null;
    
    public ListDateCommand(String startDate, String endDate) throws IllegalValueException {
        assert startDate != null || endDate != null;
        if(startDate != null) {
            this.start = new DateTime(startDate);
        }
        if(endDate != null) {
            this.end = new DateTime (endDate);
        }
        if(startDate != null && endDate != null) {
            if (start.isLater(end)) { //checks if the end time is later than start time
                throw new IllegalValueException(DateTime.MESSAGE_DATETIME_START_LATER_THAN_END);
            }
        }
    }

    @Override
    public CommandResult execute() {
        try {
            model.updateFilteredByDatesTaskList(start, end);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
