//@@author A0139961U
package seedu.tache.model.task;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.Task.RecurInterval;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Optional<DateTime> getStartDateTime();
    Optional<DateTime> getEndDateTime();
    boolean getActiveStatus();
    boolean getTimedStatus();
    boolean getRecurringStatus();
    RecurInterval getRecurInterval();
    boolean isWithinDate(Date date);
    List<Date> getRecurCompletedList();
    List<Task> getUncompletedRecurList();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    //@@author A0142255M
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        boolean start = true;
        boolean end = true;
        if (other.getStartDateTime().isPresent() && this.getStartDateTime().isPresent()) {
            start = other.getStartDateTime().equals(this.getStartDateTime());
        }
        if (other.getEndDateTime().isPresent() && this.getEndDateTime().isPresent()) {
            end = other.getEndDateTime().equals(this.getEndDateTime());
        }
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName())
                && start && end
                && (other.getActiveStatus() == this.getActiveStatus())
                && (other.getTimedStatus() == this.getTimedStatus())
                && (other.getRecurringStatus() == this.getRecurringStatus())
                && other.getRecurInterval().equals(this.getRecurInterval())); // state checks here onwards
    }
    //@@author

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\"" + getName() + "\"");
        if (getStartDateTime().isPresent()) {
            builder.append(" Start Date and Time: \"" + getStartDateTime().get().toString() + "\"");
        }
        if (getEndDateTime().isPresent()) {
            builder.append(" End Date and Time: \"" + getEndDateTime().get().toString() + "\"");
        }
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append("\n");
        return builder.toString();
    }

}
