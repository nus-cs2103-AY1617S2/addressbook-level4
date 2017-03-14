package seedu.ezdo.model.todo;

import seedu.ezdo.logic.parser.DateParser;

/**
 * Represents the date associated with tasks.
 */
public abstract class TaskDate {

    public String value;

    public static final String TASKDATE_VALIDATION_REGEX = "^$|(0[1-9]|"
            + "[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)[0-9]{2} "
            + "(2[0-3]|[0-1][0-9])[:][0-5][0-9]";

    public TaskDate(String taskDate) {
        DateParser dateParser = new DateParser(taskDate);
        this.value = dateParser.value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instanceof handles nulls
                        && this.value.equals(((TaskDate) other).value)); // state
                                                                         // check
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
