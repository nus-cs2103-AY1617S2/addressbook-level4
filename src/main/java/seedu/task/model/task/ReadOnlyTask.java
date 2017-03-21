package seedu.task.model.task;

import seedu.task.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Person in the taskmanager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

   // TaskName getName();
  //  Phone getPhone();
  //  Email getEmail();
  //  Address getAddress();
    TaskName getTaskName();
    TaskDate getTaskDate();
    TaskTime getTaskStartTime();
    TaskTime getTaskEndTime();
    String getTaskDescription();

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
                && other.getTaskName() != null
                && other.getTaskName().equals(this.getTaskName()) // state checks here onwards
                && other.getTaskDate() != null
                && other.getTaskDate().equals(this.getTaskDate())
                && other.getTaskStartTime() != null
                && other.getTaskStartTime().equals(this.getTaskStartTime())
                && other.getTaskEndTime() != null
                && other.getTaskEndTime().equals(this.getTaskEndTime()))
                && other.getTaskDescription() != null
                && other.getTaskDescription().equals(this.getTaskDescription());
    }

    /**
     * Formats the person as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Task Name: ")
        		.append(getTaskName())
                .append(" Date: ")
                .append(getTaskDate())
                .append(" Start Time: ")
                .append(getTaskStartTime())
                .append(" End Time: ")
                .append(getTaskEndTime())
                .append(" Description: " + getTaskDescription())
        		.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
