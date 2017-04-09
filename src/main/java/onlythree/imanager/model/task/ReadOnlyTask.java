package onlythree.imanager.model.task;

import java.util.Objects;
import java.util.Optional;

import onlythree.imanager.model.tag.UniqueTagList;

/**
 * A read-only immutable class for a Task in the task list.
 * Child classes should guarantee: details are present and not null, field values are validated.
 */
public abstract class ReadOnlyTask {

    /**
     * Gets the name of the task.
     */
    // TODO implementations must ensure this gets a deep copy or Name have no setter so it is not mutable.
    public abstract Name getName();

    /**
     * Gets the {@link Optional} deadline of the task.
     */
    // TODO implementations must ensure this gets a deep copy or Deadline have no setter so it is not mutable.
    public abstract Optional<Deadline> getDeadline();

    /**
     * Gets the {@link Optional} start and end date time of the task.
     */
    // TODO implementations must ensure this gets a deep copy or StartEndDateTime have no setter so it is not mutable.
    public abstract Optional<StartEndDateTime> getStartEndDateTime();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    public abstract UniqueTagList getTags();

    /**
     * Returns whether the task is completed.
     */
    public abstract boolean isComplete();

    //@@author A0140023E
    @Override
    public boolean equals(Object o) {
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
                && Objects.equals(getStartEndDateTime(), other.getStartEndDateTime())
                && Objects.equals(getTags(), other.getTags())
                && isComplete() == other.isComplete();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDeadline(), getStartEndDateTime(), getTags(), isComplete());
    }

    /**
     * Formats the task as text, showing all fields.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        buildNameString(builder);
        buildDeadlineString(builder);
        buildStartEndDateTimeString(builder);
        buildTagsString(builder);
        buildCompleteString(builder);

        return builder.toString();
    }

    private void buildNameString(final StringBuilder builder) {
        builder.append(getName());
        // TODO to remove all the line separators and rebuild toString(), create a getCLIOutput instead
        builder.append(System.lineSeparator());
    }

    private void buildDeadlineString(final StringBuilder builder) {
        if (getDeadline().isPresent()) {
            builder.append(getDeadline().get().toString());
            builder.append(System.lineSeparator());
        }
    }

    private void buildStartEndDateTimeString(final StringBuilder builder) {
        if (getStartEndDateTime().isPresent()) {
            builder.append(getStartEndDateTime().get().toString());
            builder.append(System.lineSeparator());
        }
    }

    private void buildTagsString(final StringBuilder builder) {
        builder.append("Tags: ");
        getTags().forEach(builder::append);
    }

    //@@author A0135998H
    private void buildCompleteString(final StringBuilder builder) {
        if (isComplete()) {
            builder.append(System.lineSeparator());
            builder.append("Status: Completed");
        }
    }
}
