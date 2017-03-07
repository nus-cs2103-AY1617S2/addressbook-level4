package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.Note;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private Name name;
    private Note address;
    private Status email;
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
        this.phone = personToCopy.getPriority();
        this.email = personToCopy.getStatus();
        this.address = personToCopy.getNote();
        this.tags = personToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setNote(Note address) {
        this.address = address;
    }

    public void setStatus(Status email) {
        this.email = email;
    }

    public void setPriority(Priority phone) {
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
    public Priority getPriority() {
        return phone;
    }

    @Override
    public Status getStatus() {
        return email;
    }

    @Override
    public Note getNote() {
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
        sb.append("n/" + this.getNote().value + " ");
        sb.append("p/" + this.getPriority().value + " ");
        sb.append("s/" + this.getStatus().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
