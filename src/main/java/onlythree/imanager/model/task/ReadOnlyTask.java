package onlythree.imanager.model.task;

import java.util.Objects;
import java.util.Optional;

import onlythree.imanager.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    /**
     * Gets the name of the task.
     */
    // TODO implementations must ensure this gets a deep copy or Name have no setter so it is not mutable.
    Name getName();

    /**
     * Gets the {@link Optional} deadline of the task.
     */
    // TODO implementations must ensure this gets a deep copy or Deadline have no setter so it is not mutable.
    Optional<Deadline> getDeadline();

    /**
     * Gets the {@link Optional} start and end date time of the task.
     */
    // TODO implementations must ensure this gets a deep copy or StartEndDateTime have no setter so it is not mutable.
    Optional<StartEndDateTime> getStartEndDateTime();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Gets the status of the task.
     */
    boolean isDone();

    //@@author A0140023E
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final ReadOnlyTask other = (ReadOnlyTask) o;

        return Objects.equals(getName(), other.getName())
                && Objects.equals(getDeadline(), other.getDeadline())
                && Objects.equals(getStartEndDateTime(), other.getStartEndDateTime());
        // TODO tags should actually be checked because that's how equals should usually function
        // However, the equals here is used to check for duplicates and is not really consistent
        // with how equals behave. Thus to further investigate.
        // Furthermore, this current implementation does not allow Tasks with same name to go through
        // if there are no deadlines or start and end date time. Probably would be better to remove
        // the duplicate task exception in this case.
    }

    /**
     * Formats the task as text, showing all fields.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        buildNameString(builder);
        buildDeadlineString(builder);
        buildStartEndDateTimeString(builder);
        buildTagsString(builder);
        buildDoneString(builder);

        return builder.toString();
    }

    default void buildNameString(final StringBuilder builder) {
        builder.append(getName());
        // TODO to remove all the line separators and rebuild toString(), create a getCLIOutput instead
        builder.append(System.lineSeparator());
    }

    default void buildDeadlineString(final StringBuilder builder) {
        // TODO don't include milliseconds in toString output
        builder.append("Deadline: ");
        builder.append(getDeadline().isPresent() ? getDeadline().get().toString() : "none");
        builder.append(System.lineSeparator());
    }

    default void buildStartEndDateTimeString(final StringBuilder builder) {
        // TODO don't include milliseconds in toString output
        if (getStartEndDateTime().isPresent()) {
            builder.append(getStartEndDateTime().get().toString());
        } else {
            builder.append("Start Date: none,");
            builder.append(" End Date: none ");
        }
        builder.append(System.lineSeparator());
    }

    default void buildTagsString(final StringBuilder builder) {
        builder.append("Tags: ");
        getTags().forEach(builder::append);
        builder.append(System.lineSeparator());
    }

    //@@author A0135998H
    default void buildDoneString(final StringBuilder builder) {
        if (isDone()) {
            builder.append("Status: Completed");
        } else {
            builder.append("Status: Not Completed");
        }
    }

}
