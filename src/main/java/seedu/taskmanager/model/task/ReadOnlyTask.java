package seedu.taskmanager.model.task;

import seedu.taskmanager.commons.util.CurrentDate;
import seedu.taskmanager.model.category.UniqueCategoryList;

/**
 * A read-only immutable interface for a Task in ProcrastiNomore.
 * Implementations should guarantee: details are present and not null, field
 * values are validated.
 */
public interface ReadOnlyTask {

    TaskName getTaskName();

    StartDate getStartDate();

    StartTime getStartTime();

    EndDate getEndDate();

    EndTime getEndTime();

    Boolean getIsMarkedAsComplete();

    /**
     * The returned CategoryList is a deep copy of the internal CategoryList,
     * changes on the returned list will not affect the task's internal
     * categories.
     */
    UniqueCategoryList getCategories();

    /**
     * Returns true if both have the same state. (interfaces cannot override
     * .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                        && other.getTaskName().equals(this.getTaskName()) // state
                        // checks
                        // here
                        // onwards
                        && other.getStartTime().equals(this.getStartTime())
                        && other.getEndTime().equals(this.getEndTime())
                        && other.getStartDate().equals(this.getStartDate())
                        && other.getEndDate().equals(this.getEndDate())
                        && other.getIsMarkedAsComplete().equals(this.getIsMarkedAsComplete()));
    }

    // @@author A0142418L
    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskName());

        if (!getStartDate().toString().equals("EMPTY_FIELD")) {
            builder.append(" Start Date: ").append(getStartDate());
        }

        if (!getStartTime().toString().equals("EMPTY_FIELD")) {
            builder.append(" Start Time: ").append(getStartTime());
        }

        if (!getEndDate().toString().equals("EMPTY_FIELD")) {
            builder.append(" End Date: ").append(getEndDate());
        }

        if (!getEndTime().toString().equals("EMPTY_FIELD")) {
            builder.append(" End Time: ").append(getEndTime());
        }

        if (!getCategories().isEmptyCategoryList()) {
            builder.append(" Categories: ");
            getCategories().forEach(builder::append);
        }

        return builder.toString();
    }

    /**
     * Checks the fields populated within the task
     * 
     * @return true if task is a Event Task
     */
    boolean isEventTask();

    /**
     * Checks the fields populated within the task
     * 
     * @return true if task is a Deadline Task
     */
    boolean isDeadlineTask();

    /**
     * Checks the fields populated within the task
     * 
     * @return true if task is a Floating Task
     */
    boolean isFloatingTask();

    boolean isWithinStartEndDuration(ReadOnlyTask t);

}
