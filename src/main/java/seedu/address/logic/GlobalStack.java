package seedu.address.logic;

import java.util.NoSuchElementException;
import java.util.Stack;

import seedu.address.model.task.Task;



public class GlobalStack {

    private static GlobalStack instance = null;
    private Stack<Task> undoStack;
    private Stack<Task> redoStack;
    public static final String MESSAGE_NOTHING_TO_UNDO = "No commands left to undo";
    public static final String MESSAGE_NOTHING_TO_REDO = "No commands left to redo";

    private GlobalStack() {
        undoStack = new Stack<Task>();
        redoStack = new Stack<Task>();
    }

    public static GlobalStack getInstance() {
        if (instance == null) {
            instance = new GlobalStack();
        }
        return instance;
    }

    public Task undoEdit() {
        Task temp = undoStack.pop(); // contains originalTask
        Task temp2 = undoStack.pop(); // contains editedTask
        redoStack.push(temp);
        redoStack.push(temp2);
        return temp;
    }

    public Task redoEdit() {
        Task temp = redoStack.pop();
        Task temp2 = redoStack.pop();
        undoStack.push(temp);
        undoStack.push(temp2);
        return temp;
    }
    public Task undoAdd() throws NoSuchElementException {
        try {
            Task temp = undoStack.pop();
            redoStack.push(temp);
            return temp;
        } catch (NoSuchElementException ne) {
            throw new NoSuchElementException(MESSAGE_NOTHING_TO_UNDO);
        }
    }

    public Task redoAdd() throws NoSuchElementException {
        try {
            Task temp = redoStack.pop();
            undoStack.push(temp);
            return temp;
        } catch (NoSuchElementException ne) {
            throw new NoSuchElementException(MESSAGE_NOTHING_TO_REDO);
        }
    }

    public Stack<Task> getUndoStack() {
        return undoStack;
    }

    public Stack<Task> getRedoStack() {
        return redoStack;
    }
}
