package seedu.whatsleft.model.activity;

import java.util.Comparator;
import java.util.List;

import seedu.whatsleft.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for an Tasks in WhatsLeft.
 * Implementations should guarantee: Description is present, field values are validated.
 */
public interface ReadOnlyTask {

    Description getDescription();
    Priority getPriority();
    ByDate getByDate();
    ByTime getByTime();
    Location getLocation();
    boolean getStatus();
    boolean hasDeadline();
    String getDescriptionToShow();
    String getPriorityToShow();
    String getByTimeDateToShow();
    String getLocationToShow();
    List<String> getTagsToShow();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the event's internal tags.
     */
    UniqueTagList getTags();
    //@@author A0121668A
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && (other.getDescription().toString() == null ? this.getDescription().toString() == null :
                    other.getDescription().toString().equals(this.getDescription().
                            toString())) // state checks here onwards
                && (other.getPriority().toString() == null ? this.getPriority().toString() == null :
                    other.getPriority().toString().equals(this.getPriority().toString()))
                && (other.getByDate().toString() == null ? this.getByDate().toString() == null :
                    other.getByDate().toString().equals(this.getByDate().toString()))
                && (other.getByTime().toString() == null ? this.getByTime().toString() == null :
                    other.getByTime().toString().equals(this.getByTime().toString()))
                && (other.getLocation().toString() == null ? this.getLocation().toString() == null :
                    other.getLocation().toString().equals(this.getLocation().toString()))
                && (other.getTags().equals(this.getTags()))
                && (other.getStatus() == this.getStatus())
                );
    }
    //@@author
    /**
     * Formats the activity as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Priority: ")
                .append(getPriority().toString())
                .append(" ByDate: ")
                .append(getByDate().toString())
                .append(" ByTime: ")
                .append(getByTime().toString())
                .append(" Location: ")
                .append(getLocation().toString())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }


    //@@author A0148038A
    default int compareTo(ReadOnlyTask o) {
        if (!this.hasDeadline() && !o.hasDeadline()) {
            return this.getPriority().compareTo(o.getPriority());
        } else if (this.hasDeadline() && !o.hasDeadline()) {
            return -1;
        } else if (!this.hasDeadline() && o.hasDeadline()) {
            return 1;
        } else {
            if (!this.getByDate().equals(o.getByDate())) {
                return this.getByDate().compareTo(o.getByDate());
            } else if (!this.getByTime().equals(o.getByTime())) {
                return this.getByTime().compareTo(o.getByTime());
            } else {
                return this.getPriority().compareTo(o.getPriority());
            }
        }
    }

    static Comparator<? super ReadOnlyTask> getComparator() {
        Comparator<ReadOnlyTask> byTask = (t1, t2) -> t1.compareTo(t2);
        return byTask;
    }

}
