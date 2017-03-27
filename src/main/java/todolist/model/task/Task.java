package todolist.model.task;

import java.util.Objects;
import java.util.Optional;

import todolist.model.tag.UniqueTagList;

/**
 * Represents a Task in the to-do list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    public static final char FLOAT_CHAR = 'f';
    public static final char DEADLINE_CHAR = 'd';
    public static final char EVENT_CHAR = 'e';

    private Title title;
    private Venue venue;
    private StartTime startTime;
    private EndTime endTime;
    private Description description;
    private UrgencyLevel urgencyLevel;

    private Category category;
    private boolean isCompleted;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Title title, Venue venue, StartTime startTime, EndTime endTime,
            UrgencyLevel urgencyLevel, Description description, UniqueTagList tags) {
        this.title = title;
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.urgencyLevel = urgencyLevel;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.category = sortCategory();
    }

    private boolean isValidTime(StartTime startTime, EndTime endTime) {
        return !(startTime != null && endTime != null && startTime.getTimeValue().isAfter(endTime.getTimeValue()));
    }

    //@@A0122017Y
    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTitle(),
                source.getVenue().orElse(null),
                source.getStartTime().orElse(null),
                source.getEndTime().orElse(null),
                source.getUrgencyLevel().orElse(null),
                source.getDescription().orElse(null),
                source.getTags());
    }

    private boolean isDeadlineTask() {
        return this.endTime != null && startTime == null;
    }

    private boolean isEventTask() {
        return this.endTime != null && startTime != null;
    }

    private boolean isFloatingTask() {
        return this.endTime == null;
    }

    private Category sortCategory() {
        if (isDeadlineTask()) {
            return Category.DEADLINE;
        } else if (isEventTask()) {
            return Category.EVENT;
        } else {
            return Category.FLOAT;
        }
    }

    @Override
    public Category getTaskCategory() {
        this.category = sortCategory();
        return this.category;
    }

    //@@

    //@@ A0143648Y
    @Override
    public Character getTaskChar() {
        if (isDeadlineTask()) {
            return DEADLINE_CHAR;
        } else if (isEventTask()) {
            return EVENT_CHAR;
        } else {
            return FLOAT_CHAR;
        }
    }
    //@@

    public void setTitle(Title name) {
        assert name != null;
        this.title = name;
    }

    @Override
    public Title getTitle() {
        return title;
    }
    //@@author A0122017Y
    public void setEndTime(EndTime endTime) {
        this.endTime = endTime;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public void setStartTime(StartTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public Optional<Venue> getVenue() {
        return Optional.ofNullable(this.venue);
    }

    @Override
    public Optional<UrgencyLevel> getUrgencyLevel() {
        return Optional.ofNullable(this.urgencyLevel);
    }

    @Override
    public Optional<StartTime> getStartTime() {
        return Optional.ofNullable(this.startTime);
    }

    @Override
    public Optional<EndTime> getEndTime() {
        return Optional.ofNullable(this.endTime);
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    @Override
    public Optional<Description> getDescription() {
        return Optional.ofNullable(this.description);
    }

    public void setDescription(Description description) {
        this.description = description;
    }


    /**
     * Replaces this Task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this Task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;
        this.setTitle(replacement.getTitle());
        this.setStartTime(replacement.getStartTime().orElse(null));
        this.setEndTime(replacement.getEndTime().orElse(null));
        this.setVenue(replacement.getVenue().orElse(null));
        this.setDescription(replacement.getDescription().orElse(null));
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, venue, endTime, description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
