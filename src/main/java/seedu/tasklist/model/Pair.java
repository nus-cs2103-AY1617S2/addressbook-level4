package seedu.tasklist.model;

import seedu.tasklist.model.task.Task;

//@author A0139747N
/**
 * Creates a Pair object which will be used for undo/redo command.
 */
public class Pair {
    private ReadOnlyTaskList first;
    private Task second;

    public Pair(ReadOnlyTaskList first) {
        this.first = first;
        this.second = null;
    }

    public Pair(ReadOnlyTaskList first, Task second) {
        this.first = first;
        this.second = second;
    }

    public ReadOnlyTaskList getFirst() {
        return first;
    }

    public Task getSecond() {
        return second;
    }

    public boolean isNullTask() {
        if (second == null) {
            return true;
        } else {
            return false;
        }
    }
}
