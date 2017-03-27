package seedu.onetwodo.logic.commands;

import seedu.onetwodo.commons.core.EventsCenter;
import seedu.onetwodo.commons.core.Messages;
import seedu.onetwodo.commons.events.ui.JumpToListRequestEvent;
import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.model.Model;
import seedu.onetwodo.model.task.ReadOnlyTask;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
	private static int SELECTION_TIMEOUT = 200; 

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     * @throws IllegalValueException
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model) {
        this.model = model;
    }

    /**
     * Scroll to task provided
     *
     * @param task to jump to
     */
    public void jumpToNewTask(ReadOnlyTask task) {
        int filteredIndex = model.getTaskIndex(task);
        new java.util.Timer().schedule( 
        		new java.util.TimerTask() {
                    @Override
                    public void run() {
                        EventsCenter.getInstance().post(new JumpToListRequestEvent(filteredIndex, task.getTaskType()));
                    }
                }, 
                SELECTION_TIMEOUT
		);
	}

}
