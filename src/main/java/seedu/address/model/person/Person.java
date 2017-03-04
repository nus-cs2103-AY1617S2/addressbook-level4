package seedu.address.model.person;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.label.UniqueLabelList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;

    private UniqueLabelList labels;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, UniqueLabelList labels) {
        assert !CollectionUtil.isAnyNull(name, phone, email, address, labels);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.labels = new UniqueLabelList(labels); // protect internal labels from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getLabels());
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
    public UniqueLabelList getLabels() {
        return new UniqueLabelList(labels);
    }

    /**
     * Replaces this person's labels with the labels in the argument label list.
     */
    public void setLabels(UniqueLabelList replacement) {
        labels.setLabels(replacement);
    }

    /**
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyPerson replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setPhone(replacement.getPhone());
        this.setEmail(replacement.getEmail());
        this.setAddress(replacement.getAddress());
        this.setLabels(replacement.getLabels());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, labels);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
