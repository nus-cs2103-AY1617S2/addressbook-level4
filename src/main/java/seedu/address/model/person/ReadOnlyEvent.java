package seedu.address.model.person;

import java.util.Comparator;

import seedu.address.model.tag.UniqueTagList;

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
                && other.getDescription().equals(this.getDescription()) // state checks here onwards
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime())
                && other.getStartDate().equals(this.getStartDate())
                && other.getEndDate().equals(this.getEndDate())
                && other.getLocation().equals(this.getLocation()));
    }

    /**
     * Formats the event as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Start Time: ")
                .append(getStartTime())
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" End Time: ")
                .append(getEndTime())
                .append(" End Date: ")
                .append(getEndDate())
                .append(" Location: ")
                .append(getLocation())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
    
    //@@author A0148038A
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

}
