package seedu.address.testutil;

import seedu.address.model.person.Deadline;
import seedu.address.model.person.Name;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Start;
import seedu.address.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Start start;
    private Deadline deadline;
    private Priority priority;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.start = taskToCopy.getStart();
        this.deadline = taskToCopy.getDeadline();
        this.priority = taskToCopy.getPriority();
        this.tags = taskToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }
    

    public void setStart(Start start) {
        this.start = start;
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Start getStart() {
        return start;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    @Override
    public Priority getPriority() {
        return priority;
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
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
