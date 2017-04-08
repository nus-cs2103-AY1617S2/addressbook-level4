package seedu.opus.logic.commands;

import java.util.Optional;

import seedu.opus.commons.core.UnmodifiableObservableList;
import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.opus.logic.commands.exceptions.CommandException;
import seedu.opus.model.task.ReadOnlyTask;
import seedu.opus.model.task.Status;

//@@author A0124368A
/**
 * Toggles a single task in the task manager as complete/incomplete.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = "";
    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task: %1$s as complete";

    private final int filteredTaskListIndex;

    public MarkCommand(int filteredTaskListIndex) {
        this.filteredTaskListIndex = filteredTaskListIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Status status;
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            status = getNewStatusForTask(filteredTaskListIndex);
        } catch (IllegalValueException e) {
            throw new CommandException("Invalid status created.");
        }

        editTaskDescriptor.setStatus(Optional.of(status));

        EditCommand editCommand = new EditCommand(filteredTaskListIndex, editTaskDescriptor);
        editCommand.setData(model);

        return editCommand.execute();
    }

    /** Returns incomplete status if task is complete, and complete status if task is incomplete */
    private Status getNewStatusForTask(int index) throws IllegalValueException {
        if (isTaskCompleteAt(filteredTaskListIndex)) {
            return new Status("incomplete");
        }

        return new Status("complete");
    }

    /** Returns the task at index inside filtered task list */
    private ReadOnlyTask getTask(int index) {
        UnmodifiableObservableList<ReadOnlyTask> list = model.getFilteredTaskList();
        return list.get(index - 1);
    }

    /** Returns a boolean which indicates if a task at the specified index is completed */
    private boolean isTaskCompleteAt(int index) {
        ReadOnlyTask task = getTask(index);
        return task.getStatus().isComplete();
    }

}
//@@author
