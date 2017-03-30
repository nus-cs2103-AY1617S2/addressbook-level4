package seedu.onetwodo.testutil;

import java.time.LocalDateTime;

import seedu.onetwodo.logic.commands.AddCommand;
import seedu.onetwodo.model.tag.UniqueTagList;
import seedu.onetwodo.model.task.Description;
import seedu.onetwodo.model.task.EndDate;
import seedu.onetwodo.model.task.Name;
import seedu.onetwodo.model.task.Priority;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.Recurring;
import seedu.onetwodo.model.task.StartDate;
import seedu.onetwodo.model.task.TaskType;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Description description;
    private StartDate startDate;
    private EndDate endDate;
    private Recurring recur;
    private Priority priority;
    private boolean isDone = false;
    private boolean isToday = false;
    private TaskType type;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
        type = null;
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.startDate = taskToCopy.getStartDate();
        this.endDate = taskToCopy.getEndDate();
        this.recur = taskToCopy.getRecur();
        this.priority = taskToCopy.getPriority();
        this.description = taskToCopy.getDescription();
        this.isDone = taskToCopy.getDoneStatus();
        this.tags = taskToCopy.getTags();
        initTaskType();
    }

    public void initTaskType() {
        if (!startDate.hasDate() && !endDate.hasDate()) {
            this.type = TaskType.TODO;
        } else if (!startDate.hasDate() && endDate.hasDate()) {
            this.type = TaskType.DEADLINE;
        } else if (startDate.hasDate() && endDate.hasDate()) {
            this.type = TaskType.EVENT;
        }
    }

    // Getter
    @Override
    public Name getName() {
        return name;
    }

    @Override
    public StartDate getStartDate() {
        return startDate;
    }

    @Override
    public EndDate getEndDate() {
        return endDate;
    }

    @Override
    public Recurring getRecur() {
        return recur;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public TaskType getTaskType() {
        return type;
    }

    @Override
    public boolean getDoneStatus() {
        return isDone;
    }

    @Override
    public boolean getTodayStatus() {
        return isToday;
    }

    @Override
    public UniqueTagList getTags() {
        return tags.sort();
    }

    // Setter
    public void setName(Name name) {
        this.name = name;
    }

    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(EndDate endDate) {
        this.endDate = endDate;
    }

    public void setRecur(Recurring recur) {
        this.recur = recur;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public void setTaskType(TaskType type) {
        this.type = type;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append(AddCommand.COMMAND_WORD + " " + this.getName().fullName + " ");
        if (this.hasStartDate()) {
            sb.append("s/" + this.getStartDate().value + " ");
        }
        if (this.hasEndDate()) {
            sb.append("e/" + this.getEndDate().value + " ");
        }
        if (this.hasRecur()) {
            sb.append("r/" + this.getRecur().value + " ");
        }
        if (this.hasPriority()) {
            sb.append("p/" + this.getPriority().value + " ");
        }
        if (this.hasDescription()) {
            sb.append("d/" + this.getDescription().value + " ");
        }
        if (this.hasTag()) {
            this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        }

        return sb.toString();
    }

    @Override
    public boolean isOverdue() {
        switch (type) {
        case DEADLINE:
        case EVENT:
            return LocalDateTime.now().isAfter(endDate.getLocalDateTime());
        default:
        case TODO:
            return false;
        }
    }

}
