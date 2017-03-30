package seedu.taskit.model.task;

import seedu.taskit.model.tag.UniqueTagList;
import seedu.taskit.model.task.Date;

//@@author A0141011J
/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Title getTitle();
    Date getStart();
    Date getEnd();
    Priority getPriority();

    Boolean isDone();
    Boolean isOverdue();
    void setDone(Boolean status);

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle())); // state checks here onwards
    }

    // @@author A0163996J
    /**
     * Formats the task as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle() + " ")
            .append("Start: ")
            .append(getStart() + " ")
            .append("End: ")
            .append(getEnd() + " ")
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }


    //@@author A0097141H
    /**
     * Stringifies title and tags
     * @return lowercase String with title and tags for easy string matching
     */

    default String toStringTitleTagAndDateList() {
      String titleTagAndDateString = getTitle().title + " ";
      final StringBuilder builder = new StringBuilder();
      builder.append(getTitle()).append(" ");
      builder.append(getStart()).append(" ");
      builder.append(getEnd()).append(" ");
      getTags().iterator().forEachRemaining(builder::append);

      titleTagAndDateString = builder.toString();
      titleTagAndDateString = titleTagAndDateString.replace('[', ' ').replace(']', ' ');

      return titleTagAndDateString.toLowerCase();
    }


}

