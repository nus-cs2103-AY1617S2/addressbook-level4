package seedu.whatsleft.model.activity;

import java.util.Comparator;
import java.util.List;

import seedu.whatsleft.model.tag.UniqueTagList;

//@@author A0148038A
/**
 * A read-only immutable interface for an Event in WhatsLeft.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {

    Description getDescription();
    StartTime getStartTime();
    EndTime getEndTime();
    StartDate getStartDate();
    EndDate getEndDate();
    Location getLocation();
    String getDescriptionToShow();
    String getDurationToShow();
    String getLocationToShow();
    List<String> getTagsToShow();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the event's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && (other.getDescription().toString() == null ? this.getDescription().toString() == null :
                    other.getDescription().toString().equals(this.getDescription().
                            toString())) // state checks here onwards
                && (other.getStartTime().toString() == null ? this.getStartTime().toString() == null :
                    other.getStartTime().toString().equals(this.getStartTime().toString()))
                && (other.getEndTime().toString() == null ? this.getEndTime().toString() == null :
                    other.getEndTime().toString().equals(this.getEndTime().toString()))
                && (other.getStartDate().toString() == null ? this.getStartDate().toString() == null :
                    other.getStartDate().toString().equals(this.getStartDate().toString()))
                && (other.getEndDate().toString() == null ? this.getEndDate().toString() == null :
                    other.getEndDate().toString().equals(this.getEndDate().toString()))
                && (other.getLocation().toString() == null ? this.getLocation().toString() == null :
                    other.getLocation().toString().equals(this.getLocation().toString())));
    }

    /**
     * Formats the event as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Start Time: ")
                .append(getStartTime().toString())
                .append(" Start Date: ")
                .append(getStartDate().toString())
                .append(" End Time: ")
                .append(getEndTime().toString())
                .append(" End Date: ")
                .append(getEndDate().toString())
                .append(" Location: ")
                .append(getLocation())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    //@@author A0148038A
    /**
     * compare events by start date, start time, end date, end time
     * @return a comparator of ReadOnlyEvent
     */
    static Comparator<? super ReadOnlyEvent> getComparator() {
        // sort by start date first
        Comparator<ReadOnlyEvent> byStartDate = (e1, e2) -> e1.getStartDate().compareTo(e2.getStartDate());

        // then sort by start time
        Comparator<ReadOnlyEvent> byStartTime = (e1, e2) -> e1.getStartTime().compareTo(e2.getStartTime());

        // then sort by end date
        Comparator<ReadOnlyEvent> byEndDate = (e1, e2) -> e1.getEndDate().compareTo(e2.getEndDate());

        // then sort by end time
        Comparator<ReadOnlyEvent> byEndTime = (e1, e2) -> e1.getEndTime().compareTo(e2.getEndTime());

        return byStartDate.thenComparing(byStartTime).thenComparing(byEndDate).thenComparing(byEndTime);
    }

    //@@author A0121668A
    /**
     * Check with current date/time to see if the event is over.
     */
    boolean isOver();
    //@@author

}
