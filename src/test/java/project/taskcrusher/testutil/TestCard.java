package project.taskcrusher.testutil;

import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.shared.Priority;
import project.taskcrusher.model.tag.UniqueTagList;
import project.taskcrusher.model.task.Deadline;
import project.taskcrusher.model.task.ReadOnlyTask;

/**
 * A mutable task object. For testing only.
 */
public class TestCard implements ReadOnlyTask {

    private Name name;
    private Description description;
    private Deadline deadline;
    private Priority priority;
    private UniqueTagList tags;

    public TestCard() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestCard(TestCard taskToCopy) {
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
        sb.append("add t " + this.getName().name);
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
    public void markComplete() {
        // TODO Auto-generated method stub

    }

    @Override
    public void markIncomplete() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isComplete() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int compareTo(ReadOnlyTask o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isOverdue() {
        // TODO Auto-generated method stub
        return false;
    }
}
