package seedu.todolist.model.todo;

import java.util.Date;
import java.util.Objects;

import seedu.todolist.model.tag.UniqueTagList;

/**
 * Represents a Todo in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Todo implements ReadOnlyTodo {

    private Name name;
    private Date startTime;
    private Date endTime;
    private Date completeTime;

    private UniqueTagList tags;

    /**
     * Constructor for a floating task
     */
    public Todo(Name name, UniqueTagList tags) {
        this(name, null, null, null, tags);
    }

    //@@author A0163786N, A0163720M
    /**
     * Constructor for a deadline
     */
    public Todo(Name name, Date endTime, UniqueTagList tags) {
        this(name, null, endTime, null, tags);
    }
    //@@author

    //@@author A0163786N, A0163720M
    /**
     * Constructor for an event
     */
    public Todo(Name name, Date startTime, Date endTime, UniqueTagList tags) {
        this(name, startTime, endTime, null, tags);
    }
    //@@author

    //@@author A0163786N
    /**
     * Constructor for general todo
     */
    public Todo(Name name, Date startTime, Date endTime, Date completeTime, UniqueTagList tags) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.completeTime = completeTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    //@@author
    //@@author A0165043M
    /**
     * Creates a copy of the given ReadOnlyTodo.
     */
    public Todo(ReadOnlyTodo source) {
        this.name = source.getName();
        this.tags = source.getTags();
        if (source.getStartTime() != null && source.getEndTime() != null) {
            this.startTime = source.getStartTime();
            this.endTime = source.getEndTime();
        } else if (source.getStartTime() == null && source.getEndTime() != null) {
            this.endTime = source.getEndTime();
        }
        if (source.getCompleteTime() != null) {
            this.completeTime = source.getCompleteTime();
        }
    }
    //@@author
    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public Date getEndTime() {
        return endTime;
    }
    //@@author A0163786N
    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }
    //@@author
    //@@author A0163786N
    @Override
    public Date getCompleteTime() {
        return completeTime;
    }
    //@@author
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this todo's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this todo with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTodo replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setStartTime(replacement.getStartTime());
        this.setEndTime(replacement.getEndTime());
        this.setCompleteTime(replacement.getCompleteTime());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTodo // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTodo) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startTime, endTime, completeTime, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
