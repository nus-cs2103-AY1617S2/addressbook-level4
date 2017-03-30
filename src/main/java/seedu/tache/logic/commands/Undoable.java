package seedu.tache.logic.commands;

import seedu.tache.logic.commands.exceptions.CommandException;

//@@author A0150120H
/**
 * Represents a Command that can be undone
 * Commands that have been executed successfully and can be undone should be pushed onto the
 * undoHistory Stack in the Command class
 */
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
