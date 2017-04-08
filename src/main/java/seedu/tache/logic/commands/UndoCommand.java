package seedu.tache.logic.commands;

import seedu.tache.logic.commands.exceptions.CommandException;

//@@author A0150120H
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String SHORT_COMMAND_WORD = "u";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the last change to the address book.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undone: %s";
    public static final String MESSAGE_EMPTY_HISTORY = "Nothing to undo!";
    public static final String MESSAGE_FAILURE = "Failed to undo: %s";

    @Override
    public CommandResult execute() {
        // TODO Auto-generated method stub
        if (undoHistory.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_HISTORY);
        } else {
            Undoable lastCommand = undoHistory.pop();
            assert lastCommand.isUndoable() : "The popped command can't be undone!";
            try {
                String undoResult = lastCommand.undo();
                return new CommandResult(String.format(MESSAGE_SUCCESS, undoResult));
            } catch (CommandException e) {
                return new CommandResult(String.format(MESSAGE_FAILURE, e.getMessage()));
            }
        }
    }

}
