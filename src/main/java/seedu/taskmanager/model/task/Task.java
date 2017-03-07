package seedu.taskmanager.model.task;

import java.util.Objects;

import seedu.taskmanager.commons.util.CollectionUtil;
import seedu.taskmanager.model.category.UniqueCategoryList;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private TaskName taskName;
    private Deadline deadline;
    private EndTime endTime;
    private Date date;


//    private UniqueCategoryList categories;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName taskName, Deadline deadline, EndTime endTime, Date date/*, UniqueCategoryList categories*/) {
        assert !CollectionUtil.isAnyNull(taskName, deadline, endTime, date/*, categories*/);
        this.taskName = taskName;
        this.deadline = deadline;
        this.endTime = endTime;
        this.date = date;
//        this.categories = new UniqueCategoryList(categories); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTaskName(), source.getDeadline(), source.getEndTime(), source.getDate()/* source.getCategories()*/);
    }

    public void setTaskName(TaskName taskName) {
        assert taskName != null;
        this.taskName = taskName;
    }

    @Override
    public TaskName getTaskName() {
        return taskName;
    }

    public void setDeadline(Deadline deadline) {
        assert deadline != null;
        this.deadline = deadline;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    public void setEndTime(EndTime endTime) {
        assert endTime != null;
        this.endTime = endTime;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
    }
    
    public void setDate(Date date) {
        assert date != null;
        this.date = date;
    }

    @Override
    public Date getDate() {
        return date;
    }

//    @Override
//    public UniqueCategoryList getCategories() {
//        return new UniqueCategoryList(categories);
//    }

    /**
     * Replaces this task's categories with the categories in the argument category list.
     */
//    public void setCategories(UniqueCategoryList replacement) {
//        tags.setCategories(replacement);
//    }

    /**
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setTaskName(replacement.getTaskName());
        this.setDeadline(replacement.getDeadline());
        this.setEndTime(replacement.getEndTime());
        this.setDate(replacement.getDate());
//        this.setCategories(replacement.getCategories());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, deadline, endTime, date/*, categories*/);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
