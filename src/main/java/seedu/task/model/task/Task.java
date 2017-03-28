package seedu.task.model.task;

import java.util.Comparator;
import java.util.Objects;

import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.UniqueTagList;

/**
 * Represents a Task in the ToDo List. Guarantees: details are present and not
 * null, field values are validated.
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

    private Description description;
    private Priority priority;
    private Timing startTiming;
    private Timing endTiming;
    private boolean complete;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Description description, Priority priority, Timing startTiming, Timing endTiming, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, priority, startTiming, tags);
        this.description = description;
        this.priority = priority;
        this.startTiming = startTiming;
        this.endTiming = endTiming;
        this.complete = false;
        this.tags = new UniqueTagList(tags); // protect internal tags from
        // changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getPriority(), source.getStartTiming(), source.getEndTiming(),
                source.getTags());
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

    public void setStartTiming(Timing startTiming) {
        assert startTiming != null;
        this.startTiming = startTiming;
    }

    @Override
    public Timing getStartTiming() {
        return startTiming;
    }

    public void setEndTiming(Timing endTiming) {
        assert endTiming != null;
        this.endTiming = endTiming;
    }

    @Override
    public Timing getEndTiming() {
        return endTiming;
    }

    public void setComplete() {
        this.complete = true;
    }

    @Override
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
        this.setStartTiming(replacement.getStartTiming());
        this.setEndTiming(replacement.getEndTiming());
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
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(description, priority, startTiming, endTiming, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    //@@author A0163559U
    /**
     * Results in Tasks sorted by completed state, followed by priority, endTiming, startTiming
     * and lastly by description.
     * Note: If a and b are tasks and a.compareTo(b) == 0, that does not imply
     * a.equals(b).
     */
    @Override
    public int compareTo(Task compareTask) {
        int compareToResult = 0;

        if (this.isComplete() && compareTask.isComplete()) {
            compareToResult = 0;
        } else if (this.isComplete()) {
            compareToResult = 1;
        } else if (compareTask.isComplete()) {
            compareToResult = -1;
        }

        if (compareToResult == 0) {
            compareToResult = this.priority.compareTo(compareTask.priority);
        }

        if (compareToResult == 0) {
            compareToResult = this.endTiming.compareTo(compareTask.endTiming);
        }

        if (compareToResult == 0) {
            compareToResult = this.startTiming.compareTo(compareTask.startTiming);
        }

        if (compareToResult == 0) {
            compareToResult = this.getDescription().compareTo(compareTask.getDescription());
        }

        return compareToResult;
    }

    public static Comparator<Task> TaskComparator = new Comparator<Task>() {

        @Override
        public int compare(Task task1, Task task2) {
            return task1.compareTo(task2);
        }

    };
    //@@author

}
