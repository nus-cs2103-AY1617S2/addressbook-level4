//@@author A0131125Y
package seedu.toluist.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command. Please type `help` for available commands.";
    public static final String MESSAGE_INVALID_TASK_INDEX = "A valid task index was not provided";
    public static final String MESSAGE_SAVING_FAILURE = "The data could not be saved";
    public static final String MESSAGE_STORAGE_SAME_LOCATION = "Current storage path is already set to %s.";
    public static final String MESSAGE_NO_STORAGE_PATH = "No storage path was provided.";
    public static final String MESSAGE_SET_STORAGE_FAILURE = "The storage path %s is invalid.";
    public static final String MESSAGE_SET_STORAGE_SUCCESS = "Data storage path was changed to %s.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format. "
            + "Please type `help %s` for assistance.";
}
