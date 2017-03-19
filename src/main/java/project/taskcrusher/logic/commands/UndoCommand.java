package project.taskcrusher.logic.commands;

import project.taskcrusher.commons.core.Messages;
import project.taskcrusher.commons.core.UnmodifiableObservableList;
import project.taskcrusher.logic.commands.exceptions.CommandException;
import project.taskcrusher.model.task.ReadOnlyTask;
import project.taskcrusher.model.task.Task;
import project.taskcrusher.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undo the last task  used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD ;

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Undo Task:";

  //  public final int targetIndex;

    public UndoCommand() {
       // this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        /*if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }*/

        ReadOnlyTask taskToDelete = lastShownList.get(lastShownList.size()-1);
        final Task toAdd;
        String delete = "delete";
        String add = "add";

        try {
        	model.deleteTask(taskToDelete);
           /*if(delete.equals("delete")){
        	  // model.addTask(taskToDelete);
           }else if(add.equals("add")){
        	   model.deleteTask(taskToDelete);
           }*/
           
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
