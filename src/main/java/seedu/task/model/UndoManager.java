package seedu.task.model;

import java.util.Stack;

import seedu.task.model.task.Task;

public class UndoManager {

    private Stack<Task> undoStack;
    private Stack<Integer> taskIndex;
    private static CommandStack previousCommand;

    public UndoManager() {
        undoStack = new Stack<Task>();
        taskIndex = new Stack<Integer>();
    	previousCommand = new CommandStack();
    }

    public void pushUndo(Task task) {
        undoStack.push(task);
    }

	public void pushTaskIndex(int index){
		taskIndex.push(index);
	}

	public static void pushCommand(String command) {
		previousCommand.pushCommand(command);
	}

	public Task popUndo () {
		return undoStack.pop();
	}

	public int popTaskIndex(){
		return taskIndex.pop();
	}

	public String popCommand() {
		return previousCommand.popCommand();
	}

	public boolean getStackStatus(){
		return undoStack.empty();
	}

	public boolean getCommandHistoryStatus(){
		return previousCommand.getCommandHistoryStatus();
	}

}
