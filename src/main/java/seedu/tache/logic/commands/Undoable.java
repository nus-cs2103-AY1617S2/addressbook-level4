package seedu.tache.logic.commands;

import seedu.tache.logic.commands.exceptions.CommandException;

public interface Undoable {

    //@@author A0150120H
    /**
     * Returns true if the command can be undone.
     */
    public abstract boolean isUndoable();

    //@@author A0150120H
    /**
     * Attempts to undo the command.
     * If the undo is successful, returns the CommandResult feedback string of the original command
     * @throws CommandException if the undo operation fails
     */
    public abstract String undo() throws CommandException;
}
