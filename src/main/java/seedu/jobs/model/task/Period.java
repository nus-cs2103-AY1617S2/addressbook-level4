package seedu.jobs.model.task;

import java.util.Optional;

import seedu.jobs.commons.exceptions.IllegalValueException;
//@@author A0130979U
public class Period {

    public static final String MESSAGE_PERIOD_CONSTRAINT = "Period of recurrence must be a positive integer";
    public static final String PERIOD_VALIDATION_REGEX = "^([0-9][0-9]*)$";
    public static final int DEFAULT_PERIOD = 0;
    public int value;

    /**
     * Validates given time-values input.
     *
     * @throws IllegalValueException
     *             if the input is invalid
     */
    public Period(Optional<String> period) throws IllegalValueException {
        if (!period.isPresent()) {
            this.value = DEFAULT_PERIOD;
        } else {
            if (period.get().equals("")) {
                this.value = DEFAULT_PERIOD;
            } else if (!isValidPeriod(period.get())) {
                throw new IllegalValueException(MESSAGE_PERIOD_CONSTRAINT);
            } else {
                this.value = Integer.parseInt(period.get());
            }
        }
    }

    /**
     * Returns true if a given string represents positive integer
     */
    public static boolean isValidPeriod(String test) {
        return test.matches(PERIOD_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Period// instanceof handles nulls
                        && this.value == (((Period) other).value)); // state
                                                                     // check
    }

}
