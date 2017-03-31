//@@ author A0138377U
package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

/**
 * Deletes a task identified using it's last displayed index from the address book.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the status of the task identified by the "
            + "index number used in the last task listing to Done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task: %1$s";

    public final int targetIndex;

    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);
        Task markedTask = null;
        try {
            markedTask = (!taskToMark.getStatus().toString().equals("Done") ?
                    createMarkedTask(taskToMark, "Done") : createMarkedTask(taskToMark, "UnDone"));
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.updateCopy(model.getTaskManager());
        model.updateFlag("undo copy");
        model.updateTask(targetIndex - 1, markedTask);
        model.updateFilteredListToShowAll();

        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     * @throws IllegalValueException
     */
    private static Task createMarkedTask(ReadOnlyTask taskToEdit, String newStatus) throws IllegalValueException {
        assert taskToEdit != null;

        Name updatedName = taskToEdit.getName();
        Deadline updatedDeadline = taskToEdit.getDeadline();
        Description updatedDescription = taskToEdit.getDescription();
        Status updatedStatus = createStatus(newStatus);
        UniqueTagList updatedTags = taskToEdit.getTags();

        return new Task(updatedName, updatedDeadline, updatedDescription, updatedStatus, updatedTags);
    }

    //@@author A0138377U
    public static Status createStatus(String status) throws IllegalValueException {
        return (status.equals("Done")) ? new Status("Done") : new Status("Undone");
    }
}
