package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Instruction;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Title;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private Instruction instruction;
    private Priority priority;
    private Deadline date;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.title = taskToCopy.getTitle();
        this.date = taskToCopy.getDeadline();
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

    public void setDate(Deadline date) {
        this.date = date;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public boolean isCompleted() {
        return priority.isCompleted();
    }

    @Override
    public boolean isFloating() {
        return date.isFloating();
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public Deadline getDeadline() {
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
        sb.append("d/" + this.getDeadline().toString() + " ");
        sb.append("p/" + this.getPriority().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public void setAsCompleted() {
        if (!isCompleted()) {
            int currentPriorityValue = Integer.parseInt(priority.value);
            int newPriorityValue = currentPriorityValue * (-1);
            try {
                this.priority = new Priority(Integer.toString(newPriorityValue));
            } catch (IllegalValueException e) {
            }
        }
    }

    @Override
    public void setAsIncompleted() {
        if (isCompleted()) {
            int currentPriorityValue = Integer.parseInt(priority.value);
            int newPriorityValue = currentPriorityValue * (-1);
            try {
                this.priority = new Priority(Integer.toString(newPriorityValue));
            } catch (IllegalValueException e) {
            }
        }
    }
}
