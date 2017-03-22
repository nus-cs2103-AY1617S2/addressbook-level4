package seedu.task.model;

import java.util.Stack;

import seedu.task.model.task.Task;

public class UndoManager {

    private Stack<Task> undoStack;
    private Stack<Task> editedStack;
    private static CommandStack previousCommand;

    public UndoManager() {
        undoStack = new Stack<Task>();
        editedStack = new Stack<Task>();
        previousCommand = new CommandStack();
    }

    public void pushUndo(Task task) {
        undoStack.push(task);
    }

    public static void pushCommand(String command) {
        previousCommand.pushCommand(command);
    }

    public void pushEditedTask(Task task) {
        editedStack.push(task);
    }

    public Task popUndo () {
        return undoStack.pop();
    }

    public String popCommand() {
        return previousCommand.popCommand();
    }

    public Task popEdited() {
        return editedStack.pop();
    }

    public boolean getStackStatus() {
        return undoStack.empty();
    }

    public boolean getCommandHistoryStatus() {
        return previousCommand.getCommandHistoryStatus();
    }

}
