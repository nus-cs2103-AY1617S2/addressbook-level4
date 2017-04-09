package seedu.toluist.commons.exceptions;

/**
 * Signals an error caused by storage save/load.
 */
public class DataStorageException extends Exception {
    public DataStorageException(String message) {
        super(message);
    }
}
