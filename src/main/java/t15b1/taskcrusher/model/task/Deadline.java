package t15b1.taskcrusher.model.task;

import java.util.Optional;

import t15b1.taskcrusher.commons.exceptions.IllegalValueException;
import t15b1.taskcrusher.model.event.EventDate;

public class Deadline {
    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "";
    public static final String DEADLINE_VALIDATION_REGEX = "";
    
    public final Optional<String> value;
    
    public Deadline(Optional<String> value) throws IllegalValueException{
        assert value != null;
        if(!isValidDeadline(value)){
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
        this.value = value;
    }
    
    private boolean isValidDeadline(Optional<String> value){
        //TODO: if deadline already past
        return false;
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
