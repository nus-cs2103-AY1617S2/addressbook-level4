package seedu.address.logic.commands;

import java.util.Date;

/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " [by DEADLINE]";
    private final Date endTime;
    private final Date startTime;

    public ListCommand() {
        endTime = null;
        startTime = null;
    }

    public ListCommand(Date startTime, Date dateTime) {
        this.endTime = dateTime;
        this.startTime = startTime;
    }

    @Override
    public CommandResult execute() {
        if (endTime != null && startTime != null) {
            model.updateFilteredTaskList(startTime, endTime);
        } else {
            model.updateFilteredListToShowAll();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
