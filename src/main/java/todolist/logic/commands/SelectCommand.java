package todolist.logic.commands;

import todolist.commons.core.EventsCenter;
import todolist.commons.core.Messages;
import todolist.commons.core.UnmodifiableObservableList;
import todolist.commons.events.ui.JumpToListRequestEvent;
import todolist.logic.commands.exceptions.CommandException;
import todolist.model.task.ReadOnlyTask;

/**
 * Selects a task identified using it's last displayed index from the address book.
 */
public class SelectCommand extends Command {

    public final int targetIndex;

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

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));

        ReadOnlyTask task = model.getFilteredTaskList().get(targetIndex - 1);

        String selectCommandResult = String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex) + "\n"
                + "Task selected: " + task.getTitle().title + "\n"
                + "Description of task: " + task.getDescription().toString();
        return new CommandResult(selectCommandResult);

    }

}
