package seedu.address.testutil;

//import seedu.address.model.person.Address;
import seedu.address.model.person.Date;
import seedu.address.model.person.Email;
import seedu.address.model.person.Group;
import seedu.address.model.person.Name;
//import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyPerson {

    private Name name;
    //private Address address;
    private Group group;
    private Email email;
    //private Phone phone;
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
        //this.phone = personToCopy.getPhone();
        this.date = personToCopy.getDate();
        this.email = personToCopy.getEmail();
        //this.address = personToCopy.getAddress();
        this.group = personToCopy.getGroup();
        this.tags = personToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

//    public void setAddress(Address address) {
//        this.address = address;
//    }

    public void setGroup(Group group) {
        this.group = group;
    }
    public void setEmail(Email email) {
        this.email = email;
    }

//    public void setPhone(Phone phone) {
//        this.phone = phone;
//    }
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

//    @Override
//    public Phone getPhone() {
//        return phone;
//    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Email getEmail() {
        return email;
    }

//    @Override
//    public Address getAddress() {
//        return address;
//    }

    @Override
    public Group getGroup() {
        return group;
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
        //sb.append("a/" + this.getAddress().value + " ");
        sb.append("g/" + this.getGroup().value + " ");
        //sb.append("p/" + this.getPhone().value + " ");
        sb.append("d/" + this.getDate().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
