package seedu.taskit.testutil;


import seedu.taskit.model.tag.UniqueTagList;
import seedu.taskit.model.task.Date;
import seedu.taskit.model.task.Priority;
import seedu.taskit.model.task.ReadOnlyTask;
import seedu.taskit.model.task.Task;
import seedu.taskit.model.task.Title;

public class TestTask implements ReadOnlyTask {
    private Title title;
    private Date start;
    private Date end;
    private Priority priority;
    
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

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }
    
    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public Date getStart() {
        return start;
    }
    
    @Override
    public Date getEnd() {
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
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
