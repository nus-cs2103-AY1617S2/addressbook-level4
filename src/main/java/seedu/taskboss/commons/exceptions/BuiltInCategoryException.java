//@@author A0144904H
package seedu.taskboss.commons.exceptions;

/**
 * Signals an error caused by changing data default categories where there should be none.
 */
public class BuiltInCategoryException extends Exception {
    public BuiltInCategoryException(String message) {
        super(message);
    }
}
