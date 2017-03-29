package seedu.watodo.logic.commands;

import seedu.watodo.commons.exceptions.IllegalValueException;

//@@author A0139872R-reused
/**
 * Lists all tasks scheduled on the current week in the task manager to the user.
 */
public class ListWeekCommand extends ListCommand {

    public static final String COMMAND_WORD = "week";

    public static final String MESSAGE_SUCCESS = "Listed this week's tasks";

    public static final int DEADLINE = 7;

    @Override
    public CommandResult execute() {
        try {
            model.updateFilteredByDatesTaskList(DEADLINE);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
