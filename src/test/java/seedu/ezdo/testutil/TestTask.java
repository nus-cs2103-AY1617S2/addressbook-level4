package seedu.ezdo.testutil;

import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.Email;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.StartDate;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private static final String PREFIX_EMAIL = "e/";
    private static final String PREFIX_PRIORITY = "p/";
    private static final String PREFIX_STARTDATE = "s/";

    private Name name;
    private StartDate startDate;
    private Email email;
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
        this.priority = taskToCopy.getPriority();
        this.email = taskToCopy.getEmail();
        this.startDate = taskToCopy.getStartDate();
        this.tags = taskToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }

    public void setEmail(Email email) {
        this.email = email;
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
    public Priority getPriority() {
        return priority;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    @Override
    public StartDate getStartDate() {
        return startDate;
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
        sb.append(PREFIX_STARTDATE + this.getStartDate().value + " ");
        sb.append(PREFIX_PRIORITY + this.getPriority().value + " ");
        sb.append(PREFIX_EMAIL + this.getEmail().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
