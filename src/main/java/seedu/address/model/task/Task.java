package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Title title;
    private Content content;
    private TaskDateTime dateTime;
    private boolean status;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Title title, Content content, TaskDateTime dateTime, UniqueTagList tags, boolean status) {
        assert !CollectionUtil.isAnyNull(title);
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.status = status;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTitle(), source.getContent(), source.getDateTime(), source.getTags(), source.getStatus());
    }

    public void setTitle(Title title) {
        assert title != null;
        this.title = title;
    }

    public void setContent(Content content) {
        assert content != null;
        this.content = content;
    }

    public void setDateTime(TaskDateTime dateTime) {
        assert dateTime != null;
        this.dateTime = dateTime;
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
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setTitle(replacement.getTitle());
        this.setContent(replacement.getContent());
        this.setDateTime(replacement.getDateTime());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, content, dateTime, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    public void setStatus(boolean status){
    	this.status = status;
    }
    public boolean getStatus(){
    	return status;
    }

}

