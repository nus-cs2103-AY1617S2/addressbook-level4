package seedu.onetwodo.model.task;

//@@author A0141138N
public enum TaskType {
    DEADLINE('D'),
    EVENT('E'),
    TODO('T');

    public static final String EVENT_STRING = "Event";
    public static final String TO_DO_STRING = "To-do";
    public static final String DEADLINE_STRING = "Deadline";
    private final char prefix;


    TaskType(char prefix) {
        this.prefix = prefix;
    }

    public char getPrefix() {
        return prefix;
    }

    @Override
    public String toString() {
        return String.valueOf(prefix);
    }

    public String getDescription() {
        switch (this) {
        case DEADLINE:
            return DEADLINE_STRING;
        case EVENT:
            return EVENT_STRING;
        default:
        case TODO:
            return TO_DO_STRING;
        }
    }
    public static char[] getAllPrefixes() {
        return new char[] {TODO.prefix, DEADLINE.prefix, EVENT.prefix};
    }

    public static TaskType getTaskTypeFromChar(char taskType) {
        char taskTypeCap = Character.toUpperCase(taskType);
        if (taskTypeCap == TODO.prefix) {
            return TODO;
        } else if (taskTypeCap == DEADLINE.prefix) {
            return DEADLINE;
        } else if (taskTypeCap == EVENT.prefix) {
            return EVENT;
        }
        return null;
    }
}
