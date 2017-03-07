package seedu.geekeep.model.task;

import java.util.Objects;

import seedu.geekeep.commons.util.CollectionUtil;
import seedu.geekeep.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book. Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Title title;
    private Phone phone;
    private Email email;
    private Address address;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Title title, Phone phone, Email email, Address address, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(title, phone, email, address, tags);
        this.title = title;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTitle(), source.getPhone(), source.getEmail(), source.getAddress(), source.getTags());
    }

    public void setTitle(Title title) {
        assert title != null;
        this.title = title;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    public void setPhone(Phone phone) {
        assert phone != null;
        this.phone = phone;
    }

    @Override
    public Phone getPhone() {
        return phone;
    }

    public void setEmail(Email email) {
        assert email != null;
        this.email = email;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    public void setAddress(Address address) {
        assert address != null;
        this.address = address;
    }

    @Override
    public Address getAddress() {
        return address;
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

        this.setTitle(replacement.getTitle());
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
        return Objects.hash(title, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
