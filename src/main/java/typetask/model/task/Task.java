package typetask.model.task;

import java.util.Objects;

import typetask.commons.util.CollectionUtil;

/**
 * Represents a Task in TypeTask.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Date date;
    private Time time;

    /**
     * Every field must be present and not null.
     */

    public Task(Name name) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
    }
    public Task(Name name, Date date, Time time) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        this.date = date;
        this.time = time;
    }
    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
       this(source.getName(), source.getDate(), source.getTime());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }
    @Override
    public Name getName() {
        return name;
    }
    @Override
    public Date getDate() {
        return date;
    }
    @Override
    public Time getTime() {
        return time;
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;
        this.setName(replacement.getName());
        this.setDate(replacement.getDate());
        this.setTime(replacement.getTime());
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
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
