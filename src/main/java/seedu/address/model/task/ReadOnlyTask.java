package seedu.address.model.task;

import java.util.Optional;

import seedu.address.model.label.UniqueLabelList;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Title getTitle();
    Optional<Deadline> getDeadline();
    Optional<Deadline> getStartTime();

    /**
     * The returned LabelList is a deep copy of the internal LabelList,
     * changes on the returned list will not affect the task's internal labels.
     */
    UniqueLabelList getLabels();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle()) // state checks here onwards
                && other.getDeadline().equals(this.getDeadline()));
                //TODO
                //&& other.getStartTime().equals(this.getStartTime()));
    }

    /**
     * Formats the task as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
            .append(" Start: ")
            .append(getStartTime().toString())
            .append(" Deadline: ")
            .append(getDeadline().toString())
            .append(" Label: ");
        getLabels().forEach(builder::append);
        return builder.toString();
    }

}
