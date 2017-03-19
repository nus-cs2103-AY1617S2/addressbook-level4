package project.taskcrusher.logic.commands;

import java.util.List;

import project.taskcrusher.commons.core.Messages;
import project.taskcrusher.commons.core.UnmodifiableObservableList;
import project.taskcrusher.logic.commands.exceptions.CommandException;
import project.taskcrusher.model.task.ReadOnlyTask;
import project.taskcrusher.model.task.Task;
import project.taskcrusher.model.task.UniqueTaskList;
import project.taskcrusher.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Complete the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Complete Task: %1$s";
    
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the active list.";

    public final int targetIndex;

    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    
/*    List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

    if (filteredTaskListIndex >= lastShownList.size()) {
        throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
    Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

    try {
        model.updateTask(filteredTaskListIndex, editedTask);
    } catch (UniqueTaskList.DuplicateTaskException dpe) {
        throw new CommandException(MESSAGE_DUPLICATE_TASK);
    }
    model.updateFilteredListToShowAll();*/


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMarkDone = lastShownList.get(targetIndex - 1);

        try {
            model.updateTask(targetIndex,taskToMarkDone);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
        	throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToMarkDone));
    }

}
