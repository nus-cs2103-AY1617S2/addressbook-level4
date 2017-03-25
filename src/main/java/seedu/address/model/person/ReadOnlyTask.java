package seedu.address.model.person;

import java.util.Comparator;

import seedu.address.model.tag.UniqueTagList;

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

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the event's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDescription().equals(this.getDescription()) // state checks here onwards
                && other.getPriority().equals(this.getPriority())
                && other.getByDate().equals(this.getByDate())
                && other.getByTime().equals(this.getByTime())
                && other.getTags().equals(other.getTags())
                );
    }

    /**
     * Formats the activity as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Priority: ")
                .append(getPriority())
                .append(" ByDate: ")
                .append(getByDate())
                .append(" ByTime: ")
                .append(getByTime())
                .append(" Location: ")
                .append(getLocation())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
    
    //@@author A0148038A
	static Comparator<? super ReadOnlyTask> getComparator() {
		// sort by by date first
		Comparator<ReadOnlyTask> byByDate = (t1, t2) -> t1.getByDate().compareTo(t2.getByDate());
						
		// then sort by by time
		Comparator<ReadOnlyTask> byByTime = (t1, t2) -> t1.getByTime().compareTo(t2.getByTime());
				
		// then sort by priority
		Comparator<ReadOnlyTask> byPriority = (t1, t2) -> t1.getPriority().compareTo(t2.getPriority());
						
		return byByDate.thenComparing(byByTime).thenComparing(byByTime).thenComparing(byPriority);
	}
}
