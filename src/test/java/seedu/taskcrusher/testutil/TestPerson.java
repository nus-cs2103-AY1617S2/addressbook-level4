package seedu.taskcrusher.testutil;

import seedu.taskcrusher.model.shared.Description;
import seedu.taskcrusher.model.shared.Name;
import seedu.taskcrusher.model.tag.UniqueTagList;
import seedu.taskcrusher.model.task.Email;
import seedu.taskcrusher.model.task.Priority;
import seedu.taskcrusher.model.task.ReadOnlyTask;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private Name name;
    private Description address;
    private Email email;
    private Priority phone;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestPerson(TestPerson personToCopy) {
        this.name = personToCopy.getTaskName();
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

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setPhone(Priority phone) {
        this.phone = phone;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getTaskName() {
        return name;
    }

    @Override
    public Priority getPhone() {
        return phone;
    }

    @Override
    public Email getEmail() {
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
        sb.append("add " + this.getTaskName().taskName + " ");
        sb.append("a/" + this.getDescription().value + " ");
        sb.append("p/" + this.getPhone().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
