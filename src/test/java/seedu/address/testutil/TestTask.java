package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private UniqueTagList tags;
    private boolean done;
    private String id;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.tags = taskToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public boolean isDone() {
        return done;
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
        if (!this.getTags().toSet().isEmpty()) {
            sb.append("tag ");
            this.getTags().asObservableList().stream()
                    .forEach(s -> sb.append(s.tagName + " "));
        }
        return sb.toString();
    }

    @Override
    public TaskType getTaskType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTaskDateTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setToday() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isToday() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public DateTime getDeadline() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTaskAbsoluteDateTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DateTime getStartingTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public int compareTo(ReadOnlyTask task2) {
        // TODO Auto-generated method stub
        return 0;
    }
}
