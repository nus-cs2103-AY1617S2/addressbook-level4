package seedu.address.testutil;

import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.EndDateTime;
import seedu.task.model.task.StartDateTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.Description;
import seedu.task.model.task.ReadOnlyTask;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private Name name;
    private EndDateTime address;
    private StartDateTime email;
    private Description phone;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestPerson(TestPerson personToCopy) {
        this.name = personToCopy.getName();
        this.phone = personToCopy.getDescription();
        this.email = personToCopy.getStartDateTime();
        this.address = personToCopy.getEndDateTime();
        this.tags = personToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setAddress(EndDateTime address) {
        this.address = address;
    }

    public void setEmail(StartDateTime email) {
        this.email = email;
    }

    public void setPhone(Description phone) {
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
    public Description getDescription() {
        return phone;
    }

    @Override
    public StartDateTime getStartDateTime() {
        return email;
    }

    @Override
    public EndDateTime getEndDateTime() {
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
        sb.append("add " + this.getName().taskName + " ");
        sb.append("d/" + this.getDescription().toString() + " ");
        sb.append("s/" + this.getStartDateTime().toString() + " ");
        sb.append("e/" + this.getEndDateTime().toString() + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
