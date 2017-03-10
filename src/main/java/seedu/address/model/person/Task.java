package seedu.address.model.person;

import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Priority priority;
    private Email email;
    private Description description;

    private UniqueTagList tags;

    /**
     * left for the sake of leaving.
     */
    public Task(Name name, Priority phone, Email email, Description description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, phone, email, description, tags);
        this.name = name;
        this.priority = phone;
        this.email = email;
        this.description = description;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Modified for Task. 
     * @throws IllegalValueException 
     */
    public Task(Name name, Description description, UniqueTagList tags) throws IllegalValueException {
        assert !CollectionUtil.isAnyNull(name, description, tags);
        this.name = name;
        this.priority = null;
        this.email = null;
        this.description = description;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTaskName(), source.getPhone(), source.getEmail(), source.getDescription(), source.getTags());
    }

    public void setTaskName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getTaskName() {
        return name;
    }

    public void setPhone(Priority phone) {
        assert phone != null;
        this.priority = phone;
    }

    @Override
    public Priority getPhone() {
        return priority;
    }

    public void setEmail(Email email) {
        assert email != null;
        this.email = email;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    public void setDescription(Description address) {
        assert address != null;
        this.description = address;
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
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setTaskName(replacement.getTaskName());
        this.setPhone(replacement.getPhone());
        this.setEmail(replacement.getEmail());
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
        return Objects.hash(name, priority, email, description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
