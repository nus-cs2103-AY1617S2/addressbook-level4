package todolist.testutil;

import java.util.Optional;

import todolist.model.tag.UniqueTagList;
import todolist.model.task.Description;
import todolist.model.task.EndTime;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.StartTime;
import todolist.model.task.Title;
import todolist.model.task.UrgencyLevel;
import todolist.model.task.Venue;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    public static final char FLOAT_CHAR = 'f';
    public static final char DEADLINE_CHAR = 'd';
    public static final char EVENT_CHAR = 'e';

    private Title title;
    private Optional<Venue> venue;
    private Optional<StartTime> startTime;
    private Optional<EndTime> endTime;
    private Optional<UrgencyLevel> urgencyLevel;
    private Optional<Description> description;
    private UniqueTagList tags;
    private Category category;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.title = taskToCopy.getTitle();
        this.venue = taskToCopy.getVenue();
        this.startTime = taskToCopy.getStartTime();
        this.endTime = taskToCopy.getEndTime();
        this.urgencyLevel = taskToCopy.getUrgencyLevel();
        this.description = taskToCopy.getDescription();
        this.tags = taskToCopy.getTags();
        this.category = sortCategory();
    }

    // @@author A0122017Y
    public void setTitle(Title title) {
        this.title = title;
    }

    public void setStartTime(StartTime starttime) {
        this.startTime = Optional.of(starttime);
        updateCategory();
    }

    public void setEndTime(EndTime endtime) {
        this.endTime = Optional.of(endtime);
        updateCategory();
    }

    public void setVenue(Venue venue) {
        this.venue = Optional.of(venue);
    }

    public void setUrgencyLevel(UrgencyLevel urgencyLevel) {
        this.urgencyLevel = Optional.of(urgencyLevel);
    }

    public void setDescription(Description description) {
        this.description = Optional.of(description);
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public Category getTaskCategory() {
        return category;
    }

    @Override
    public Optional<StartTime> getStartTime() {
        return startTime;
    }

    @Override
    public Optional<EndTime> getEndTime() {
        return endTime;
    }

    @Override
    public Optional<Venue> getVenue() {
        return venue;
    }

    @Override
    public Optional<Description> getDescription() {
        return description;
    }

    @Override
    public Optional<UrgencyLevel> getUrgencyLevel() {
        return urgencyLevel;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    // @@author A0110791M
    private boolean isDeadlineTask() {
        return this.endTime != null && startTime == null;
    }

    private boolean isEventTask() {
        return this.endTime != null && startTime != null;
    }

    private boolean isFloatingTask() {
        return this.endTime == null;
    }

    private void updateCategory() {
        this.category = sortCategory();
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

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTitle().title + " ");
        if (this.getVenue().isPresent()) {
            sb.append("/venue " + this.getVenue().get() + " ");
        }
        if (this.getStartTime().isPresent()) {
            sb.append("/from " + this.getStartTime().get() + " ");
        }
        if (this.getEndTime().isPresent()) {
            sb.append("/to " + this.getEndTime().get() + " ");
        }
        if (this.getUrgencyLevel().isPresent()) {
            sb.append("/level " + this.getUrgencyLevel().get() + " ");
        }
        if (this.getDescription().isPresent()) {
            sb.append("/description " + this.getDescription().get() + " ");
        }
        this.getTags().asObservableList().stream().forEach(s -> sb.append("#" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public Character getTaskChar() {
        if (startTime.isPresent()) {
            return EVENT_CHAR;
        } else if (endTime.isPresent()) {
            return DEADLINE_CHAR;
        } else {
            return FLOAT_CHAR;
        }
    }
}
