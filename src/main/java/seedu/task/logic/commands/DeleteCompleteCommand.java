package seedu.task.logic.commands;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0139161J
public class DeleteCompleteCommand extends Command {

    public static final String COMMAND_WORD = "deletecompleted";

    public static final String MESSAGE_SUCCESS = "Completed Task deleted: %1$s";

    public static final Object MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the completed task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";;

    public final int indexToDelete;
    public DeleteCompleteCommand(Integer index) {
        indexToDelete = index;
    }
    @Override
    public CommandResult execute() throws CommandException, IllegalValueException {
        assert model != null;

        UnmodifiableObservableList<ReadOnlyTask> list = model.getCompletedTaskList();
        ReadOnlyTask deletedTask = list.get(indexToDelete - 1);
        try {
            model.deleteCompletedTask(deletedTask);
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, deletedTask));
    }
}
