package seedu.todolist.logic;

import java.util.Stack;

import seedu.todolist.logic.commands.Command;
import seedu.todolist.model.Model;
import seedu.todolist.model.ReadOnlyToDoList;
import seedu.todolist.model.ToDoList;

public class UndoManager {

    protected Model model;
    private Stack <CommandAndState> stateStack;
    private Stack <CommandAndState> undoStack;

    public UndoManager(Model model) {
        this.model = model;
        stateStack = new Stack <CommandAndState> ();
        undoStack = new Stack <CommandAndState> ();
    }

    public void addMutatingTask(Command command) {
        ReadOnlyToDoList previousState = new ToDoList(model.getToDoList());
        stateStack.push(new CommandAndState(command, previousState));
    }

    public Stack<CommandAndState> getStack() {
        return stateStack;
    }

    public CommandAndState getCommandAndStateToUndo() {
        return stateStack.peek();
    }

    public void undoLatestTask() {
        undoStack.push(stateStack.pop());
    }


}


