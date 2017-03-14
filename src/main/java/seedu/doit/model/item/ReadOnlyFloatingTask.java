package seedu.doit.model.item;

/**
 * A read-only immutable interface for a FloatingTask in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyFloatingTask extends Item {

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyFloatingTask other) {
        return other == this // short circuit if same object
            || (other != null // this is first to avoid NPE below
            && other.getName().equals(this.getName()) // state checks here onwards
            && other.getPriority().equals(this.getPriority())
            && other.getDescription().equals(this.getDescription()));
    }

    /**
     * Formats the floatingTask as text, showing all details.
     */
    @Override
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append(" Priority: ")
            .append(getPriority())
            .append(" Description: ")
            .append(getDescription())
            .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
