package seedu.tasklist.testutil;

import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.model.task.ReadOnlyTask;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Comment comment;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.comment = taskToCopy.getComment();
        this.tags = taskToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }


    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }


    @Override
    public Comment getComment() {
        return comment;
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
        sb.append("c/" + this.getComment().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
