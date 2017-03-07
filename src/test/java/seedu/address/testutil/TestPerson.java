package seedu.address.testutil;

import seedu.address.model.person.Location;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Name;
import seedu.address.model.person.Date;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyPerson {

    private Name name;
    private Location address;
    private Remark email;
    private Date phone;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestPerson(TestPerson personToCopy) {
        this.name = personToCopy.getName();
        this.phone = personToCopy.getPhone();
        this.email = personToCopy.getEmail();
        this.address = personToCopy.getAddress();
        this.tags = personToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setAddress(Location address) {
        this.address = address;
    }

    public void setEmail(Remark email) {
        this.email = email;
    }

    public void setPhone(Date phone) {
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
    public Date getPhone() {
        return phone;
    }

    @Override
    public Remark getEmail() {
        return email;
    }

    @Override
    public Location getAddress() {
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
