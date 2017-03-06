package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;

/**
 * Represents a Task in Task Manager
 */
public class Task implements ReadOnlyTask {

    private final String DEFAULT_DESCRIPTION = "";

    private Name name;
    private Description description;
    private Deadline deadline;

    /**
     * Only name and deadline are required and must not be null
     */
    public Task(Name name, Deadline deadline, Object... params) {
        assert !CollectionUtil.isAnyNull(name, deadline);
        this.name = name;
        this.deadline = deadline;
        this.description = new Description(DEFAULT_DESCRIPTION);

        // Optional parameters
        for(Object param : params) {
            if (param instanceof Description) {
                this.description = (Description)param;
            }
        }
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDeadline(), source.getDescription());
    }

    
    /**
     * Getters and setters
     */
    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setDeadline(Deadline deadline) {
        assert deadline != null;
        this.deadline = deadline;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    public void setDescription(Description description) {
        if (description == null) {
            this.description = new Description(DEFAULT_DESCRIPTION);
        } else {
            this.description = description;
        }
    }

    @Override
    public Description getDescription() {
        return description;
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
        return Objects.hash(name, deadline, description);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
