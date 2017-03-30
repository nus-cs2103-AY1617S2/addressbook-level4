package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.TaskStatus;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0146757R
/**
 * Mark a task as completed which is identified using it's last displayed index
 * from the task manager.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
	    + ": Mark a task as completed which is identified by the index number used in the last task listing.\n"
	    + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETED_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_ALREADY_COMPLETED = "The task is already done.";
    public final String MESSAGE_DUPLICATE = "The task is a duplicate of an existing task.";
    public final String MESSAGE_NOT_FOUND = "The task was not found.";

    public int[] targetIndex;
    public String targetStatus;

    public DoneCommand(int[] targetIndex) {
	this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {
    	
    	assert model != null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        StringBuilder sb = new StringBuilder();
	for (int i = 0; i < targetIndex.length; i++) {
        if (lastShownList.size() < targetIndex[i] - i) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        }
        ReadOnlyTask taskToComplete = lastShownList.get(targetIndex[i] - 1 - i);

        targetStatus= taskToComplete.getTaskStatus().status;
        if (targetStatus.equals(TaskStatus.MESSAGE_DONE)) {
        	return new CommandResult(MESSAGE_ALREADY_COMPLETED);
        }
        
        try {
            model.completeTask(targetIndex[i] - 1);
            //to focus on the completed task
            EventsCenter.getInstance().post(new JumpToListRequestEvent(model.getFilteredTaskList().size() - 1));
            
            
       } catch (TaskNotFoundException pnfe) {
			//remove this command from list for undo
	   assert false : "The target task cannot be found";
        }
        sb.append(String.format(MESSAGE_COMPLETED_TASK_SUCCESS, targetIndex[i]));
	    sb.append("\n");
	}
        return new CommandResult(sb.toString());
    }
    public boolean isUndoable() {
    	return true;
    }
}
//@@author
