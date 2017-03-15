package seedu.tache.model.task;

import java.util.Optional;

import seedu.tache.model.tag.UniqueTagList;

public class DetailedTask extends Task implements ReadOnlyDetailedTask {

    private Date startDate;
    private Optional<Date> endDate;
    private Time startTime;
    private Optional<Time> endTime;

    public DetailedTask(Name name, UniqueTagList tags) {
        super(name, tags);
    }

    public DetailedTask(Name name, Date startDate, Date endDate, Time startTime, Time endTime, UniqueTagList tags) {
        super(name, tags);
        this.startDate = startDate;
        this.endDate = Optional.ofNullable(endDate);
        this.startTime = startTime;
        this.endTime = Optional.ofNullable(endTime);
    }

    /**
     * Creates a copy of the given ReadOnlyDetailedTask.
     */
    public DetailedTask(ReadOnlyDetailedTask source) {
        this(source.getName(), source.getStartDate(), source.getEndDate(), source.getStartTime(), source.getEndTime(),
             source.getTags());
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate.orElse(getStartDate());
    }

    public void setEndDate(Date endDate) {
        this.endDate = Optional.ofNullable(endDate);
    }

    @Override
    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    @Override
    public Time getEndTime() {
        return endTime.orElse(getStartTime());
    }

    public void setEndTime(Time endTime) {
        this.endTime = Optional.ofNullable(endTime);
    }

    /**
     * Updates this detailed task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyDetailedTask replacement) {
        assert replacement != null;
        this.setName(replacement.getName());
        this.setEndDate(replacement.getEndDate());
        this.setStartDate(replacement.getStartDate());
        this.setStartTime(replacement.getStartTime());
        this.setEndTime(replacement.getEndTime());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean isSameStateAs(ReadOnlyTask other) {
        // TODO Auto-generated method stub
        return ReadOnlyDetailedTask.super.isSameStateAs(other);
    }

    @Override
    public String getAsText() {
        // TODO Auto-generated method stub
        return ReadOnlyDetailedTask.super.getAsText();
    }

}
