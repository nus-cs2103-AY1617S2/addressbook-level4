package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the ToDo List.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

    private Description description;
    private Priority priority;
    private TaskDate startDate;
    private TaskDate endDate;
    private boolean complete;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Description description, Priority priority, TaskDate startDate, TaskDate endDate, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, priority, startDate, tags);
        this.description = description;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
        this.complete = false;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getPriority(), source.getStartDate(),
            source.getEndDate(), source.getTags());
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setStartDate(TaskDate date) {
        assert date != null;
        this.startDate = date;
    }

    @Override
    public TaskDate getStartDate() {
        return startDate;
    }

    public void setEndDate(TaskDate date) {
        assert date != null;
        this.endDate = date;
    }

    @Override
    public TaskDate getEndDate() {
        return endDate;
    }

    public void setComplete() {
        this.complete = true;
    }

    public boolean isComplete() {
        return this.complete;
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

        this.setDescription(replacement.getDescription());
        this.setPriority(replacement.getPriority());
        this.setStartDate(replacement.getStartDate());
        this.setEndDate(replacement.getEndDate());
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
        return Objects.hash(description, priority, startDate, endDate, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    /**
    * Results in Tasks sorted by priority, followed by endDate, then startDate.
    * Note: If a and b are tasks and a.compareTo(b) == 0, that does not imply a.equals(b).
    */
   @Override
   public int compareTo(Task compareTask) {

       int compareToResult = this.priority.compareTo(compareTask.priority);
       
       if (compareToResult == 0) {
           compareToResult = this.endDate.compareTo(compareTask.endDate);
       }
       
       if (compareToResult == 0) {
           compareToResult = this.startDate.compareTo(compareTask.startDate);
       }
       return compareToResult;
   }

}
