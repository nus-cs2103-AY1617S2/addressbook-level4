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

    public String value;
    public HashMap<String, String> priorityMap = new HashMap<String, String>();
    public String number;
    

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
        
        int actualValue = Integer.parseInt(trimmedPriority);
        if (actualValue < 0) { // means task is completed
        	this.number = trimmedPriority;
        	this.value = PRIORITY_LEVEL_DONE;
        	
        } else {
        
  
        	this.value = priorityMap.get(trimmedPriority); // string word
        	this.number = trimmedPriority; // string number
        }

        
        
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.number.equals(((Priority) other).number)); // state check
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
    
    public void setAsComplete() {
    	String negativeNum = "-" + this.number;
    	this.number = negativeNum;
        this.value = PRIORITY_LEVEL_DONE;
    }
    
    public void setAsIncomplete() {
    	String toBeReplaced = priorityMap.get(this.number.substring(1));
    	this.value = toBeReplaced;
    	this.number = this.number.substring(1);
    }
}
