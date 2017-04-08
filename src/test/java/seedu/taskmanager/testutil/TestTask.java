package seedu.taskmanager.testutil;

import seedu.taskmanager.commons.util.DateTimeUtil;
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
    private boolean completed;
    private UniqueCategoryList categories;

    private final String EMPTY_FIELD = "EMPTY_FIELD";

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
        this.completed = taskToCopy.getIsMarkedAsComplete();
        this.categories = taskToCopy.getCategories();
    }

    public void setTaskName(TaskName taskname) {
        this.taskName = taskname;
    }

    public void setStartDate(StartDate startdate) {
        this.startDate = startdate;
    }

    public void setStartTime(StartTime starttime) {
        this.startTime = starttime;
    }

    public void setEndDate(EndDate enddate) {
        this.endDate = enddate;
    }

    public void setEndTime(EndTime endtime) {
        this.endTime = endtime;
    }

    public void setCompleted(Boolean isCompleted) {
        this.completed = isCompleted;
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
        return completed;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("ADD " + this.getTaskName().fullTaskName + " ");
        sb.append("FROM " + this.getStartDate().value + " ");
        sb.append(this.getStartTime().value + " ");
        sb.append("TO " + this.getEndDate().value + " ");
        sb.append(this.getEndTime().value + " ");
        this.getCategories().asObservableList().stream().forEach(s -> sb.append("CATEGORY " + s.categoryName + " "));
        return sb.toString();
    }

    public String getOneDayEventAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("ADD " + this.getTaskName().fullTaskName + " ");
        sb.append("ON " + this.getStartDate().value + " ");
        sb.append(this.getStartTime().value + " ");
        sb.append("TO " + this.getEndTime().value + " ");
        this.getCategories().asObservableList().stream().forEach(s -> sb.append("CATEGORY " + s.categoryName + " "));
        return sb.toString();
    }

    // @@author A0142418L
    /**
     * Checks the fields populated within the task
     *
     * @return true if task is a Event Task
     */
    @Override
    public boolean isEventTask() {
        if (!startDate.value.equals(EMPTY_FIELD) && !endDate.value.equals(EMPTY_FIELD)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks the fields populated within the task
     *
     * @return true if task is a Deadline Task
     */
    @Override
    public boolean isDeadlineTask() {
        if (startDate.value.equals(EMPTY_FIELD) && !endDate.value.equals(EMPTY_FIELD)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks the fields populated within the task
     *
     * @return true if task is a Floating Task
     */
    @Override
    public boolean isFloatingTask() {
        if (startDate.value.equals(EMPTY_FIELD) && endDate.value.equals(EMPTY_FIELD)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isWithinStartEndDuration(ReadOnlyTask t) {

        if (this.getStartDate().equals(t.getStartDate()) && this.getEndDate().equals(t.getEndDate())) {
            if ((DateTimeUtil.isDateWithin(this.getStartDate().value, t.getStartDate().value,
                    t.getEndDate().value) == 2)) {
                if ((this.getStartTime().laterThanOrEqual(t.getEndTime()))
                        || (t.getStartTime().laterThanOrEqual(this.getEndTime()))) {

                    return false;
                }
                return true;
            } else {
                return true;
            }
        } else if ((this.getEndDate().value).equals(t.getStartDate().value)) {
            if (t.getStartTime().laterThanOrEqual(this.getEndTime())) {
                return false;
            } else {
                return true;
            }
        } else if ((this.getStartDate().value).equals(t.getEndDate().value)) {
            if (this.getStartTime().laterThanOrEqual(t.getEndTime())) {
                return false;
            } else {
                return true;
            }
        }

        else if ((DateTimeUtil.isDateWithin(this.getStartDate().value, t.getStartDate().value,
                t.getEndDate().value) == 1)
                || (DateTimeUtil.isDateWithin(this.getEndDate().value, t.getStartDate().value,
                        t.getEndDate().value) == 1)
                || (DateTimeUtil.isDateWithin(t.getStartDate().value, this.getStartDate().value,
                        this.getEndDate().value) == 1)
                || (DateTimeUtil.isDateWithin(t.getEndDate().value, this.getStartDate().value,
                        this.getEndDate().value) == 1)) {
            return true;
        } else if ((DateTimeUtil.isDateWithin(this.getStartDate().value, t.getStartDate().value,
                t.getEndDate().value) == 2) && t.getEndTime().laterThanOrEqual(this.getStartTime())) {
            return true;
        } else if ((DateTimeUtil.isDateWithin(this.getEndDate().value, t.getStartDate().value,
                t.getEndDate().value) == 2) && this.getEndTime().laterThanOrEqual(t.getStartTime())) {
            return true;
        }

        return false;
    }
}
