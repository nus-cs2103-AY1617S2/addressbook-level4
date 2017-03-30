//@@author A0119505J
package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.model.UndoInfo;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Undo's the last undo action made by the user that mutated the task list
 */

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Your previous action has been undone.";

    public static final String MESSAGE_FAILURE = "There are no changes that can be undone.";

    public static final int ADD_CMD_ID = 1;
    public static final int DEL_CMD_ID = 2;
    public static final int UPD_CMD_ID = 3;
    public static final int DONE_CMD_ID = 4;
    public static final int CLR_CMD_ID = 5;
    public static final int STR_CMD_ID = 6;
    
    private static final int CURRENT_TASK = 0;
    private UndoInfo undoInfo;


    @Override
    public CommandResult execute() {
        assert model != null;
        if((model.getUndoStack().isEmpty()))
           return new CommandResult(MESSAGE_FAILURE);
        undoInfo = model.getUndoStack().pop();
        // model.getRedoStack().push(undoInfo);
        int undoID = undoInfo.getUndoID();
        switch (undoID) {
            case ADD_CMD_ID:               
                undoAdd(undoInfo.getTasks().get(CURRENT_TASK));
                return new CommandResult(MESSAGE_SUCCESS);
            case DEL_CMD_ID:
                undoDelete(undoInfo.getTasks().get(CURRENT_TASK));
                return new CommandResult(MESSAGE_SUCCESS);    
            // case UPD_CMD_ID:
                // undoUpdate(undoInfo.getTasks().get(CURRENT_TASK), undoInfo.getTasks().get(ORIGINAL_TASK_INDEX));
                // return new CommandResult(MESSAGE_SUCCESS);
            // case DONE_CMD_ID:
                // undoDone(undoInfo.getTasks().get(CURRENT_TASK));
                // return new CommandResult(MESSAGE_SUCCESS);
            case CLR_CMD_ID:
                undoClear(undoInfo.getTasks());
                return new CommandResult(MESSAGE_SUCCESS);
            // case STR_CMD_ID:
                // undoSetStorage();
                // return new CommandResult(MESSAGE_SUCCESS);
            default:
                return new CommandResult(MESSAGE_FAILURE);
        }
    }
    
    // private void undoSetStorage() {
        // try {
            // String filePath = model.changeFileStorageUndo(undoInfo.getFilePath());
            // undoInfo.setFilePath(filePath);
    	// } catch (IOException | ParseException | JSONException e) {
    	    // e.printStackTrace();
    	// }
    // }
    
    private void undoClear(ArrayList<Task> tasks) {
        try {
            model.clearTaskUndo(tasks);
        } catch (TaskNotFoundException e) {
            assert false: "The target task cannot be missing";
        }
    }

    private void undoAdd(Task task) {
        try {
            model.deleteTaskUndo(task);
        } catch (TaskNotFoundException e) {
            assert false: "The target task cannot be missing";
        }
    }
    
    private void undoDelete(Task task) {
        try {
            model.addTaskUndo(task);  
        } catch (UniqueTaskList.DuplicateTaskException e) {
            e.printStackTrace();
        }
    }

    // private void undoUpdate(Task newTask, Task originalTask) {
        // Task stubTask = new Task (newTask.getTaskDetails(), newTask.getStartTime(), newTask.getEndTime(), newTask.getPriority(), newTask.getRecurringFrequency());
        // try {
            // model.updateTaskUndo(newTask, originalTask.getTaskDetails(), originalTask.getStartTime(), originalTask.getEndTime(), originalTask.getPriority(), originalTask.getRecurringFrequency());
            // model.updateTaskUndo(originalTask, stubTask.getTaskDetails(), stubTask.getStartTime(), stubTask.getEndTime(), stubTask.getPriority(), originalTask.getRecurringFrequency());
        // } catch (IllegalValueException e) {
			// e.printStackTrace();
		// }
    // }

    // private void undoDone(ReadOnlyTask task) {
        // try {
            // model.markTaskAsIncomplete(task);
        // } catch (TaskNotFoundException e) {
            // assert false: "The target task cannot be missing";
        // }
    // }

}