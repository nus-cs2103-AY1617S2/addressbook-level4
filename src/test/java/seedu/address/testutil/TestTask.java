package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Content;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskDateTime;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Content content;
    private TaskDateTime dateTime;
    private UniqueTagList tags;

    public TestTask() throws IllegalValueException {
        dateTime = new TaskDateTime("");
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.content = taskToCopy.getContent();
        this.dateTime = taskToCopy.getDateTime();
        this.tags = taskToCopy.getTags();
    }

    public void setContent(Content name) {
        this.content = name;
    }

    public void setTaskDateTime(TaskDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Content getContent() {
        return content;
    }

    @Override
    public TaskDateTime getDateTime() {
        return dateTime;
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
        sb.append("add " + this.getContent().fullContent + " ");
        if (this.getDateTime().isThereDateTime()) {
            sb.append("by/" + this.getDateTime().value + " ");
        }
        this.getTags().asObservableList().stream().forEach(s -> sb.append("#" + s.tagName + " "));
        return sb.toString();
    }
}

