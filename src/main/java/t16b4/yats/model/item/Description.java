package t16b4.yats.model.item;

/**
 * Represents a Task's description in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Task description can take any values but must be a string. It can be left blank.";

    
    public final String value;

    public Description(String description) {
        this.value = description;
    }
    

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Returns true if a given string is a valid Description.
     */
    public static boolean isValidDescription(String string) {
        if (string != null) {
            return true;
        } else {
            return false;
        }
    }



}
