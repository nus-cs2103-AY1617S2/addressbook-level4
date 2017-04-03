// @@author A0163996J

package seedu.taskit.testutil;

import seedu.taskit.model.tag.UniqueTagList;
import seedu.taskit.model.task.Date;
import seedu.taskit.model.task.Priority;
import seedu.taskit.model.task.ReadOnlyTask;
import seedu.taskit.model.task.Title;

public class TestTask implements ReadOnlyTask {
    private Title title;
    private Date start;
    private Date end;
    private Priority priority;

    private boolean isDone;
    private boolean isOverdue;

    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.title = taskToCopy.getTitle();
        this.tags = taskToCopy.getTags();
        this.start = taskToCopy.getStart();
        this.end = taskToCopy.getEnd();
        this.priority = taskToCopy.getPriority();
        this.isDone = false;
        this.isOverdue = false;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public Date getStart() {
        if (start == null) {
            return new Date();
        }
        return start;
    }

    @Override
    public Date getEnd() {
        if (end == null) {
            return new Date();
        }
        return end;
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
        sb.append("add " + this.getTitle().title + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("tag " + s.tagName + " "));
        return sb.toString();
    }

    // @@author
    
    //@@author A0141872E
    @Override
    public Boolean isDone() {
        return isDone;
    }

    @Override
    public Boolean isOverdue() {
        return isOverdue;
    }

    @Override
    public void setDone(String status) {
        if(status.equals("done")) {
            this.isDone = true;
        } else {
            this.isDone = false;
        }
    }//@@author
}
