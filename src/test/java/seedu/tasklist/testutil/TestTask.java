package seedu.tasklist.testutil;

import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.Status;

/**
 * A mutable task object. For testing only.
 */
public abstract class TestTask implements ReadOnlyTask {

    public abstract void setName(Name name);

    public abstract Name getName();

    public abstract void setComment(Comment comment);

    public abstract Comment getComment();

    public abstract UniqueTagList getTags();

    public abstract void setPriority(Priority priority);

    public abstract Priority getPriority();

    public abstract void setStatus(Status status);

    public abstract Status getStatus();

    public abstract String getAddCommand();

    public abstract String toString();

    public abstract String getAsText();

    public abstract boolean isSameStateAs(ReadOnlyTask other);

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public abstract void setTags(UniqueTagList replacement);
}
