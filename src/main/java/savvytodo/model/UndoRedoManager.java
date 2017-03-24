package savvytodo.model;

import java.util.EmptyStackException;
import java.util.Stack;

import savvytodo.model.undoredo.UndoCommand;

//@@A0124863A
/**
 * @author A0124863A
 * Class that stores the undo and redo commands
 */
public class UndoRedoManager {
    private Stack<UndoCommand> undoStack = new Stack<UndoCommand>();
    private Stack<UndoCommand> redoStack = new Stack<UndoCommand>();

    public void storeUndoCommand(UndoCommand undoCommand) {
        undoStack.push(undoCommand);
    }

    public void resetRedo() {
        redoStack.clear();
    }

    public UndoCommand getUndoCommand() throws EmptyStackException {
        UndoCommand undo = undoStack.pop();
        UndoCommand redo = undo.reverseUndo();
        redoStack.push(redo);
        return undo;
    }

    public UndoCommand getRedoCommand() throws EmptyStackException {
        UndoCommand redo = redoStack.pop();
        UndoCommand undo = redo.reverseUndo();
        undoStack.push(undo);
        return redo;
    }

}
