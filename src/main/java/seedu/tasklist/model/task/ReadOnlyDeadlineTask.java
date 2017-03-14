package seedu.tasklist.model.task;

import java.util.Date;

/**
 * A read-only immutable interface for an event task (contains 2 dates) in the task list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 *
 */

public interface ReadOnlyDeadlineTask extends ReadOnlyTask {
    Date getDeadline();
    String getDeadlineString();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getComment().equals(this.getComment()));
    }
}
