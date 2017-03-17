package seedu.taskmanager.testutil;

import seedu.taskmanager.model.category.UniqueCategoryList;
import seedu.taskmanager.model.task.Date;
import seedu.taskmanager.model.task.Deadline;
import seedu.taskmanager.model.task.TaskName;
import seedu.taskmanager.model.task.StartTime;
import seedu.taskmanager.model.task.EndTime;
import seedu.taskmanager.model.task.ReadOnlyTask;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private TaskName taskname;
    private Date date;
    private Deadline deadline;
    private StartTime starttime;
    private EndTime endtime;
    private UniqueCategoryList categories;

    public TestTask() {
        categories = new UniqueCategoryList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.taskname = taskToCopy.getTaskName();
        this.date = taskToCopy.getDate();
        this.deadline = taskToCopy.getDeadline();
        this.starttime = taskToCopy.getStartTime();
        this.endtime = taskToCopy.getEndTime();
//        this.categories = taskToCopy.getCategories();
    }

    public void setTaskName(TaskName taskname) {
        this.taskname = taskname;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }

    public void setStartTime(StartTime starttime) {
        this.starttime = starttime;
    } 

    public void setEndTime(EndTime endtime) {
    	this.endtime = endtime;
    }

/*    public void setCategories(UniqueCategoryList categories) {
        this.categories = categories;
    } */

    @Override
    public TaskName getTaskName() {
        return taskname;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    @Override
    public StartTime getStartTime() {
        return starttime;
    } 

    @Override
    public EndTime getEndTime() {
    	return endtime;
    }

    @Override
    public UniqueCategoryList getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("ADD " + this.getTaskName().fullTaskName + " ");
        sb.append("ON" + this.getDate().value + " ");
        sb.append("BY" + this.getDeadline().value + " ");
        sb.append("FROM" + this.getStartTime().value + " ");
        sb.append("TO" + this.getEndTime().value + " ");
//        this.getCategories().asObservableList().stream().forEach(s -> sb.append("t/" + s.categoryTaskName + " "));
        return sb.toString();
    }
}
