package savvytodo.model.task;

import savvytodo.model.category.UniqueCategoryList;

/**
 * @author A0140016B (Contributer)
 * A read-only immutable interface for a Task in the taskManager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Priority getPriority();
    Description getDescription();
    Location getLocation();
    DateTime getDateTime();
    Recurrence getRecurrence();
    Status isCompleted();

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
                && other.getPriority().equals(this.getPriority())
                && other.isCompleted().equals(this.isCompleted())
                && other.getDescription().equals(this.getDescription())
                && other.getLocation().equals(this.getLocation()))
                && other.isCompleted().equals(this.isCompleted());
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date Time: ")
                .append(getDateTime())
                .append(" Recurrence: ")
                .append(getRecurrence())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Location: ")
                .append(getLocation())
                .append(" Description: ")
                .append(getDescription())
                .append(" Status: ")
                .append(isCompleted())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }

}
