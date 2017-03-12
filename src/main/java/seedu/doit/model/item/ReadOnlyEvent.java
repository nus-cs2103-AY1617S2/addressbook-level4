package seedu.doit.model.item;

/**
 * A read-only immutable interface for an Event in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent extends Item {

    StartTime getStartTime();

    EndTime getEndTime();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
            || (other != null // this is first to avoid NPE below
            && other.getName().equals(this.getName()) // state checks here onwards
            && other.getPriority().equals(this.getPriority())
            && other.getStartTime().equals(this.getStartTime())
            && other.getEndTime().equals(this.getEndTime())
            && other.getDescription().equals(this.getDescription()));
    }

    /**
     * Formats the event as text, showing all details.
     */
    @Override
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append(" Priority: ")
            .append(getPriority())
            .append(" Start Time: ")
            .append(getStartTime())
            .append(" End Time: ")
            .append(getEndTime())
            .append(" Description: ")
            .append(getDescription())
            .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }


}
