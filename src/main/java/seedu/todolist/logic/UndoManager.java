package seedu.todolist.logic;

import java.util.Stack;

import seedu.todolist.logic.commands.Command;
import seedu.todolist.model.Model;

public class UndoManager {

    protected Model model;
    Stack <CommandAndState> StateStack;
    Stack <CommandAndState> UndoStack;

    public UndoManager() {
		StateStack = new Stack <CommandAndState> ();
		UndoStack = new Stack <CommandAndState> ();
	}

    public void addMutatingTask(Command command) {
		StateStack.push(new CommandAndState(command, model.getToDoList()));
	}

    public CommandAndState getCommandAndStateToUndo() {
    	return StateStack.peek();
    }

    public void undoLatestTask() {
    	UndoStack.push(StateStack.pop());
    }


}


