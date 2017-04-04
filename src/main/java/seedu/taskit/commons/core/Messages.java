package seedu.taskit.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_INVALID_START_DATE = "Start Date must occur before End Date";
    public static final String MESSAGE_INVALID_DATE = "Date not valid. Try formats such as: 'next friday at 2 pm' or '2/12/17'";
    public static final String MESSAGE_INVALID_PRIORITY = "Error saving priority, "
            + "valid inputs: 'high', 'medium', 'low'";

}
