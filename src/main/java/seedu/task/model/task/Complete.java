package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

public class Complete {
	
	 public boolean Completion;

	    /**
	     * Validates given description.
	     *
	     * @throws IllegalValueException if given description string is invalid.
	     */
	    public Complete(boolean Completion){
	        this.Completion = Completion;
	    }

	    public void setCompete(){
	    	this.Completion=true;	    	
	    }
	    
	    public void setNotCompete(){
	    	this.Completion=false;	    	
	    }
	    
	    public boolean getCompletion(){
	    	return this.Completion;
	    }
	    

}
