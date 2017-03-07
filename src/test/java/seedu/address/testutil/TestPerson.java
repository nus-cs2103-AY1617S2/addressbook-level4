package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.todo.Address;
import seedu.address.model.todo.Email;
import seedu.address.model.todo.Name;
import seedu.address.model.todo.Phone;
import seedu.address.model.todo.ReadOnlyTodo;

/**
 * A mutable todo object. For testing only.
 */
public class TestTodo implements ReadOnlyTodo {

    private Name name;
    private Address address;
    private Email email;
    private Phone phone;
    private UniqueTagList tags;

    public TestTodo() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code todoToCopy}.
     */
    public TestTodo(TestTodo todoToCopy) {
        this.name = todoToCopy.getName();
        this.phone = todoToCopy.getPhone();
        this.email = todoToCopy.getEmail();
        this.address = todoToCopy.getAddress();
        this.tags = todoToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Phone getPhone() {
        return phone;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("a/" + this.getAddress().value + " ");
        sb.append("p/" + this.getPhone().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
