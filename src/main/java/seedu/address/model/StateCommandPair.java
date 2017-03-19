//@@author A0114395E
package seedu.address.model;

import seedu.address.logic.commands.*;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Class to store an action, and it's inverse
 */
public class StateCommandPair {
    private Command executeCommand;
    private Command undoCommand;
    
    public StateCommandPair(Command cmd, Command inverseCmd) {
        this.executeCommand = cmd;
        this.undoCommand = inverseCmd;
    }
    
    /**
     * Executes the command previously entered (for redo)
     * @throws CommandException 
     */
    public void executeCommand() throws CommandException {
        this.executeCommand.execute();
    }
    
    /**
     * Executes the inverse of the command previously entered (for undo)
     * @throws CommandException 
     */
    public void executeInverseCommand() throws CommandException {
        this.undoCommand.execute();
    }
}
