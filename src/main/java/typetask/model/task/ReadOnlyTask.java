package typetask.model.task;


/**
 * A read-only immutable interface for a Task in TypeTask.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    DueDate getDate();
    DueDate getEndDate();
    boolean getIsCompleted();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName())); // state checks here onwards
    }

    /**
     * Formats the task as text, showing all the task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
               .append(" DueDate: ")
               .append(getDate())
               .append(" Completed: ")
               .append(getIsCompleted());
        return builder.toString();
    }

}
