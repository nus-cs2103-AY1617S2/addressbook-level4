package seedu.typed.model.task;


/**
 * Represents a Task's name in the task manager. Guarantees: immutable;
 */
public class Name {

    public static final String MESSAGE_NAME_CONSTRAINTS = "Task name should not be blank";
    public final String value;

    public Name(String name) {
        assert name != null;
        this.value = name;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                        && this.value.equals(((Name) other).getValue())); // state
                                                                           // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String getValue() {
        return this.value;
    }

    public static boolean isValidName(String name) {
        if (name == null) {
            return false;
        }
        String trimmedName = name.trim();
        if (trimmedName.equals("")) {
            return false;
        } else {
            return true;
        }
    }

}
