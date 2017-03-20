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
    Boolean isCompleted();

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
                && other.getDeadline().equals(this.getDeadline())
                && other.getStartTime().equals(this.getStartTime())
                && other.getLabels().equals(this.getLabels()))
                && other.isCompleted().equals(this.isCompleted());
    }

    /**
     * Formats the task as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle());
        if (getStartTime().isPresent()) {
            builder.append(" Start: ")
                .append(getStartTime().get().toString());
        }
        if (getDeadline().isPresent()) {
            builder.append(" Deadline: ")
                .append(getDeadline().get().toString());
        }
        if (isCompleted()) {
            builder.append(" Status: Completed");
        } else {
            builder.append(" Status: Incomplete");
        }
        builder.append(" Label: ");
        getLabels().forEach(builder::append);
        return builder.toString();
    }

    default int compareTo(ReadOnlyTask other) {
        return compareCompletionStatus(other);
    }

    default int compareCompletionStatus(ReadOnlyTask other) {
        if (this.isCompleted() && !other.isCompleted()) {
            return 1;
        } else if (!this.isCompleted() && other.isCompleted()) {
            return -1;
        } else {
            return compareDates(other);
        }
    }

    default int compareDates(ReadOnlyTask other) {
        Deadline dateToCompareForOther;
        Deadline dateToCompareForThis;
        if (other.getStartTime().isPresent()) {
            dateToCompareForOther = other.getStartTime().get();
        } else if (other.getDeadline().isPresent()) {
            dateToCompareForOther = other.getDeadline().get();
        } else {
            dateToCompareForOther = null;
        }
        if (this.getStartTime().isPresent()) {
            dateToCompareForThis = this.getStartTime().get();
        } else if (this.getDeadline().isPresent()) {
            dateToCompareForThis = this.getDeadline().get();
        } else {
            dateToCompareForThis = null;
        }
        if (dateToCompareForThis != null && dateToCompareForOther != null) {
            return dateToCompareForThis.getDateTime().compareTo(dateToCompareForOther.getDateTime());
        } else if (dateToCompareForThis == null && dateToCompareForOther == null) {
            return 0;
        } else if (dateToCompareForThis == null) {
            return 1;
        } else {
            return -1;
        }
    }

}
