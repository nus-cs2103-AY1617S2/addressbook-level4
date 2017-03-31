package savvytodo.model.undoredo.exceptions;

//@@A0124863A
/**
 * @author A0124863A
 * Signals that undo cannot be performed
 */
public class UndoFailureException extends Exception {

    public UndoFailureException(String s) {
        super(s);
    }
}
