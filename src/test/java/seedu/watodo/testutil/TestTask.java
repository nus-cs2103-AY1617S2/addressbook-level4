package seedu.watodo.testutil;

import seedu.watodo.model.tag.UniqueTagList;
import seedu.watodo.model.task.Address;
import seedu.watodo.model.task.Description;
import seedu.watodo.model.task.Email;
import seedu.watodo.model.task.Phone;
import seedu.watodo.model.task.ReadOnlyFloatingTask;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyFloatingTask {

    private Description name;
    private Address address;
    private Email email;
    private Phone phone;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestTask(TestTask personToCopy) {
        this.name = personToCopy.getDescription();
        this.tags = personToCopy.getTags();
    }

    public void setName(Description name) {
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
    public Description getDescription() {
        return name;
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
        sb.append("add " + this.getDescription().fullDescription + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
