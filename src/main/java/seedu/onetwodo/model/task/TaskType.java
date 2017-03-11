package seedu.onetwodo.model.task;

//@@author A0141138N
public enum TaskType {
    FLOATING, DEADLINE, EVENT;
    
    private char prefix;
    
    static {
        FLOATING.prefix = 'F';
        DEADLINE.prefix = 'D';
        EVENT.prefix = 'E';
    }

    public char getPrefix() {
        return prefix;
    }
    
    public static char[] getAllPrefixes() {
        return new char[] {FLOATING.prefix, DEADLINE.prefix, EVENT.prefix};
    }
    
    public static TaskType getTaskTypeFromChar(char taskType) {
        switch (Character.toLowerCase(taskType)) {
        case 'f': return FLOATING;
        case 'd': return DEADLINE;
        case 'e': return EVENT;
        };
        return null;
    }
}
