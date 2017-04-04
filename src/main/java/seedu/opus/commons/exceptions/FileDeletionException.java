package seedu.opus.commons.exceptions;

//@@author A0148081H
/**
 * Represents an error during deletion of a file
 */
public class FileDeletionException extends Exception {
    public FileDeletionException(String message) {
        super(message);
    }
}
