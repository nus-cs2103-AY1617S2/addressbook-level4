package seedu.tache.model.task;

import seedu.tache.model.tag.UniqueTagList;

public class DetailedTask extends Task implements ReadOnlyDetailedTask {

    private Date startDate;
    private Date endDate;
    private Time time;
    private Duration duration;

    public DetailedTask(Name name, UniqueTagList tags) {
        super(name, tags);
    }

    public DetailedTask(Name name, Date startDate, Date endDate, Time time, Duration duration, UniqueTagList tags) {
        super(name, tags);
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.duration = duration;
    }

    /**
     * Creates a copy of the given ReadOnlyDetailedTask.
     */
    public DetailedTask(ReadOnlyDetailedTask source) {
        this(source.getName(), source.getStartDate(), source.getEndDate(), source.getTime(), source.getDuration(),
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
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * Updates this detailed task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyDetailedTask replacement) {
        assert replacement != null;
        this.setName(replacement.getName());
        this.setDuration(replacement.getDuration());
        this.setEndDate(replacement.getEndDate());
        this.setStartDate(replacement.getStartDate());
        this.setTime(replacement.getTime());
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
