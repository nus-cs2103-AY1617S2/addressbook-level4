package seedu.address.model.person;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the address book. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Start start;
    private Deadline deadline;
    private Priority priority;
    private UniqueTagList tags;
    private Notes notes;
    private Completion completion;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Start start, Deadline deadline, Priority priority, UniqueTagList tags, Notes notes,
            Completion completion) {
        assert !CollectionUtil.isAnyNull(name, start, deadline, tags, notes);
        this.name = name;
        this.start = start;
        this.deadline = deadline;
        this.priority = priority;
        this.tags = new UniqueTagList(tags); // protect internal tags from
                                             // changes in the arg list
        this.notes = notes;
        this.completion = completion;
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStart(), source.getDeadline(), source.getPriority(), source.getTags(),
                source.getNotes(), source.getCompletion());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setStart(Start start) {
        assert start != null;
        this.start = start;
    }

    @Override
    public Start getStart() {
        return start;
    }

    public void setDeadline(Deadline deadline) {
        assert deadline != null;
        this.deadline = deadline;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        assert notes != null;
        this.notes = notes;
    }

    @Override
    public Completion getCompletion() {
        return completion;
    }

    public void setCompletion(Completion completion) {
        assert completion != null;
        this.completion = completion;
    }

    /**
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setStart(replacement.getStart());
        this.setDeadline(replacement.getDeadline());
        this.setPriority(replacement.getPriority());
        this.setTags(replacement.getTags());
        this.setNotes(replacement.getNotes());
        this.setCompletion(replacement.getCompletion());
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
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
