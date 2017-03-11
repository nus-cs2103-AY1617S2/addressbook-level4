package seedu.tasklist.model.task;

import seedu.tasklist.model.tag.UniqueTagList;

/**
 * Represents a Task in the task list.
 * Guarantees: details are present and not null, field values are validated.
 */
public abstract class Task implements ReadOnlyTask {

    public abstract void setName(Name name);

    public abstract Name getName();

    public abstract void setComment(Comment comment);

    public abstract Comment getComment();

    public abstract UniqueTagList getTags();

    public abstract void setPriority(Priority priority);

    public abstract Priority getPriority();

    public abstract void setStatus(Status status);

    public abstract Status getStatus();

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public abstract void setTags(UniqueTagList replacement);


}
