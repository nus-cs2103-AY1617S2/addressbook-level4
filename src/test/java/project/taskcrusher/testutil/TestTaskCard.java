package project.taskcrusher.testutil;

import java.util.Date;

import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.shared.Priority;
import project.taskcrusher.model.tag.UniqueTagList;
import project.taskcrusher.model.task.Deadline;
import project.taskcrusher.model.task.ReadOnlyTask;

//@@author A0127737X
/**
 * A mutable task object. For testing only.
 */
public class TestTaskCard implements ReadOnlyTask {

    private Name name;
    private Description description;
    private Deadline deadline;
    private Priority priority;
    private UniqueTagList tags;
    private boolean isComplete;

    public TestTaskCard() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTaskCard(TestTaskCard taskToCopy) {
        this.name = taskToCopy.getName();
        this.priority = taskToCopy.getPriority();
        this.deadline = taskToCopy.getDeadline();
        this.description = taskToCopy.getDescription();
        this.tags = taskToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDescription(Description description) {
        this.description = description;
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
    public Deadline getDeadline() {
        return deadline;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public Description getDescription() {
        return description;
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
        sb.append("add t " + this.getName().name + " ");

        if (this.getDeadline().hasDeadline()) {
            sb.append("d/" + this.getDeadline().deadline + " ");
        }

        if (this.getPriority().hasPriority()) {
            sb.append("p/" + this.getPriority().priority + " ");
        }

        if (this.getDescription().hasDescription()) {
            sb.append("//" + this.getDescription().description + " ");
        }

        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public boolean isComplete() {
        return this.isComplete;
    }

    @Override
    public int compareTo(ReadOnlyTask another) {
        if (this.isComplete) {
            if (another.isComplete()) {
                return 0;
            } else {
                return 1;
            }
        } else if (another.isComplete()) {
            return -1;
        }
        //neither is complete

        if (!this.getDeadline().hasDeadline() && !another.getDeadline().hasDeadline()) {
            return this.getPriority().compareTo(another.getPriority());
        } else if (!this.getDeadline().hasDeadline() && another.getDeadline().hasDeadline()) {
            return 1;
        } else if (this.getDeadline().hasDeadline() && !another.getDeadline().hasDeadline()) {
            return -1;
        } else {
            //both has deadline
            Date thisDate = this.getDeadline().getDate().get();
            Date anotherDate = another.getDeadline().getDate().get();
            return thisDate.compareTo(anotherDate);
        }
    }

    @Override
    public boolean isOverdue(Date timer) {
        return false;
    }
}
