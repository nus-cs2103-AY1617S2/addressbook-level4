package project.taskcrusher.model.task;

import java.util.Objects;
import java.util.Optional;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.commons.util.CollectionUtil;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.tag.UniqueTagList;

/**
 * Represents an active task.
 * Guarantees: details are present and not null (just empty in <Optional>), field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Deadline deadline;
    private Priority priority;
    private Description description;

    private UniqueTagList tags;

    /**
     * Modified for Task.
     */
    public Task(Name name, Deadline deadline, Priority priority, Description description, UniqueTagList tags){
        assert !CollectionUtil.isAnyNull(name);

        this.name = name;
        this.deadline = deadline;
        this.priority = priority;
        this.description = description;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source){
        this(source.getTaskName(), source.getDeadline(), source.getPriority(), source.getDescription(), source.getTags());
    }

    public void setTaskName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getTaskName() {
        return name;
    }

    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    public void setDeadline(Deadline deadline) {
        assert deadline != null;
        this.deadline = deadline;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    public boolean hasDeadline(){
        return this.deadline.hasDeadline();
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setTaskName(replacement.getTaskName());
        this.setPriority(replacement.getPriority());
        this.setDeadline(replacement.getDeadline());
        this.setDescription(replacement.getDescription());
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
        return Objects.hash(name, deadline, priority, description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
