//@@evanyeung A0163744B
package seedu.task.model.task;

public class TaskId {
    public final long id;

    public TaskId(long id) {
        this.id = id;
        System.out.println(id);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskId // instance of handles nulls
                && this.id == ((TaskId) other).id); // state check
    }

}
