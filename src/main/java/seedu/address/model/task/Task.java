package seedu.address.model.task;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the task list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;

    private UniqueTagList tags;

    private Optional<StartEndDateTime> startEndDateTime;

    private Optional<Deadline> deadline;

    /**
     * Every field must not be null except for the {@code Optional} fields.
     */
    public Task(Name name, Optional<Deadline> deadline, Optional<StartEndDateTime> startEndDateTime,
            UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, deadline, startEndDateTime, tags);
        this.name = name;
        this.deadline = deadline;
        this.startEndDateTime = startEndDateTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDeadline(), source.getStartEndDateTime(), source.getTags());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Optional<Deadline> getDeadline() {
        return deadline;
    }

    /**
     * Only allow changing the {@link StartEndDateTime} if there is actually a value (not {@link Optional}).
     */
    public void setDeadline(Deadline dateTime) {
        assert dateTime != null;
        this.deadline = Optional.of(dateTime);
    }

    @Override
    public Optional<StartEndDateTime> getStartEndDateTime() {
        return startEndDateTime;
    }

    /**
     * Only allow changing the {@link StartEndDateTime} if there is actually a value (not {@link Optional}).
     */
    public void setStartEndDateTime(StartEndDateTime dateTime) {
        assert dateTime != null;
        this.startEndDateTime = Optional.of(dateTime);
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        // TODO to use or not to use a new Optional to wrap the Deadline
        // replacement Deadline is currently reused
        if (replacement.getDeadline().isPresent()) {
            this.deadline = Optional.of(replacement.getDeadline().get());
        } else {
            this.deadline = Optional.empty();
        }
        // TODO to use or not to use a new Optional to wrap the startEndDateTime
        // replacement startEndDateTime is currently reused
        if (replacement.getStartEndDateTime().isPresent()) {
            this.startEndDateTime = Optional.of(replacement.getStartEndDateTime().get());
        } else {
            this.deadline = Optional.empty();
        }
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
