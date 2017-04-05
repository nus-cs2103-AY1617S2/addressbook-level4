package typetask.model.task;

import java.util.Objects;

import typetask.commons.util.CollectionUtil;

/**
 * Represents a Task in TypeTask.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private DueDate date;
    private boolean isCompleted;
    private DueDate endDate;
    private Priority priority;

    //@@author A0139926R
    /**
     * Every field must be present and not null.
     */
    public Task(Name name, DueDate date, DueDate endDate, boolean isCompleted, Priority priority) {

        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        this.date = date;
        this.endDate = endDate;
        this.isCompleted = isCompleted;
        this.priority = priority;
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
       this(source.getName(), source.getDate(), source.getEndDate(),
              source.getIsCompleted(), source.getPriority());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    //@@author A0139926R
    public void setDate(DueDate date) {
        this.date = date;
    }
    //@@author A0139926R
    public void setEndDate(DueDate endDate) {
        this.endDate = endDate;
    }

    //@@author A0144902L
    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    //@@author A0144902L
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public Name getName() {
        return name;
    }
    //@@author A0139926R
    @Override
    public DueDate getDate() {
        return date;
    }
    //@@author A0139926R
    @Override
    public DueDate getEndDate() {
        return endDate;
    }

    //@@author A0144902L
    @Override
    public boolean getIsCompleted() {
        return isCompleted;
    }

    //@@author A0144902L
    @Override
    public Priority getPriority() {
        return priority;
    }

    //@@author A0139154E
    public String getIsCompletedToString() {
        if (isCompleted) {
            return "Yes";
        } else {
            return "No";
        }
    }
    //@@author A0139926R

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;
        this.setName(replacement.getName());
        this.setDate(replacement.getDate());
        this.setEndDate(replacement.getEndDate());
        this.setPriority(replacement.getPriority());
    }

    //@@author A0144902L
    /**
     * Marks this task as completed {boolean is set true}.
     */
    public void markComplete(ReadOnlyTask complete) {
        assert complete != null;
        this.setIsCompleted(true);
    }
    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
