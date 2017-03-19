package seedu.address.testutil;

import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Title;
import seedu.address.model.tag.UniqueTagList;

public class TestTask implements ReadOnlyTask {
    private Title title;
    private String start;
    private String end;

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


    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    public void setStart(String start) {
        this.start = start;
    }


    public void setEnd(String end) {
        this.end = end;
    }
    
    @Override
    public Title getTitle() {
        return title;
    }


    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String getStart() {
        return start;    
    }
    
    @Override
    public String getEnd() {
        return end;
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
