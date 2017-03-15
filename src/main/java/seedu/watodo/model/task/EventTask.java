package seedu.watodo.model.task;

import seedu.watodo.model.tag.UniqueTagList;

public class EventTask extends Task implements ReadOnlyTask {

    private DateTime startDateTime;
    private DateTime endDateTime;

    public EventTask(Description description, DateTime startDate, DateTime dueDate, UniqueTagList tags) {
        super(description, tags);
        this.setStartDateTime(startDate);
        this.setEndDateTime(dueDate);
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

}
