package seedu.taskmanager.testutil;

import seedu.taskmanager.model.category.UniqueCategoryList;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.EndTime;
import seedu.taskmanager.model.task.ReadOnlyTask;
// import seedu.taskmanager.model.category.UniqueCategoryList;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.StartTime;
import seedu.taskmanager.model.task.TaskName;

// @@author A0141102H
/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private TaskName taskName;
    private StartDate startDate;
    private StartTime startTime;
    private EndDate endDate;
    private EndTime endTime;
    private UniqueCategoryList categories;
    private boolean isMarkedAsComplete;

    public TestTask() {
        categories = new UniqueCategoryList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.taskName = taskToCopy.getTaskName();
        this.startDate = taskToCopy.getStartDate();
        this.startTime = taskToCopy.getStartTime();
        this.endDate = taskToCopy.getEndDate();
        this.endTime = taskToCopy.getEndTime();
        this.categories = taskToCopy.getCategories();
    }

    public void setTaskName(TaskName taskName) {
        this.taskName = taskName;
    }

    public void setDate(StartDate startDate) {
        this.startDate = startDate;
    }

    public void setStartTime(StartTime startTime) {
        this.startTime = startTime;
    }

    public void setEndDate(EndDate endDate) {
        this.endDate = endDate;
    }

    public void setEndTime(EndTime endTime) {
        this.endTime = endTime;
    }

    public void setCategories(UniqueCategoryList categories) {
        this.categories = categories;
    }

    @Override
    public TaskName getTaskName() {
        return taskName;
    }

    @Override
    public StartDate getStartDate() {
        return startDate;
    }

    @Override
    public StartTime getStartTime() {
        return startTime;
    }

    @Override
    public EndDate getEndDate() {
        return endDate;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
    }

    @Override
    public UniqueCategoryList getCategories() {
        return categories;
    }

    @Override
    public Boolean getIsMarkedAsComplete() {
        return isMarkedAsComplete;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("ADD " + this.getTaskName().fullTaskName + " ");
        sb.append("ON " + this.getStartDate().value + " ");
        sb.append(this.getStartTime().value + " ");
        sb.append("TO " + this.getEndDate().value + " ");
        sb.append(this.getEndTime().value);
        // this.getCategories().asObservableList().stream().forEach(s ->
        // sb.append("t/" + s.categoryTaskName + " "));
        return sb.toString();
    }

}
