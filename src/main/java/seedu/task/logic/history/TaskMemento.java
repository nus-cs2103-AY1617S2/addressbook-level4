//@@evanyeung A0163744B
package seedu.task.logic.history;

import seedu.task.model.task.Task;

/**
 * A memento contains the information to restore the system to some state. In this case, it
 * only contains a Task. This task object may be used to undo/redo the effects of commands on a
 * Task. The Task ID may be used to match up the memento's task with that in the Task list.
 */
public class TaskMemento {
    public final Task task;

    public TaskMemento(Task task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskMemento // instanceof handles nulls
                && this.task.equals(((TaskMemento) other).task));
    }
}
