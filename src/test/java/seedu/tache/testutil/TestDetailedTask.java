package seedu.tache.testutil;

import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.Date;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.ReadOnlyDetailedTask;
import seedu.tache.model.task.Time;

/**
 * A mutable detailed task object. For testing only.
 */
public class TestDetailedTask implements ReadOnlyDetailedTask  {

    private Name name;
    private Date startDate;
    private Date endDate;
    private Time startTime;
    private Time endTime;
    private UniqueTagList tags;

    public TestDetailedTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code detailedTaskToCopy}.
     */
    public TestDetailedTask(TestDetailedTask detailedTaskToCopy) {
        this.name = detailedTaskToCopy.getName();
        this.startDate = detailedTaskToCopy.getStartDate();
        this.endDate = detailedTaskToCopy.getEndDate();
        this.startTime = detailedTaskToCopy.getStartTime();
        this.endTime = detailedTaskToCopy.getEndTime();
        this.tags = detailedTaskToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public Time getStartTime() {
        return startTime;
    }

    @Override
    public Time getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
