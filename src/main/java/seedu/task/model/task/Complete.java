package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

public class Complete {

    public static final String COMPLETE_VALUE = "true";
    public static final String UNCOMPLETE_VALUE = "false";

    public boolean completion = false;

    /**
    * Validates given description.
    *
    * @throws IllegalValueException if given description string is invalid.
    */
    public Complete(boolean completion) {
        this.completion = completion;
    }

    public void setComplete() {
        this.completion = true;
    }

    public void setNotComplete() {
        this.completion = false;
    }

    public boolean getCompletion() {
        return this.completion;
    }

    public String getString() {
        return completion == true ? COMPLETE_VALUE : UNCOMPLETE_VALUE;
    }

}
