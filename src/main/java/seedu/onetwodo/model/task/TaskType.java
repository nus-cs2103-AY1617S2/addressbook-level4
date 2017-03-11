package seedu.onetwodo.model.task;

//@@author A0141138N
public enum TaskType {
    FLOATING, DEADLINE, EVENT;
    
    private String prefix;
    
    static {
        FLOATING.prefix = "F";
        DEADLINE.prefix = "D";
        EVENT.prefix = "E";
    }

    public String getPrefix() {
        return prefix;
    }
}
