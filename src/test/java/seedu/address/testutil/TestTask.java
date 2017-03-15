package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Content;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskDateTime;
import seedu.address.model.task.Title;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private Content content;
    private TaskDateTime dateTime;
    private UniqueTagList tags;

    public TestTask() throws IllegalValueException {
        content = new Content("");
        dateTime = new TaskDateTime("");
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.title = taskToCopy.getTitle();
        this.content = taskToCopy.getContent();
        this.dateTime = taskToCopy.getDateTime();
        this.tags = taskToCopy.getTags();
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public void setTaskDateTime(TaskDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Title getTitle() {
        return title;
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
        sb.append("add " + this.getTitle().fullTitle + " ");
        if (this.getContent().isThereContent()) {
            sb.append(PREFIX_CONTENT.toString() + this.getContent().fullContent + " ");
        }
        if (this.getDateTime().isThereDateTime()) {
            sb.append(PREFIX_DATE_TIME.toString() + this.getDateTime().value + " ");
        }
        this.getTags().asObservableList().stream().forEach(s -> sb.append(PREFIX_TAG.toString() + s.tagName + " "));
        return sb.toString();
    }
}

