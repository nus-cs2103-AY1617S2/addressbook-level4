package seedu.ezdo.logic.commands;

import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.logic.commands.exceptions.CommandException;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList.TaskNotFoundException;

/**
 * Marks a task as identified using its last displayed index from ezDo as done
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the last task listing as done\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Done Task: %1$s";
    public static final String MESSAGE_DONE_LISTED = "Done Tasks listed";

    public final int targetIndex;
    public final boolean requestToViewDoneOnly;

    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.requestToViewDoneOnly = false;
    }

    public DoneCommand() {
        this.targetIndex = 0;
        this.requestToViewDoneOnly = true;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (requestToViewDoneOnly) {
            model.updateFilteredDoneList();
            return new CommandResult(MESSAGE_DONE_LISTED);
        }

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToDone = (Task) lastShownList.get(targetIndex - 1);

        try {
            model.doneTask(taskToDone);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToDone));
    }

}
