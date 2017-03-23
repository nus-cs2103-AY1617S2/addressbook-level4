package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.model.ReadOnlyToDoList;
import seedu.address.model.ToDoList;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "To-do list has been cleared!";
    public static final String MESSAGE_RESTORED = "To-do list has been restored!";
    private ReadOnlyToDoList originalToDoList;
    private CommandResult commandResultToUndo;

    // @@author A0143648Y
    @Override
    public CommandResult execute() {
        assert model != null;
        originalToDoList = new ToDoList(model.getToDoList());
        model.resetData(new ToDoList());
        commandResultToUndo = new CommandResult(MESSAGE_SUCCESS);
        updateUndoLists();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void updateUndoLists() {
        if (previousToDoLists == null) {
            previousToDoLists = new ArrayList<ReadOnlyToDoList>(3);
            previousCommandResults = new ArrayList<CommandResult>(3);
        }
        if (previousToDoLists.size() >= 3) {
            previousToDoLists.remove(0);
            previousCommandResults.remove(0);
            previousToDoLists.add(originalToDoList);
            previousCommandResults.add(commandResultToUndo);
        } else {
            previousToDoLists.add(originalToDoList);
            previousCommandResults.add(commandResultToUndo);
        }
    }
}
