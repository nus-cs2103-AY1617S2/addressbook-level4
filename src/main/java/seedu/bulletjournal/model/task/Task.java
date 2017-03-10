package seedu.bullletjournal.model.task;

import java.util.Objects;

import seedu.bullletjournal.commons.util.CollectionUtil;
import seedu.bullletjournal.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Description description;
    private Deadline deadline;
    private Status status;
    private Detail detail;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Description description, Deadline deadline, Status status, Detail detail, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, deadline, status, detail, tags);
        this.description = description;
        this.deadline = deadline;
        this.status = status;
        this.detail = detail;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getTags());
    }

    public void setName(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public Description getName() {
        return description;
    }

    public void setPhone(Deadline deadline) {
        assert deadline != null;
        this.deadline = deadline;
    }

    @Override
    public Deadline getPhone() {
        return deadline;
    }

    public void setEmail(Status status) {
        assert status != null;
        this.status = status;
    }

    @Override
    public Status getEmail() {
        return status;
    }

    public void setAddress(Detail detail) {
        assert detail != null;
        this.detail = detail;
    }

    @Override
    public Detail getAddress() {
        return detail;
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

    /**
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setPhone(replacement.getPhone());
        this.setEmail(replacement.getEmail());
        this.setAddress(replacement.getAddress());
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
        return Objects.hash(description, deadline, status, detail, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
