// @@author A0163996J

package seedu.taskit.testutil;

import seedu.taskit.model.tag.UniqueTagList;
import seedu.taskit.model.task.Date;
import seedu.taskit.model.task.Priority;
import seedu.taskit.model.task.ReadOnlyTask;
import seedu.taskit.model.task.Task;
import seedu.taskit.model.task.Title;

public class TestTask implements ReadOnlyTask, Comparable<TestTask> {
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
    public int compareTo(TestTask o) {
        int priorityComparison = this.priority.compareTo(o.priority);
        if (priorityComparison == 0) {
            int startComparison = this.getStart().compareTo(o.getStart());
            if (startComparison == 0) {
                return this.getEnd().compareTo(o.getEnd());
            }
            return startComparison;
        }
        return priorityComparison;
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

    //@@author A0141872E
    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void setDone(String status) {
        if(status.equals("done")) {
            this.isDone = true;
        } else {
            this.isDone = false;
        }
    }

    @Override
    public boolean isOverdue() {
        return checkOverdue();
    }

    private boolean checkOverdue() {
        return this.end.isEndTimePassCurrentTime()== true && isDone == false;
    }

    @Override
    public boolean isFloating() {
        return this.end.date == null;
    }

    @Override
    public boolean isEvent() {
        return this.start.date != null && this.end.date != null;
    }

    @Override
    public boolean isDeadline() {
        return this.start.date == null && this.end.date != null;
    }//@@author
}
