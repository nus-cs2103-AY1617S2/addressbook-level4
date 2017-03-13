package seedu.ezdo.model.todo;

import com.joestelmach.natty.Parser;

/**
 * Represents the date associated with tasks.
 */
public abstract class TaskDate {

    public final String value;

    public static final String TASKDATE_VALIDATION_REGEX =
        "^$|([12][0-9]|3[01]|0?[1-9])/(0?[1-9]|1[012])/((?:19|20)\\d\\d)";

    public TaskDate(String taskDate) {
    	Parser parser = new Parser();
        this.value = parser.parse(taskDate).get(0).getDates().get(0).toString();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instanceof handles nulls
                && this.value.equals(((TaskDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Returns true if a given string is a valid task date.
     */
    public static boolean isValidTaskDate(String test) {
        return test.matches(TASKDATE_VALIDATION_REGEX);
    }
}
