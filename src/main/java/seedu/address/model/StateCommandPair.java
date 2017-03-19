package seedu.address.model;

import seedu.address.logic.commands.*;

/**
 * Class to store an action, and it's inverse
 */
public class StateCommandPair {
    private String executeCommand;
    private String undoCommand;
    
    public void execute() {
        System.out.println(executeCommand);
    }
    
    public void executeInvese() {
        System.out.println(undoCommand);
    }
}
