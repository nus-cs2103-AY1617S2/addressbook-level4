//@@author A0114395E
package seedu.address.model;

import seedu.address.logic.commands.*;

/**
 * Class to store an action, and it's inverse
 */
public class StateCommandPair {
    private Command executeCommand;
    private Command undoCommand;
    
    public StateCommandPair(Command cmd) {
        this.executeCommand = cmd;
        this.undoCommand = this.evaluateInverse(cmd);
    }
    
    /**
     * Executes the command previously entered (for redo)
     */
    public void execute() {
        System.out.println(executeCommand);
    }
    
    /**
     * Executes the inverse of the command previously entered (for undo)
     */
    public void executeInvese() {
        System.out.println(undoCommand);
    }
    
    /**
     * 
     * @param Command
     * @return an Inverse action of the Command
     */
    private Command evaluateInverse(Command cmd) {
        return cmd;// stub
    }
}
