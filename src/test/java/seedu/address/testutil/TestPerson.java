package seedu.address.testutil;

import seedu.address.model.task.Description;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.tag.UniqueTagList;

/**
 * A mutable task object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private Name name;
    private Description address;
    private Deadline email;
    private Priority phone;
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
        this.address = personToCopy.getDescription();
        this.tags = personToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setAddress(Description address) {
        this.address = address;
    }

    public void setEmail(Deadline email) {
        this.email = email;
    }

    public void setPhone(Priority phone) {
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
    public Priority getPhone() {
        return phone;
    }

    @Override
    public Deadline getEmail() {
        return email;
    }

    @Override
    public Description getDescription() {
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
        sb.append("a/" + this.getDescription().value + " ");
        sb.append("p/" + this.getPhone().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
