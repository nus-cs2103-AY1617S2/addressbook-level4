package savvytodo.model.task;

/**
 * @author A0140016B
 *
 * Represents Task's Status of Completion in the task manager
 * Guarantees: immutable; Defaults to false
 */
public class Status {

    public final boolean value;

    public static final boolean COMPLETED = true;
    public static final boolean ONGOING = false;
    private static final String MESSAGE_STATUS_COMPLETED = "Completed";
    private static final String MESSAGE_STATUS_ONGOING = "Ongoing";

    public static final String MESSAGE_RECURR_CONSTRAINTS =
            "If recurrence type is NONE, occurences can only be 0";
    public static final String MESSAGE_RECURR_NOT_MATCH =
            "Task recurrence type should be 'none', 'daily', 'weekly', 'monthly' or 'yearly'";

    /**
     * Defaults to Not Completed
     */
    public Status() {
        value = ONGOING;
    }

    /**
     * Changing the status
     */
    public Status(boolean newStatus) {
        this.value = newStatus;
    }

    @Override
    public String toString() {
        if (value) {
            return MESSAGE_STATUS_COMPLETED;
        } else {
            return MESSAGE_STATUS_ONGOING;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Status
                        && this.toString().equals(((Status) other).toString()));
    }

}
