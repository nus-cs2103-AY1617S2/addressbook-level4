package seedu.onetwodo.model.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import seedu.onetwodo.model.tag.UniqueTagList;

/**
 * Represents a Task in the toDo list. Guarantees: details are present and not
 * null, field values are validated.
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

    private Name name;
    private StartDate startDate;
    private EndDate endDate;
    private Priority priority;
    private Recurring recur;
    private Description description;
    private UniqueTagList tags;

    private TaskType type;
    private boolean isDone;
    private boolean isToday;

    // @@author A0141138N
    /**
     * Every field must be present and not null. Event
     */
    public Task(Name name, StartDate startDate, EndDate endDate, Recurring recur,
            Priority priority, Description description, UniqueTagList tags) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.recur = recur;
        this.priority = priority;
        this.description = description;
        this.tags = new UniqueTagList(tags); // protect internal tags from
        // changes in the arg list
        this.isDone = false;
        checkTaskType(startDate, endDate);
        isToday(startDate, endDate);
    }

    // @@ author A0141138N
    private void checkTaskType(StartDate startDate, EndDate endDate) {
        if (!startDate.hasDate() && !endDate.hasDate()) {
            this.type = TaskType.TODO;
        } else if (!startDate.hasDate() && endDate.hasDate()) {
            this.type = TaskType.DEADLINE;
        } else if (startDate.hasDate() && endDate.hasDate()) {
            this.type = TaskType.EVENT;
        } else {
            this.type = null;
        }
    }

    // @@author A0141138N
    private void isToday(StartDate startDate, EndDate endDate) {
        LocalDate dateEnd = LocalDate.MIN;
        LocalDate dateStart = LocalDate.MAX;
        if (endDate.hasDate()) {
            dateEnd = endDate.getLocalDateTime().toLocalDate();
        }
        if (startDate.hasDate()) {
            dateStart = startDate.getLocalDateTime().toLocalDate();
        }
        if ((endDate.hasDate()) && (!startDate.hasDate()) && (dateEnd.isEqual(LocalDate.now()))) {
            this.isToday = true;
        } else if ((endDate.hasDate()) && (startDate.hasDate())) {
            if ((dateStart.isEqual(LocalDate.now())) || (dateEnd.isEqual(LocalDate.now()))) {
                this.isToday = true;
            } else if ((dateStart.isBefore(LocalDate.now())) && (dateEnd.isAfter(LocalDate.now()))) {
                this.isToday = true;
            }

        } else {
            this.isToday = false;
        }
    }

    //@@author
    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDate(), source.getEndDate(), source.getRecur(),
                source.getPriority(), source.getDescription(), source.getTags(), source.getDoneStatus());
    }

    public Task(Name name, StartDate startDate, EndDate endDate, Recurring recur,
            Priority priority, Description description, UniqueTagList tags, boolean isDone) {
        this(name, startDate, endDate, recur, priority, description, tags);
        this.isDone = isDone;
    }

    // Getters
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

    //@@author A0139343E
    @Override
    public Recurring getRecur() {
        return recur;
    }

    //@@author A0141138N
    @Override
    public Priority getPriority() {
        return priority;
    }

    //@@author
    @Override
    public Description getDescription() {
        return description;
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
    public TaskType getTaskType() {
        return type;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    // Setters
    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    public void setStartDate(StartDate startDate) {
        assert startDate != null;
        this.startDate = startDate;
    }

    public void setEndDate(EndDate endDate) {
        assert endDate != null;
        this.endDate = endDate;
    }

    //@@author A0139343E
    public void setRecur(Recurring recur) {
        assert recur != null;
        this.recur = recur;
    }

    //@@author A0141138N
    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    //@@author
    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    public void setDone() {
        assert isDone == false;
        /*        if (!this.hasRecur()) {
            isDone = true;
        } else {
            forwardTaskRecurDate();
        }*/
        isDone = true;
    }

    public void setUndone() {
        assert isDone == true;
        isDone = false;
    }

    public void setToday() {
        assert isToday == false;
        isToday = true;
    }

    public void setTaskType(TaskType type) {
        this.type = type;
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setStartDate(replacement.getStartDate());
        this.setEndDate(replacement.getEndDate());
        this.setRecur(replacement.getRecur());
        this.setPriority(replacement.getPriority());
        this.setDescription(replacement.getDescription());
        this.setTags(replacement.getTags());
    }

    //@@author A0139343E
    public void forwardTaskRecurDate() {
        assert this.getTaskType() != TaskType.TODO;
        StartDate tempStartDate;
        EndDate tempEndDate = getEndDate();
        switch(this.recur.getRecur()) {
        case DAILY:
            this.setEndDate(new EndDate(tempEndDate.localDateTime.get().plusDays(1)));
            if (this.hasStartDate()) {
                tempStartDate = getStartDate();
                this.setStartDate(new StartDate(tempStartDate.localDateTime.get().plusDays(1)));
            }
            break;
        case WEEKLY:
            this.setEndDate(new EndDate(tempEndDate.localDateTime.get().plusWeeks(1)));
            if (this.hasStartDate()) {
                tempStartDate = getStartDate();
                this.setStartDate(new StartDate(tempStartDate.localDateTime.get().plusWeeks(1)));
            }
            break;
        case MONTHLY:
            this.setEndDate(new EndDate(tempEndDate.localDateTime.get().plusMonths(1)));
            if (this.hasStartDate()) {
                tempStartDate = getStartDate();
                this.setStartDate(new StartDate(tempStartDate.localDateTime.get().plusMonths(1)));
            }
            break;
        case YEARLY:
            this.setEndDate(new EndDate(tempEndDate.localDateTime.get().plusYears(1)));
            if (this.hasStartDate()) {
                tempStartDate = getStartDate();
                this.setStartDate(new StartDate(tempStartDate.localDateTime.get().plusYears(1)));
            }
            break;
        default:
            break;
        }
    }

    //@@author
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, startDate, endDate, recur, priority, description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public int compareTo(Task taskB) {
        int i = type.compareTo(taskB.type);
        if (i != 0) {
            return i;
        }
        switch (type) {
        case DEADLINE:
        case EVENT:
            return endDate.getLocalDateTime().compareTo(taskB.endDate.getLocalDateTime());
        default: return 0;
        }
    }

}
