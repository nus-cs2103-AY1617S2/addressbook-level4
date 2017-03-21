package seedu.task.commons.exceptions;

public class NoChangeException extends Exception {
    /**
     * @param message should contain relevant information when there is no changeß
     */
    public NoChangeException(String message) {
        super(message);
    }
}
