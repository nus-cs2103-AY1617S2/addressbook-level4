package seedu.doit.model.item;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask extends Item {

    StartTime getStartTime();
    EndTime getEndTime();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
            || (other != null // this is first to avoid NPE below
            && other.getName().equals(this.getName()) // state checks here onwards
            && other.getPriority().equals(this.getPriority())
            && other.getDescription().equals(this.getDescription()));

            /*
            && ((other.getStartTime() == null &&  this.getStartTime() == null)
            || (other.getStartTime().equals(this.getStartTime())))
            && ((other.getEndTime() == null &&  this.getEndTime() == null)
            || other.getEndTime().equals(this.getEndTime()))
            */
    }

    /**
     * Formats the event as text, showing all details.
     */
    @Override
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        String startTime = (getStartTime() != null) ? getStartTime().toString() : "None";
        String endTime = (getEndTime() != null) ? getEndTime().toString() : "None";

        builder.append(getName())
               .append(" Priority: ")
               .append(getPriority());
        if (hasStartTime()) {
            builder.append(" Start Time: ");
            builder.append(getStartTime());
        }
        if (hasEndTime()) {
            builder.append(" End Time: ");
            builder.append(getEndTime());
        }
        builder.append(" Description: ")
               .append(getDescription())
               .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    boolean hasStartTime();

    boolean hasEndTime();

    int compareTo(ReadOnlyTask other);

    boolean isTask();

    boolean isEvent();

    boolean isFloatingTask();
}
