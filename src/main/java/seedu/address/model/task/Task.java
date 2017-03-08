package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Priority phone;
    private Deadline email;
    private Description description;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Priority phone, Deadline email, Description description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, phone, email, description, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.description = description;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPriority(), source.getDeadline(), source.getDescription(), source.getTags());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setPhone(Priority phone) {
        assert phone != null;
        this.phone = phone;
    }

    @Override
    public Priority getPriority() {
        return phone;
    }

    public void setEmail(Deadline email) {
        assert email != null;
        this.email = email;
    }

    @Override
    public Deadline getDeadline() {
        return email;
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public Description getDescription() {
        return description;
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
        this.setPhone(replacement.getPriority());
        this.setEmail(replacement.getDeadline());
        this.setDescription(replacement.getDescription());
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
        return Objects.hash(name, phone, email, description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
