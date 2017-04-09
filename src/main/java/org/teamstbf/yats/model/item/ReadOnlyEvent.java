package org.teamstbf.yats.model.item;

import org.teamstbf.yats.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for an Event in the TaskManager.
 * Implementations should guarantee: details are present and not null, field
 * values are validated.
 */
public interface ReadOnlyEvent {

	public String MESSAGE_NEWLINE = "\n";
	public String MESSAGE_HYPEN = " - ";

	/**
	 * Formats the person as text, showing all contact details.
	 */
	default String getAsText() {
		final StringBuilder builder = new StringBuilder();
		builder.append(getTitle()).append(MESSAGE_NEWLINE).append("Location: ").append(getLocation())
				.append(MESSAGE_NEWLINE).append("Description: ").append(getDescription()).append(MESSAGE_NEWLINE)
				.append("Time: ").append(getStartTime()).append(MESSAGE_HYPEN).append(getEndTime())
				.append(MESSAGE_NEWLINE).append("Completed: ").append(getIsDone().getValue()) // String.valueOf(isTaskDone())
				.append(MESSAGE_NEWLINE).append("Tags: ");
		getTags().forEach(builder::append);
		return builder.toString();
	}

	Title getTitle();

	Description getDescription();

	Location getLocation();

	Schedule getStartTime();

	Schedule getEndTime();

	Schedule getDeadline();

	Recurrence getRecurrence();

	boolean hasDeadline();

	boolean isRecurring();

	IsDone getIsDone();

	public void markDone();

	/**
	 * The returned TagList is a deep copy of the internal TagList, changes on
	 * the returned list will not affect the person's internal tags.
	 */
	UniqueTagList getTags();

	/**
	 * Returns true if both have the same state. (interfaces cannot override
	 * .equals)
	 */
	default boolean isSameStateAs(ReadOnlyEvent other) {
		return other == this // short circuit if same object
				|| (other != null // this is first to avoid NPE below
						&& other.getTitle().equals(this.getTitle()) // state
						// checks
						// here
						// onwards
						&& other.getDescription().equals(this.getDescription()));
	}

	boolean hasStartOrEndTime();

	boolean hasStartAndEndTime();

	/**
	 * Get number of hours required to perform event
	 */

}
