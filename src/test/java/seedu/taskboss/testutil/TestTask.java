package seedu.taskboss.testutil;

import seedu.taskboss.model.category.UniqueTagList;
import seedu.taskboss.model.task.Email;
import seedu.taskboss.model.task.Information;
import seedu.taskboss.model.task.Name;
import seedu.taskboss.model.task.PriorityLevel;
import seedu.taskboss.model.task.ReadOnlyTask;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Information information;
    private Email email;
    private PriorityLevel priorityLevel;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.priorityLevel = taskToCopy.getPriorityLevel();
        this.email = taskToCopy.getEmail();
        this.information = taskToCopy.getInformation();
        this.tags = taskToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    @Override
    public Information getInformation() {
        return information;
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
        sb.append("i/" + this.getInformation().value + " ");
        sb.append("p/" + this.getPriorityLevel().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
