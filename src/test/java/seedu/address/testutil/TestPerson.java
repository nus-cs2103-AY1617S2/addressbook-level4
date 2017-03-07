package seedu.address.testutil;

<<<<<<< HEAD
import seedu.address.model.person.Location;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Name;
import seedu.address.model.person.Date;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.tag.UniqueTagList;
=======
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Date;
import seedu.task.model.task.Location;
import seedu.task.model.task.Name;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Remark;
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

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
        this.phone = personToCopy.getDate();
        this.email = personToCopy.getRemark();
        this.address = personToCopy.getLocation();
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
    public Date getDate() {
        return phone;
    }

    @Override
    public Remark getRemark() {
        return email;
    }

    @Override
    public Location getLocation() {
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
        sb.append("a/" + this.getLocation().value + " ");
        sb.append("p/" + this.getDate().value + " ");
        sb.append("e/" + this.getRemark().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
