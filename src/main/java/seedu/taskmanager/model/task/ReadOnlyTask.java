package seedu.taskmanager.model.task;

import java.util.Optional;

import seedu.taskmanager.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the taskmanager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Title getTitle();
    Optional<StartDate> getStartDate();
    Optional<EndDate> getEndDate();
    Optional<Description> getDescription();
    Optional<Repeat> getRepeat();

    Status getStatus();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    // @@author A0140032E
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     * This ignores the Status from consideration of being in the same state.
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle()) // state checks here onwards
                && other.getStartDate().equals(this.getStartDate())
                && other.getEndDate().equals(this.getEndDate())
                && other.getDescription().equals(this.getDescription()))
                && other.getRepeat().equals(this.getRepeat());
    }
    // @@author

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        // @@author A0140032E
        builder.append(getTitle())
                .append(getStartDate().isPresent() ? " Start Date: " + getStartDate().get() : "")
                .append(getEndDate().isPresent() ? " End Date: " + getEndDate().get() : "")
                .append(getDescription().isPresent() ? " Description: " + getDescription().get() : "")
                .append(getRepeat().isPresent() ? " Repeats: " + getRepeat().get() : "")
                .append(" Status: " + getStatus())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        // @@author
        return builder.toString();
    }

}
