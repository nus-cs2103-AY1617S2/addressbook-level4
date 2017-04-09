package seedu.watodo.logic.commands;

import java.util.Optional;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.task.DateTime;

//@@author A0139872R
/**
 * Lists all tasks scheduled on the range of dates specified in the task manager to the user.
 */
public class ListDateCommand extends ListCommand {

    public static final String MESSAGE_SUCCESS = "Lists all tasks scheduled within the range of dates specified";

    DateTime start = null;
    DateTime end = null;

    public ListDateCommand(Optional<String> startDate, Optional<String> endDate) throws IllegalValueException {
        assert endDate.isPresent();
        if (startDate.isPresent()) {
            this.start = new DateTime(startDate.get());
        }
        this.end = new DateTime(endDate.get());

        if (startDate.isPresent() && endDate.isPresent()) {
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
