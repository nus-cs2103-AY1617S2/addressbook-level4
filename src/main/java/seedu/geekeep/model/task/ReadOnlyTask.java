package seedu.geekeep.model.task;

public interface ReadOnlyTask {
    DateTime getEndDateTime();
    Location getLocation();
    DateTime getStartDateTime();
    Title getTitle();

}
