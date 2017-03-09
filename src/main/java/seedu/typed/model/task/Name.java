package seedu.typed.model.task;


/**
 * Represents a Task's name in the task manager. Guarantees: immutable; 
 */
public class Name {

    public final String fullName;

    public Name(String name) {
        assert name != null;
        this.fullName = name;
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                        && this.fullName.equals(((Name) other).fullName)); // state
                                                                           // check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
