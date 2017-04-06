//@@author A0163744B
package seedu.task.model.task;

public class Complete {

    public static final String COMPLETE_STRING = "true";
    public static final String INCOMPLETE_STRING = "false";

    public final boolean isComplete;

    public Complete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public String getString() {
        return isComplete == true ? COMPLETE_STRING : INCOMPLETE_STRING;
    }

}
