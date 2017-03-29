package typetask.model.task;


/**
 * A read-only immutable interface for a Task in TypeTask.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    //@@author A0139926R
    DueDate getDate();
    DueDate getEndDate();
    //@@author
    boolean getIsCompleted();
    //@@author A0139154E
    String getIsCompletedToString();

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
    //@@author A0139154E
    //edited for friendlier UI
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
               .append("\nDetails: \nStart On:")
               .append(getDate())
               .append("\nEnds On: ")
               .append(getEndDate())
               .append(" \nCompleted? ")
               .append(getIsCompletedToString());
        return builder.toString();
    }

}
