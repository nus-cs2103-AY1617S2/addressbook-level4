package org.teamstbf.yats.model.item;

import org.teamstbf.yats.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for an Event in the TaskManager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {

	Title getTitle();
	Description getDescription();
	Location getLocation();
	Schedule getStartTime();
	Schedule getEndTime();
	Date getDeadline();
	Periodic getPeriod();


	/**
	 * The returned TagList is a deep copy of the internal TagList,
	 * changes on the returned list will not affect the person's internal tags.
	 */
	UniqueTagList getTags();

	/**
	 * Returns true if both have the same state. (interfaces cannot override .equals)
	 */
	default boolean isSameStateAs(ReadOnlyItem other) {
		return other == this // short circuit if same object
				|| (other != null // this is first to avoid NPE below
				&& other.getTitle().equals(this.getTitle()) // state checks here onwards
				&& other.getDescription().equals(this.getDescription()));
	}

	/**
	 * Formats the person as text, showing all contact details.
	 */
	default String getAsText() {
		final StringBuilder builder = new StringBuilder();
		builder.append(getTitle())
		.append(" Title: ")
		.append(" Description: ")
		.append(getDescription())
			.append(" Tags: ");
		getTags().forEach(builder::append);
		return builder.toString();
	}


}
