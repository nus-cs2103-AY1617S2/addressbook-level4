//@@author A0144885R
package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the addressbook.
 */
public interface ReadOnlyTask {

    Name getName();
    Deadline getDeadline();
    Description getDescription();
    UniqueTagList getTags();
    Status getStatus();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDeadline().equals(this.getDeadline())
                && other.getDescription().equals(this.getDescription())
                && other.getTags().equals(this.getTags())
                && other.getStatus().equals(this.getStatus()));
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Name: ")
                .append(getName())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Description: ")
                .append(getDescription())
                .append(" Status: ")
                .append(getStatus());
        return builder.toString();
    }

}
