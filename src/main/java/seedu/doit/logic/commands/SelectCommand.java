package seedu.doit.logic.commands;

import seedu.doit.commons.core.EventsCenter;
import seedu.doit.commons.core.Messages;
import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.commons.events.ui.JumpToListRequestEvent;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.model.item.ReadOnlyTask;

/**
 * Selects a task identified using it's last displayed index from the task manager.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Selects the task identified by the index number used in the last task listing.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";
    public final int targetIndex;

    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    private String getMessageToDisplay() {
        String firstLine = String.format(MESSAGE_SELECT_TASK_SUCCESS, this.targetIndex);
        ReadOnlyTask task =  this.model.getFilteredTaskList().get(this.targetIndex - 1);
        String taskDetails = "Name: \t\t\t".concat(task.getName().toString()).concat("\n").
                concat("Description: \t\t").concat(task.getDescription().toString()).concat("\n").
                concat("Priority: \t\t\t").concat(task.getPriority().toString()).concat("\n");
        if (task.hasStartTime()) {
            taskDetails = taskDetails.concat("Start Time: \t\t").concat(task.getStartTime().toString()).concat("\n");
        }
        if (task.hasEndTime()) {
            taskDetails = taskDetails.concat("End Time: \t\t").concat(task.getDeadline().toString()).concat("\n");
        }
        //tag not yet implemented
        return firstLine.concat("\n\n").concat(taskDetails);
    }
    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = this.model.getFilteredTaskList();

        if (lastShownList.size() < this.targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(this.targetIndex - 1));
        return new CommandResult(getMessageToDisplay());

    }

}
