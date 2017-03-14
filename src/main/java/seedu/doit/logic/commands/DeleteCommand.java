package seedu.doit.logic.commands;

import seedu.doit.commons.core.Messages;
import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.model.item.ReadOnlyEvent;
import seedu.doit.model.item.ReadOnlyFloatingTask;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.UniqueEventList.EventNotFoundException;
import seedu.doit.model.item.UniqueFloatingTaskList.FloatingTaskNotFoundException;
import seedu.doit.model.item.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD

        + ": Deletes the task identified by the index number used in the last task list.\n"

        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_DELETE_FLOATING_TASK_SUCCESS = "Deleted Floating Task: %1$s";
    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";


    public final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownTaskList = model.getFilteredTaskList();
        UnmodifiableObservableList<ReadOnlyFloatingTask> lastShownFloatingTaskList = model
                .getFilteredFloatingTaskList();
        UnmodifiableObservableList<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();

        int taskSize = lastShownTaskList.size();
        int taskAndEventSize = taskSize + lastShownEventList.size();
        int totalSize = taskAndEventSize + lastShownFloatingTaskList.size();

        System.out.println("taskSize = " + taskSize);
        System.out.println("taskAndEventSize = " + taskAndEventSize);
        System.out.println("totalSize = " + totalSize);


        if (targetIndex <= taskSize) {
            ReadOnlyTask taskToDelete = lastShownTaskList.get(targetIndex - 1);

            try {
                model.deleteTask(taskToDelete);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }

            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
        } else if (taskSize < targetIndex &&  targetIndex <= taskAndEventSize) {
            ReadOnlyEvent taskToDelete = lastShownEventList.get(targetIndex - 1 - taskSize);

            try {
                model.deleteEvent(taskToDelete);
            } catch (EventNotFoundException pnfe) {
                assert false : "The target event cannot be missing";
            }

            return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, taskToDelete));
        } else if (taskAndEventSize < targetIndex &&  targetIndex <= totalSize) {
            ReadOnlyFloatingTask taskToDelete = lastShownFloatingTaskList.get(targetIndex - 1);

            try {
                model.deleteFloatingTask(taskToDelete);
            } catch (FloatingTaskNotFoundException pnfe) {
                assert false : "The target floating task cannot be missing";
            }

            return new CommandResult(String.format(MESSAGE_DELETE_FLOATING_TASK_SUCCESS, taskToDelete));
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }

}
