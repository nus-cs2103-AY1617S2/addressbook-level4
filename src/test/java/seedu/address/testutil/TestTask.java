package seedu.address.testutil;

import seedu.address.model.task.Instruction;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Title;
import seedu.address.model.task.Date;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.tag.UniqueTagList;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private Instruction instruction;
    private Priority priority;
    private Date date;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.title = taskToCopy.getTitle();
        this.date = taskToCopy.getDate();
        this.priority = taskToCopy.getPriority();
        this.instruction = taskToCopy.getInstruction();
        this.tags = taskToCopy.getTags();
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public Instruction getInstruction() {
        return instruction;
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
        sb.append("add " + this.getTitle().title + " ");
        sb.append("i/" + this.getInstruction().value + " ");
        sb.append("d/" + this.getDate().value + " ");
        sb.append("p/" + this.getPriority().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
