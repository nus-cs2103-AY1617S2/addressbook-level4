package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.IdentificationNumber;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Deadline deadline;
    private Description description;
    private IdentificationNumber id;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.deadline = taskToCopy.getDeadline();
        this.description = taskToCopy.getDescription();
        this.id = taskToCopy.getID();
        this.tags = taskToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setID(IdentificationNumber id) {
        this.id = id;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public IdentificationNumber getID() {
        return id;
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
        sb.append("add " + this.getName().toString() + " ");
        sb.append("dl/" + this.getDeadline().toString() + " ");
        sb.append("ds/" + this.getDescription().toString() + " ");
        sb.append("id/" + this.getID().toString() + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
