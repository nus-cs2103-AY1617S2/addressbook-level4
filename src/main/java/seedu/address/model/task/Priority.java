package seedu.address.model.task;

import java.util.HashMap;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * @author ryuus
 * Represents a Task's priority in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 *
 */
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Priority Levels are only 1-5";
    public static final String PRIORITY_VALIDATION_REGEX = "[-]?(1|2|3|4|5)";

    public static final String PRIORITY_LEVEL_ONE = "lame";
    public static final String PRIORITY_LEVEL_TWO = "decent";
    public static final String PRIORITY_LEVEL_THREE = "moderate";
    public static final String PRIORITY_LEVEL_FOUR = "forreal";
    public static final String PRIORITY_LEVEL_FIVE = "urgent";
    public static final String PRIORITY_LEVEL_DONE = "completed";

    public static final String PRIORITY_LEVEL_1 = "1";
    public static final String PRIORITY_LEVEL_2 = "2";
    public static final String PRIORITY_LEVEL_3 = "3";
    public static final String PRIORITY_LEVEL_4 = "4";
    public static final String PRIORITY_LEVEL_5 = "5";
    public static final String PRIORITY_LEVEL_DEFAULT_VALUE = PRIORITY_LEVEL_2;

    //public String value;
    public HashMap<String, String> priorityMap = new HashMap<String, String>();
    public final String value;


    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        priorityMap.put(PRIORITY_LEVEL_1, PRIORITY_LEVEL_ONE);
        priorityMap.put(PRIORITY_LEVEL_2, PRIORITY_LEVEL_TWO);
        priorityMap.put(PRIORITY_LEVEL_3, PRIORITY_LEVEL_THREE);
        priorityMap.put(PRIORITY_LEVEL_4, PRIORITY_LEVEL_FOUR);
        priorityMap.put(PRIORITY_LEVEL_5, PRIORITY_LEVEL_FIVE);


        assert priority != null;

        String trimmedPriority = priority.trim();
        if (!isValidPriority(trimmedPriority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }

        /*int actualValue = Integer.parseInt(trimmedPriority);

        if (actualValue < 0) { // means task is completed
            this.number = trimmedPriority;
            this.value = PRIORITY_LEVEL_DONE;

        } else {


            this.value = priorityMap.get(trimmedPriority); // string word
            this.number = trimmedPriority; // string number
        }
        */
        this.value = trimmedPriority;


    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        if (Integer.parseInt(value) < 0) {
            return PRIORITY_LEVEL_DONE;
        } else {
            return priorityMap.get(value);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.value.equals(((Priority) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public boolean isCompleted() {
        return Integer.parseInt(value) < 0;
    }
}
