package seedu.address.model.task;

/**
 * Represents a Task's description in the TaskManager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "No contraints for description";

    public final String description;

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
