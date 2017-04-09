package seedu.task.model.task;

import java.util.Objects;

import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */

public class Task implements ReadOnlyTask {

    private TaskName name;
    private Deadline date;
    private PriorityLevel priority;
    private Information info;
    private UniqueTagList tags;

    //@@author A0139161J
    private String parserInfo;
    private int index;
    //@@author

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName name, Deadline date, PriorityLevel priority, Information info, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, date, priority, info, tags);
        this.name = name;
        this.date = date;
        this.priority = priority;
        this.info = info;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTaskName(), source.getDate(), source.getPriority(), source.getInfo(), source.getTags());
    }

    public void setName(TaskName name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public TaskName getTaskName() {
        return name;
    }

    public void setDate(Deadline date) {
        assert date != null;
        this.date = date;
    }

    @Override
    public Deadline getDate() {
        return date;
    }

    public void setPriority(PriorityLevel priority) {
        assert priority != null;
        this.priority = priority;
    }

    @Override
    public PriorityLevel getPriority() {
        return priority;
    }

    public void setInformation(Information info) {
        assert info != null;
        this.info = info;
    }

    @Override
    public Information getInfo() {
        return info;
    }

  //@@author A0139161J
    public void setParserInfo(String info) {
        this.parserInfo = info;
    }

    public String getParserInfo() {
        return this.parserInfo;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
    //@@author

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

        this.setName(replacement.getTaskName());
        this.setDate(replacement.getDate());
        this.setPriority(replacement.getPriority());
        this.setInformation(replacement.getInfo());
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
        return Objects.hash(name, date, priority, info, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
