package seedu.tasklist.model.task;

import seedu.tasklist.commons.util.CollectionUtil;
import seedu.tasklist.model.tag.UniqueTagList;

/**
 * Represents a Floating Task in the task list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class FloatingTask implements ReadOnlyFloatingTask {

    private Name name;
    private Comment comment;
    private Priority priority;
    private Status status; 
    
    private UniqueTagList tags;
    
    /**
     * Every field must be present and not null.
     */
    public FloatingTask(Name name, Comment comment, Priority priority, Status status, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, comment, priority,status, tags);
        this.name = name;
        this.comment = comment;
        this.priority = priority;
        this.status = status;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public FloatingTask(ReadOnlyFloatingTask source) {
        this(source.getName(), source.getComment(), source.getPriority(), source.getStatus(), source.getTags());
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
    
    public Priority getPriority() {
        return priority;
    }
   
    public Status getStatus() {
        return status;
    }


}
