package seedu.tache.logic.commands;

import seedu.tache.logic.commands.exceptions.CommandException;

//@@author A0150120H
public interface Undoable {


    /**
     * Returns true if the command can be undone.
     */
    public abstract boolean isUndoable();

    /**
     * Attempts to undo the command.
     * If the undo is successful, returns the CommandResult feedback string of the original command
     * @throws CommandException if the undo operation fails
     */
    public abstract String undo() throws CommandException;
}
