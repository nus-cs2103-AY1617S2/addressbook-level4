package werkbook.task.model.task;

import java.text.SimpleDateFormat;
import java.util.Objects;

import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.commons.util.CollectionUtil;
import werkbook.task.model.tag.Tag;
import werkbook.task.model.tag.UniqueTagList;

/**
 * Represents a Task in the task list. Guarantees: name is present and not null,
 * field values are validated.
 */
public class Task implements ReadOnlyTask {

    public static final String MESSAGE_START_WITHOUT_END_CONSTRAINTS = "End Date/Time must be specified " +
            "if Start Date/Time is specified";
    public static final String MESSAGE_END_BEFORE_START_CONSTRAINTS = "End Date/Time must occur after " +
            "Start Date/Time";
    public static final SimpleDateFormat START_DATETIME_FORMATTER = new SimpleDateFormat("dd/MM/yyyy HHmm");

    private Name name;
    private Description description;
    private StartDateTime startDateTime;
    private EndDateTime endDateTime;

    private UniqueTagList tags;

    /**
     * Name must be present and not null.
     * @throws IllegalValueException when EndDateTime is not present or occurs after StartDateTime when
     * StartDateTime is present
     */
    public Task(Name name, Description description, StartDateTime startDateTime, EndDateTime endDateTime,
            UniqueTagList tags) throws IllegalValueException {
        assert !CollectionUtil.isAnyNull(name);
        if (startDateTime.isPresent()) {
            if (!endDateTime.isPresent()) {
                throw new IllegalValueException(MESSAGE_START_WITHOUT_END_CONSTRAINTS);
            } else if (endDateTime.value.get().before(startDateTime.value.get())) {
                throw new IllegalValueException(MESSAGE_END_BEFORE_START_CONSTRAINTS);
            }
        }
        this.name = name;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from
                                             // changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     * @throws IllegalValueException
     */
    public Task(ReadOnlyTask source) throws IllegalValueException {
        this(source.getName(), source.getDescription(), source.getStartDateTime(), source.getEndDateTime(),
                source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @Override
    public StartDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(StartDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    @Override
    public EndDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(EndDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public UniqueTagList getTags() {
        return tags;
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
        this.setDescription(replacement.getDescription());
        this.setStartDateTime(replacement.getStartDateTime());
        this.setEndDateTime(replacement.getEndDateTime());
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
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, description, startDateTime, endDateTime, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
