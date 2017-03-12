package org.teamstbf.yats.model.item;

import java.util.ArrayList;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's Periodicity in the Task Manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidPeriod(String)}
 */

public class Periodic {
	
	private ArrayList<Schedule> scheduleArray;
	private final String PERIODIC_DAILY = "daily";
	private final String PERIODIC_WEEKLY = "weekly";
	private final String PERIODIC_MONTHLY = "monthly";
	private final int REPEAT_FREQUENCY_NONE = 1;
	private final int REPEAT_FREQUENCY_DAILY = 365;
	private final int REPEAT_FREQUENCY_WEEKLY = 52;
	private final int REPEAT_FREQUENCY_MONTHLY = 12;
	

    public static final String MESSAGE_PERIODIC_CONSTRAINTS =
            "Periodicity must be none, daily, weekly or monthly";
    public static final String PERIODIC_VALIDATION_REGEX = ".*(none|daily|weekly|monthly).*";

    public final String value;

    /**
     * Validates given period.
     *
     * @throws IllegalValueException if given period string is invalid.
     */
    public Periodic(String period) throws IllegalValueException {
        assert period != null;
        String trimmedPeriod = period.trim();
        if (!isValidPeriod(trimmedPeriod)) {
            throw new IllegalValueException(MESSAGE_PERIODIC_CONSTRAINTS);
        }
        this.value = trimmedPeriod;
        int repeatFrequency = parsePeriod(trimmedPeriod);
        setupScheduleArray(scheduleArray, repeatFrequency);
    }
    
    public int parsePeriod(String value) {
    	int repeatFrequency = REPEAT_FREQUENCY_NONE;
    	switch(value) {
    	case(PERIODIC_DAILY):
    		repeatFrequency = REPEAT_FREQUENCY_DAILY;
    	case(PERIODIC_WEEKLY):
    		repeatFrequency = REPEAT_FREQUENCY_WEEKLY;
    	case(PERIODIC_MONTHLY):
    		repeatFrequency = REPEAT_FREQUENCY_MONTHLY;
    	}
    	return repeatFrequency;
    }
    
    public ArrayList<Schedule> getScheduleArray() {
    	return scheduleArray;
    }
    
    private void setupScheduleArray(ArrayList<Schedule> scheduleArray, int repeatFrequency) {
    	for(int i=0; i<repeatFrequency; i++) {
    		scheduleArray.add(new Schedule());
    	}
    }

    /**
     * Returns if a given string is a valid period.
     */
    public static boolean isValidPeriod(String test) {
        return test.matches(PERIODIC_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Periodic // instanceof handles nulls
                && this.value.equals(((Periodic) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
