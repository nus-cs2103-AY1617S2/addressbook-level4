package seedu.address.model.task;

import java.util.Optional;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.tag.UniqueTagList;

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
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        // TODO check other fields as well
        // since this is error prone might want to consider Google's equals checker
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName())); // state checks here onwards
    }

    /**
     * Formats the task as text, showing all fields.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());

        // TODO improve SLAP and consider lambda
        if (getDeadline().isPresent()) {
            builder.append(" Deadline: " + getDeadline().get().getValue().format(ParserUtil.DATE_TIME_FORMAT));
        } else {
            builder.append(" Deadline: none ");
        }

        // TODO improve SLAP and consider lambda
        if (getStartEndDateTime().isPresent()) {
            final StartEndDateTime startEndDateTime = getStartEndDateTime().get();
            builder.append(" Start Date: " + startEndDateTime.getStartDateTime().format(ParserUtil.DATE_TIME_FORMAT));
            builder.append(" End Date: " + startEndDateTime.getEndDateTime().format(ParserUtil.DATE_TIME_FORMAT));
        } else {
            builder.append(" Start Date: ");
            builder.append(" End Date: ");
        }

        // TODO although only two lines, but SLAP can still be improved
        builder.append(" Tags: ");
        getTags().forEach(builder::append);

        return builder.toString();
    }

}
