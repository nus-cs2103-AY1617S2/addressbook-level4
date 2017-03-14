package t15b1.taskcrusher.model.task;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import t15b1.taskcrusher.commons.exceptions.IllegalValueException;
import t15b1.taskcrusher.model.event.EventDate;

/**
 * Represents a deadline for a task. Empty value means no deadline. 
 */
public class Deadline {
    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "";
    public static final String DEADLINE_VALIDATION_REGEX = "";
    public static final String NO_DEADLINE = "";
    
    public String value;
    public final int inMilseconds = 0;
    
    //TODO keeping for now
    public Deadline(String value) throws IllegalValueException{
        assert value != null;
        if(!isValidDeadline(value)){
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
        this.value = value;
    }
    
    public Deadline(Date deadline) {
    	
    	if (deadline != null) {
    		this.value = deadline.toString();
    	} else {
    		this.value = "";
    	}
    	
    }
    
    private boolean isValidDeadline(String value){
    	
    	Date rightNow = new Date();
    	PrettyTimeParser dateParser = new PrettyTimeParser();
    	
    	//TODO see how parse() can fail and maybe manage this more elegantly
    	List<Date> parsedValue = dateParser.parse(value); 
    	Date deadline = null;
    	
    	if (parsedValue.size() != 0) {
    		deadline = parsedValue.get(0);
    	}
   	
    	if (!this.hasDeadline() ||
    			(deadline != null && !deadline.before(rightNow))) {
    		return true;
    	} else {
    		return false;
    	}

    }
    
    public boolean hasDeadline(){
    	this.value = ""; //TODO why is there a null pointer exception otherwise...
        return !value.equals(NO_DEADLINE);
    }
    
    //TODO replace with above to avoid double negatives, keep for now to avoid breaking code
    public boolean hasNoDeadline(){
        return value.equals(NO_DEADLINE);
    }
    
    @Override
    public String toString(){
        //TODO:Change it so that it deals with Optional
    	return this.value;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                        //TODO: change it so that it deals with Optional
                && this.value.equals(((Deadline) other).value));
    }
    
    public Optional<Date> getDate() {
    	
    	Optional<Date> deadlineAsDate = Optional.empty();
    	
    	if (this.hasDeadline()) {
    		PrettyTimeParser dateParser = new PrettyTimeParser();
    		List<Date> deadline = dateParser.parse(this.value);
    		deadlineAsDate = Optional.of(deadline.get(0));    		
    	}
    	
    	return deadlineAsDate;
    }
}
