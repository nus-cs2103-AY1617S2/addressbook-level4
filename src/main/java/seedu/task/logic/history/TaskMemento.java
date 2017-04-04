//@@author A0163744B
package seedu.task.logic.history;

import seedu.task.model.task.Task;
import seedu.task.model.task.TaskId;

/**
 * A memento contains the information to restore the system to some state. In this case, it
 * only contains a Task. This task object may be used to undo/redo the effects of commands on a
 * Task. The Task ID may be used to match up the memento's task with that in the Task list.
 */
public class TaskMemento {
    public final TaskId taskId;
    public final Task oldTask;
    public final Task newTask;

    public TaskMemento(Task oldTask, Task newTask) {
        assert oldTask != null || newTask != null;
        System.out.println("creating memento");
        if (oldTask != null && newTask != null) {
            assert oldTask.getTaskId().equals(newTask.getTaskId());
        }
        this.taskId = oldTask != null ? oldTask.getTaskId() : newTask.getTaskId();
        this.oldTask = oldTask != null ? new Task(oldTask) : null;
        this.newTask = newTask != null ? new Task(newTask) : null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TaskMemento)) { // instance of handles nulls
            return false;
        }

        boolean isOldTaskEqual = this.oldTask == null ?
                ((TaskMemento) other).oldTask == null :
                this.oldTask.equals(((TaskMemento) other).oldTask);

        boolean isNewTaskEqual = this.newTask == null ?
                ((TaskMemento) other).newTask == null :
                this.newTask.equals(((TaskMemento) other).newTask);

        boolean isTaskIdEqual = this.taskId.equals(((TaskMemento) other).taskId);

        return isOldTaskEqual && isNewTaskEqual && isTaskIdEqual;
    }
}
