package seedu.watodo.model.task;

/** Represents a task type in the task manager */
public enum TaskType {
    FLOAT("float"), DEADLINE("deadline"), EVENT("event");

    private final String type;

    private TaskType (String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
