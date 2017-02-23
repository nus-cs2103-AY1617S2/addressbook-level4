package seedu.address.model.task;

/**
 * Represents a Task
 */
public class Task {

    public String description;

    /**
     * To be used with json deserialisation
     */
    public Task() {}

    public Task(String description) {
        assert description != null;
        String trimmedDescription = description.trim();
        this.description = trimmedDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Task // instanceof handles nulls
                && this.description.equals(((Task) other).description)); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return description;
    }

}
