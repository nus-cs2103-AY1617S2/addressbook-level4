package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

public class Complete {

    public static final String COMPLETE_WORD = "true";
    public static final String UNCOMPLETE_WORD = "false";

    public static final boolean COMPLETE_VALUE = true;
    public static final boolean UNCOMPLETE_VALUE = false;

    public boolean completion = UNCOMPLETE_VALUE;

    /**
    * Validates given description.
    *
    * @throws IllegalValueException if given description string is invalid.
    */
    public Complete(boolean completion) {
        this.completion = completion;
    }

    public void setComplete() {
        this.completion = COMPLETE_VALUE;
    }

    public void setNotComplete() {
        this.completion = UNCOMPLETE_VALUE;
    }

    public boolean getCompletion() {
        return this.completion;
    }

    public String getString() {
        return completion == true ? COMPLETE_WORD : UNCOMPLETE_WORD;
    }

}
