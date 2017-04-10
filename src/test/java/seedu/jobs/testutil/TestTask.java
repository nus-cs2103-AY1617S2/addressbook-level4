package seedu.jobs.testutil;

import seedu.jobs.model.tag.UniqueTagList;
import seedu.jobs.model.task.Description;
import seedu.jobs.model.task.Name;
import seedu.jobs.model.task.Period;
import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.model.task.Time;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Time startTime;
    private Time endTime;
    private Description description;
    private UniqueTagList tags;
    private boolean status;
    private Period period;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.startTime = taskToCopy.getStartTime();
        this.endTime = taskToCopy.getEndTime();
        this.description = taskToCopy.getDescription();
        this.status = taskToCopy.isCompleted();
        this.tags = taskToCopy.getTags();
        this.period = taskToCopy.getPeriod();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    @Override
    public Name getName() {
        return name;
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
    public Description getDescription() {
        return description;
    }

    @Override
    public boolean isCompleted() {
        return status;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public Period getPeriod() {
        return period;
    }
    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("start/" + this.getStartTime().value + " ");
        sb.append("end/" + this.getEndTime().value + " ");
        sb.append("desc/" + this.getDescription().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("tag/" + s.tagName + " "));
        sb.append("recur/" + this.getPeriod().toString() + " ");
        return sb.toString();
    }

    @Override
    public void markComplete() {
        this.status = true;
    }

}
