package seedu.taskboss.model.task;

import seedu.taskboss.model.category.UniqueCategoryList;

/**
 * A read-only immutable interface for a Task in TaskBoss.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    PriorityLevel getPriorityLevel();
    Information getInformation();
    DateTime getStartDateTime();
    DateTime getEndDateTime();
    Recurrence getRecurrence();
    boolean isRecurring();

    /**
     * The returned CategoryList is a deep copy of the internal CategoryList,
     * changes on the returned list will not affect the task's internal categories.
     */
    UniqueCategoryList getCategories();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPriorityLevel().equals(this.getPriorityLevel())
                && other.getStartDateTime().equals(this.getStartDateTime())
                && other.getEndDateTime().equals(this.getEndDateTime())
                && other.getInformation().equals(this.getInformation()))
                && other.getRecurrence().equals(this.getRecurrence())
                && other.getCategories().equals(this.getCategories());
    }

    //@@author A0147990R
    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        checkEmptyValue(builder, " Priority Level: ", getPriorityLevel().value);
        checkEmptyValue(builder, " Start Date: ", getStartDateTime().toString());
        checkEmptyValue(builder, " End Date: ", getEndDateTime().toString());
        checkEmptyValue(builder, " Information: ", getInformation().toString());
        checkEmptyValue(builder, " Recurrence: ", getRecurrence().toString());

        builder.append(" Categories: ");
        getCategories().forEach(builder::append);
        builder.append("\n");
        return builder.toString();
    }

    /**
     * Append the field and value to the builder if the value is not empty;
     */
    default void checkEmptyValue(StringBuilder builder, String field, String value) {
        String EMPTY_STRING = "";
        String PRIORITY_FIELD = " Priority Level: ";
        String PRIORITY_NO_VALUE = "No priority";
        String RECURRENCE_FIELD = " Recurrence: ";
        String RECURRENCE_NONE = "NONE";

        //don't append 'no priority' value
        if (field.equals(PRIORITY_FIELD) && value.equals(PRIORITY_NO_VALUE)) {
            return;
        }

        //don't append 'none' recurrence value
        if (field.equals(RECURRENCE_FIELD) && value.equals(RECURRENCE_NONE)) {
            return;
        }

        if (!value.equals(EMPTY_STRING)) {
            builder.append(field)
                .append(value);
        }
    }
}
