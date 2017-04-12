package seedu.todolist.model.todo;

import java.util.Date;

import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Todo in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTodo {

    Name getName();
    Date getStartTime();
    Date getEndTime();
    Date getCompleteTime();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the todo's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     * didn't check for startTime, endTime, or completeTime
     */
    default boolean isSameStateAs(ReadOnlyTodo other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName())); // state checks here onwards
                //&& other.getStartTime().equals(this.getStartTime())
                //&& other.getEndTime().equals(this.getEndTime())
                //&& other.getCompleteTime().equals(this.getCompleteTime()));
    }

    //@@author A0163720M
    /**
     * Formats the todo as text, showing all todo details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();

        builder.append(getName());

        if (getStartTime() != null) {
            builder.append("\n[Start]: ")
                .append(getStartTime());
        }

        if (getEndTime() != null) {
            builder.append("\n[End]: ")
                .append(getEndTime());
        }

        if (getCompleteTime() != null) {
            builder.append("\n[Complete]: ")
                .append(getCompleteTime());
        }

        if (!getTagsAsString().isEmpty()) {
            builder.append("\n[Tags]: ");
            builder.append(getTagsAsString());
        }

        return builder.toString();
    }
    //@@author

    //@@author A0163720M
    /**
     *  Formats the todo's tags as a string
     */
    default String getTagsAsString() {
        final StringBuilder builder = new StringBuilder();

        for (Tag tag:getTags()) {
            builder.append(tag.tagName + " ");
        }

        return builder.toString();
    }
    //@@author
}
