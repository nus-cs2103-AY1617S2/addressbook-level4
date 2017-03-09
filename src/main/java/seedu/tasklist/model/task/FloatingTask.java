package seedu.tasklist.model.task;

import seedu.tasklist.commons.util.CollectionUtil;
import seedu.tasklist.model.tag.UniqueTagList;

/**
 * Represents a Floating Task in the task list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class FloatingTask extends Task implements ReadOnlyFloatingTask {

    private Name name;
    private Comment comment;
    private Priority priority;
    private Status status; 
    
    private UniqueTagList tags;
    
    /**
     * Every field must be present and not null.
     */
    public FloatingTask(Name name, Comment comment, Priority priority, Status status, UniqueTagList tags) {
        super(name, comment, priority, status, tags);
        assert !CollectionUtil.isAnyNull(name, comment, priority, status, tags);
        this.name = name;
        this.comment = comment;
        this.priority = priority;
        this.status = status;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyFloatingTask.
     */
    public FloatingTask(ReadOnlyFloatingTask source) {
        this(source.getName(), source.getComment(), source.getPriority(), source.getStatus(), source.getTags());
    }
    
    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }
    
    @Override
    public Name getName() {
        return name;
    }
    
    public void setComment(Comment comment) {
        assert comment != null;
        this.comment = comment;
    }

    @Override
    public Comment getComment() {
        return comment;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }
    
    public void setPriority(Priority priority){
        assert priority != null;
        this.priority = priority;
    }
    
    public Priority getPriority() {
        return priority;
    }
   
    public void setStatus(Status status){
        assert status != null;
        this.status = status;
    }

    
    public Status getStatus() {
        return status;
    }
    
    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyFloatingTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setComment(replacement.getComment());
        this.setPriority(replacement.getPriority());
        this.setStatus(replacement.getStatus());
        this.setTags(replacement.getTags());
    }



}
