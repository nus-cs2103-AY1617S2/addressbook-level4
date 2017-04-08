//@@author A0144885R
package seedu.address.model.task;

/**
 * Represents a Task's description in the TaskManager.
 * Guarantees: immutable;
 */
public class Description {

    public final String defaultDescription = "";

    public final String description;

    public Description() {
        this.description = defaultDescription;
    }

    /**
     * Description just needs to be not null
     */
    public Description(String description) {
        assert description != null;
        this.description = description.trim();
    }


    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.description.equals(((Description) other).description)); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

}
