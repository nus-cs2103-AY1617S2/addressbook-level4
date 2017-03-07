package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> parent of 9b5fb6b... test
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.UniquePersonList.PersonNotFoundException;
=======
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
>>>>>>> a767941edae67662e99e1bfd4f1f28910f9d385f

/**
 * Deletes a task identified using its last displayed index from the task manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

<<<<<<< HEAD
<<<<<<< HEAD
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();
=======
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
>>>>>>> a767941edae67662e99e1bfd4f1f28910f9d385f
=======
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();
>>>>>>> parent of 9b5fb6b... test

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

<<<<<<< HEAD
<<<<<<< HEAD
        ReadOnlyTask personToDelete = lastShownList.get(targetIndex - 1);
=======
        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);
>>>>>>> a767941edae67662e99e1bfd4f1f28910f9d385f
=======
        ReadOnlyTask personToDelete = lastShownList.get(targetIndex - 1);
>>>>>>> parent of 9b5fb6b... test

        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
