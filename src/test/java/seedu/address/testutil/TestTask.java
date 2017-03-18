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
        this.getTags().asObservableList().stream()
                .forEach(s -> sb.append("t/" + s.tagName + " "));
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
    public int getID() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setID(int id) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getTaskAbsoluteDateTime() {
        // TODO Auto-generated method stub
        return null;
    }
}
