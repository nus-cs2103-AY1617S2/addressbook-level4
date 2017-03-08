package savvytodo.model.task;

import java.util.Objects;

import savvytodo.commons.util.CollectionUtil;
import savvytodo.model.category.UniqueCategoryList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;

    private UniqueCategoryList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Phone phone, Email email, Address address, UniqueCategoryList tags) {
        assert !CollectionUtil.isAnyNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags = new UniqueCategoryList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getCategories());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
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
    public UniqueCategoryList getCategories() {
        return new UniqueCategoryList(tags);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setCategories(UniqueCategoryList replacement) {
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
        this.setCategories(replacement.getCategories());
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
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
