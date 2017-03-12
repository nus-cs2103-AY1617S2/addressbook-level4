package t15b1.taskcrusher.model.task;

import java.util.Optional;

import t15b1.taskcrusher.commons.exceptions.IllegalValueException;
import t15b1.taskcrusher.model.event.EventDate;

/**
 * Represents a deadline for a task. Empty value means no deadline. 
 */
public class Deadline {
    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "";
    public static final String DEADLINE_VALIDATION_REGEX = "";
    public static final String NO_DEADLINE = "";
    
    public final String value;
    public final int inMilseconds = 0;
    
    public Deadline(String value) throws IllegalValueException{
        assert value != null;
        if(!isValidDeadline(value)){
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
        this.value = value;
    }
    
    private boolean isValidDeadline(String value){
        if(value.equals(NO_DEADLINE))
            return true;
        //TODO: if deadline already past
        return false;
    }
    
    public boolean hasNoDeadline(){
        return value.equals(NO_DEADLINE);
    }
    
    @Override
    public String toString(){
        //TODO:Change it so that it deals with Optional
        return null;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                        //TODO: change it so that it deals with Optional
                && this.value.equals(((Deadline) other).value));
    }
    
    
}
