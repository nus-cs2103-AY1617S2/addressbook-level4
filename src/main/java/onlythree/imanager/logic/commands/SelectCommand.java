package onlythree.imanager.logic.commands;

import onlythree.imanager.commons.core.EventsCenter;
import onlythree.imanager.commons.core.Messages;
import onlythree.imanager.commons.core.UnmodifiableObservableList;
import onlythree.imanager.commons.events.ui.JumpToListRequestEvent;
import onlythree.imanager.logic.commands.exceptions.CommandException;
import onlythree.imanager.model.task.ReadOnlyTask;

/**
 * Selects a task identified using it's last displayed index from the task list.
 */
public class SelectCommand extends Command {

    public int targetIndex;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        if (lastShownList.size() < targetIndex) {
            targetIndex = lastShownList.size();
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex));

    }

}
