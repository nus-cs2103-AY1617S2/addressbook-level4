package project.taskcrusher.logic.commands;

import project.taskcrusher.commons.core.Messages;
import project.taskcrusher.commons.core.UnmodifiableObservableList;
import project.taskcrusher.logic.commands.exceptions.CommandException;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.event.UniqueEventList.EventNotFoundException;
import project.taskcrusher.model.task.ReadOnlyTask;
import project.taskcrusher.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address
 * book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    // TODO update this as well
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task/event identified by the index number used in the last task/event listing.\n"
            + "Parameters: FLAG INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " e 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final int targetIndex;
    public final String flag;

    public DeleteCommand(String flag, int targetIndex) {
        this.flag = flag;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        // TODO define these flags in a central place sometime perhaps
        assert (this.flag.equals(AddCommand.EVENT_FLAG) || this.flag.equals(AddCommand.TASK_FLAG));

        if (this.flag.equals(AddCommand.TASK_FLAG)) {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

            if (lastShownList.size() < targetIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

            try {
                model.deleteTask(taskToDelete);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }

            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
        } else {
            UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

            if (lastShownList.size() < targetIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
            }

            ReadOnlyEvent eventToDelete = lastShownList.get(targetIndex - 1);

            try {
                model.deleteEvent(eventToDelete);
            } catch (EventNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }

            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, eventToDelete));
        }

    }

}
