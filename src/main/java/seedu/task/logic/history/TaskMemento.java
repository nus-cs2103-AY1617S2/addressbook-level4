//@@evanyeung A0163744B
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
    public final Task task;

    public TaskMemento(Task task) {
        assert task != null;
        this.task = task;
        this.taskId = task.getTaskId();
    }

    public TaskMemento(Task task, TaskId taskId) {
        assert taskId != null;
        this.task = task;
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TaskMemento)) { // instanceof handles nulls
            return false;
        }

        boolean isTaskEqual = this.task == null ?
                ((TaskMemento) other).task == null :
                this.task.equals(((TaskMemento) other).task);

        boolean isTaskIdEqual = this.taskId.equals(((TaskMemento) other).taskId);

        return isTaskEqual && isTaskIdEqual;
    }
}
