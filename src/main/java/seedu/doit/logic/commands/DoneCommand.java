package seedu.doit.logic.commands;

import seedu.doit.commons.core.Messages;
import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.model.item.ReadOnlyEvent;
import seedu.doit.model.item.ReadOnlyFloatingTask;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.UniqueEventList;
import seedu.doit.model.item.UniqueFloatingTaskList;
import seedu.doit.model.item.UniqueTaskList;

public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD

        + ": Completes the task identified by the index number used in the last task list.\n"

        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_DONE_FLOATING_TASK_SUCCESS = "Completed Floating Task: %1$s";
    public static final String MESSAGE_DONE_EVENT_SUCCESS = "Completed Event: %1$s";


    public final int targetIndex;

    public DoneCommand(int targetIndex) {
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

        if (targetIndex <= taskSize) {
            ReadOnlyTask taskToDone = lastShownTaskList.get(targetIndex - 1);

            try {
                model.deleteTask(taskToDone);
            } catch (UniqueTaskList.TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }

            return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToDone));
        } else if (taskSize < targetIndex &&  targetIndex <= taskAndEventSize) {
            ReadOnlyEvent taskToDone = lastShownEventList.get(targetIndex - 1 - taskSize);

            try {
                model.deleteEvent(taskToDone);
            } catch (UniqueEventList.EventNotFoundException pnfe) {
                assert false : "The target event cannot be missing";
            }

            return new CommandResult(String.format(MESSAGE_DONE_EVENT_SUCCESS, taskToDone));
        } else if (taskAndEventSize < targetIndex &&  targetIndex <= totalSize) {
            ReadOnlyFloatingTask taskToDone = lastShownFloatingTaskList.get(targetIndex - 1 - taskAndEventSize);

            try {
                model.deleteFloatingTask(taskToDone);
            } catch (UniqueFloatingTaskList.FloatingTaskNotFoundException pnfe) {
                assert false : "The target floating task cannot be missing";
            }

            return new CommandResult(String.format(MESSAGE_DONE_FLOATING_TASK_SUCCESS, taskToDone));
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }
}
