package typetask.testutil;

import typetask.model.task.DueDate;
import typetask.model.task.Name;
import typetask.model.task.ReadOnlyTask;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private DueDate date;
    private DueDate endDate;
    private boolean isCompleted;

    public TestTask() {
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setEndDate(DueDate endDate) {
        this.endDate = endDate;
    }

    public void setDate(DueDate date) {
        this.date = date;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public Name getName() {
        return name;
    }
    @Override
    public DueDate getDate() {
        return date;
    }
    @Override
    public DueDate getEndDate() {
        return endDate;
    }
    @Override
    public boolean getIsCompleted() {
        return isCompleted;
    }
    //@@author A0139154E
    public String getIsCompletedToString() {
        if (isCompleted == true) {
            return "Yes";
        } else {
            return "No";
        }
    }
    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        return sb.toString();
    }
}
