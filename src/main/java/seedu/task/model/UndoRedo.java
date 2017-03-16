package seedu.task.model;

import java.util.Stack;

import seedu.task.model.task.Task;

public class UndoRedo {

	private Stack<String> command = new Stack<String>();
    private Stack<Task> undoStack = new Stack<Task>();
    private Stack<Task> redoStack = new Stack<Task>();

	public void pushUndo(Task task) {
	    undoStack.push(task);
    }

	public void pushCommand(String command) {
		this.command.push(command);
	}

	public Task popUndo () {
		Task task = undoStack.pop();
		redoStack.push(task);
		return task;
	}

	public String popCommand(){
		return command.pop();
	}

}
