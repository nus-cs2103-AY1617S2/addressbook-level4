package project.taskcrusher.model.task;

import java.util.Date;
import java.util.Objects;

import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.shared.Priority;
import project.taskcrusher.model.shared.UserToDo;
import project.taskcrusher.model.tag.UniqueTagList;

//@@author A0127737X
/**
 * Represents an active task. Guarantees: details are present and not null (just
 * empty in <Optional>), field values are validated.
 */
public class Task extends UserToDo implements ReadOnlyTask {

    public static final String TASK_FLAG = "t";

    private Deadline deadline;
    private boolean isOverdue;

    /**
     * Modified for Task.
     */
    public Task(Name name, Deadline deadline, Priority priority, Description description, UniqueTagList tags) {
        super(name, priority, description, tags);
        assert deadline != null;

        this.deadline = deadline;
        this.isOverdue = false;
    }

    /**
     * This constructor is used when loading from storage
     */
    public Task(Name name, Deadline deadline, Priority priority, Description description, UniqueTagList tags,
            boolean isComplete, boolean isOverdue) {
        super(name, priority, description, tags);
        assert deadline != null;

        this.deadline = deadline;
        this.isOverdue = isOverdue;
        this.isComplete = isComplete;
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDeadline(), source.getPriority(), source.getDescription(),
                source.getTags(), source.isComplete(), source.isOverdue());
    }

    public void updateOverdueStatus() {
        Date now = new Date();
        if (hasDeadline() && now.after(getDeadline().getDate().get())) {
            markOverdue();
        }
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    public void setDeadline(Deadline deadline) {
        assert deadline != null;
        this.deadline = deadline;
    }

    public boolean hasDeadline() {
        return this.deadline.hasDeadline();
    }

    public boolean isOverdue() {
        return this.isOverdue;
    }

    public void markOverdue() {
        this.isOverdue = true;
    }

    public void unmarkOverdue() {
        this.isOverdue = false;
    }

    @Override
    public void markComplete() {
        super.markComplete();
        isOverdue = false;
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setPriority(replacement.getPriority());
        this.setDeadline(replacement.getDeadline());
        this.setDescription(replacement.getDescription());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, deadline, priority, description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public int compareTo(ReadOnlyTask another) {
        if (this.isComplete) {
            if (another.isComplete()) {
                return 0;
            } else {
                return 1;
            }
        } else if (another.isComplete()) {
            return -1;
        }
        //neither is complete

        if (!this.getDeadline().hasDeadline() && !another.getDeadline().hasDeadline()) {
            return this.getPriority().compareTo(another.getPriority());
        } else if (!this.getDeadline().hasDeadline() && another.getDeadline().hasDeadline()) {
            return 1;
        } else if (this.getDeadline().hasDeadline() && !another.getDeadline().hasDeadline()) {
            return -1;
        } else {
            //both has deadline
            Date thisDate = this.getDeadline().getDate().get();
            assert thisDate != null;
            Date anotherDate = another.getDeadline().getDate().get();
            assert anotherDate != null;
            return thisDate.compareTo(anotherDate);
        }
    }

}
