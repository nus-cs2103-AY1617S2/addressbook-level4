package seedu.todolist.logic;

import java.util.Stack;

import seedu.todolist.logic.commands.Command;
import seedu.todolist.model.Model;
import seedu.todolist.model.ReadOnlyToDoList;
import seedu.todolist.model.ToDoList;

public class UndoManager {

    protected Model model;
    private Stack <CommandAndState> undoStack;
    private Stack <CommandAndState> redoStack;

    public UndoManager(Model model) {
        this.model = model;
        undoStack = new Stack <CommandAndState> ();
        redoStack = new Stack <CommandAndState> ();
    }

    /**
     * Add a new mutating task into the undoStack
     * @return
     */
    public void addMutatingTask(Command command) {
        ReadOnlyToDoList previousState = new ToDoList(model.getToDoList());
        undoStack.push(new CommandAndState(command, previousState, (ReadOnlyToDoList) null));
    }

    public void setCurrentStateForMutatingTask(ReadOnlyToDoList state) {
        undoStack.peek().setCurrentState(state);
    }

    /**
     * Get the stack that stores the undo commands and states
     * @return Stack<CommandAndState>
     */
    public Stack<CommandAndState> getUndoStack() {
        return undoStack;
    }

    /**
     * Get the stack that stores the redo commands and states
     * @return Stack<CommandAndState>
     */
    public Stack<CommandAndState> getRedoStack() {
        return redoStack;
    }

    /**
     * Get the command and state of the latest command to undo
     * @return CommandAndState
     */
    public CommandAndState getCommandAndStateToUndo() {
        return undoStack.peek();
    }

    /**
     * Transfer the latest undone command into the redo stack
     *
     */
    public void undoLatestTask() {
        redoStack.push(undoStack.pop());
    }

    /**
     * Get the command and state of the latest command to redo
     * @return CommandAndState
     */
    public CommandAndState getCommandAndStateToRedo() {
        return redoStack.peek();
    }

    /**
     * Transfer the latest redone command into the undo stack
     *
     */
    public void redoLatestUndoneTask() {
        undoStack.push(redoStack.pop());
    }



}


