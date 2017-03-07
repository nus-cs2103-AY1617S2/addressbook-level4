package seedu.geekeep.model.task;

public class Task implements ReadOnlyTask {
    private Title title;
    private Location location;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private boolean isDone = false;

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Task(ReadOnlyTask source) {
       this(source.getTitle(), source.getLocation(), source.getStartDateTime(), source.getEndDateTime());
    }

    public Task(Title title, Location location, DateTime startDateTime, DateTime endDateTime) {
        this.title = title;
        this.location = location;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }


    @Override
    public DateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public DateTime getStartDateTime() {
        return startDateTime;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    public boolean isDone() {
        return isDone;
    }

    /**
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setTitle(replacement.getTitle());
        this.setLocation(replacement.getLocation());
        this.setStartDateTime(replacement.getStartDateTime());
        this.setEndDateTime(replacement.getEndDateTime());
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setTitle(Title title) {
        this.title = title;
    }


}
