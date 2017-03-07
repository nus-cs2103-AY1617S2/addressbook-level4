package seedu.address.testutil;

import seedu.address.model.task.Address;
import seedu.address.model.task.Email;
import seedu.address.model.task.Name;
import seedu.address.model.task.Date;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private Name name;
    private Address address;
    private Email email;
    private Date date;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestPerson(TestPerson personToCopy) {
        this.name = personToCopy.getName();
        this.date = personToCopy.getDate();
        this.email = personToCopy.getEmail();
        this.address = personToCopy.getAddress();
        this.tags = personToCopy.getTags();
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

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Date getDate() {
        return date;
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
        sb.append("p/" + this.getDate().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
