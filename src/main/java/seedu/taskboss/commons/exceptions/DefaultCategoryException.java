package seedu.taskboss.commons.exceptions;

/**
 * Signals an error caused by changing data default categories where there should be none.
 */
public class DefaultCategoryException extends Exception {
    public DefaultCategoryException(String message) {
        super(message);
    }
}
