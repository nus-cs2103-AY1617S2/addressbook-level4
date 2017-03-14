package seedu.tasklist.model.task;

/**
 * A read-only immutable interface for a floating task in the task list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 *
 */
public interface ReadOnlyFloatingTask extends ReadOnlyTask {
    Priority getPriority();
    Status getStatus();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getComment().equals(this.getComment()))
                && other.getPriority().equals(this.getPriority())
                && other.getStatus().equals(this.getStatus());
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    String getAsText();
}
