package seedu.bulletjournal.testutil;

import seedu.bullletjournal.model.tag.UniqueTagList;
import seedu.bullletjournal.model.task.Deadline;
import seedu.bullletjournal.model.task.Description;
import seedu.bullletjournal.model.task.Detail;
import seedu.bullletjournal.model.task.ReadOnlyTask;
import seedu.bullletjournal.model.task.Status;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description description;
    private Detail detail;
    private Status status;
    private Deadline deadline;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestTask(TestTask personToCopy) {
        this.description = personToCopy.getName();
        this.deadline = personToCopy.getPhone();
        this.status = personToCopy.getEmail();
        this.detail = personToCopy.getAddress();
        this.tags = personToCopy.getTags();
    }

    public void setName(Description description) {
        this.description = description;
    }

    public void setAddress(Detail detail) {
        this.detail = detail;
    }

    public void setEmail(Status status) {
        this.status = status;
    }

    public void setPhone(Deadline deadline) {
        this.deadline = deadline;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Description getName() {
        return description;
    }

    @Override
    public Deadline getPhone() {
        return deadline;
    }

    @Override
    public Status getEmail() {
        return status;
    }

    @Override
    public Detail getAddress() {
        return detail;
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
