package seedu.address.logic;

import java.util.Stack;

import seedu.todolist.logic.commands.Command;

public class UndoManager {
	
	private Stack<Command> CommandStack;
	private Stack<Command> UndoStack;
	
	public UndoManager() {
		CommandStack = new Stack<Command> ();
		UndoStack = new Stack<Command> ();
	}
	
	public void addNewMutatingCommand(Command item) {
		CommandStack.push(item);
		UndoStack.push(makeUndoableCommand(item));
	}
	
	public Command makeUndoableCommand(Command item) {
		return item;
	}
	
	public Command getFromCommandStack() {
		return CommandStack.pop();
	}
	
	public Command getFromUndoStack() {
		return UndoStack.pop();
	}

}
