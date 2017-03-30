package seedu.address.testutil;


import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Date;
import seedu.address.model.task.Email;
import seedu.address.model.task.Group;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.ReadOnlyPerson;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyPerson {

    private Name name;
    private Group group;
    private Email email;
    private Date date;
    private StartDate sdate;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestPerson(TestPerson personToCopy) {
        this.name = personToCopy.getName();
        this.sdate = personToCopy.getStartDate();
        this.date = personToCopy.getDate();
        this.email = personToCopy.getEmail();
        this.group = personToCopy.getGroup();
        this.tags = personToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStartDate(StartDate sdate) {
        this.sdate = sdate;
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
    public StartDate getStartDate() {
        return sdate;
    }

    @Override
    public Email getEmail() {
        return email;
    }

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
        sb.append("g/" + this.getGroup().value + " ");
        sb.append("s/" + this.getStartDate().value + " ");
        sb.append("d/" + this.getDate().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
