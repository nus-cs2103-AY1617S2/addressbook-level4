package typetask.model.task;

//@@author A0140010M
public class Priority {

    private boolean priority;

    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Priority should be either in true or false.";

    public Priority () {
        this.priority = false;
    }

    public Priority(boolean priority) {
        this.priority = priority;
    }

    public Priority(String priority) {
        String amended = priority.substring(2).trim();
        if (amended.toLowerCase().compareTo("true") == 0) {
            this.priority = true;
        } else {
            this.priority = false;
        }
    }

    public boolean getPriority() {
        return priority;
    }


    @Override
    public int hashCode() {
        return this.hashCode();
    }
}
