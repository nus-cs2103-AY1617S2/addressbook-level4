package seedu.ezdo.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid.";
    public static final String MESSAGE_TASK_DONE = "The task has a status marked as done.";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";
  //@@author A0141010L
    public static final String MESSAGE_WRONG_LIST = "Please return to the task list "
                                                  + "if you want to mark a task as done.";
  //@@author A0139248X
    public static final String MESSAGE_TASK_DATES_INVALID = "Start date is after due date!";
    // @@author A0139177W
    public static final String MESSAGE_RECUR_FAILURE = "Both the start and due dates cannot be empty "
            + "with a recur status present.";

}
