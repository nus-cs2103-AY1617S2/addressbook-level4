package savvytodo.model.undoredo.exceptions;

/**
 * 
 * @author A0124863A
 * Signals that redo cannot be performed
 * 
 */
public class RedoFailureException extends Exception {

    public RedoFailureException(String s) {
        super(s);
    }
}
