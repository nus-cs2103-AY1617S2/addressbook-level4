package seedu.tache.testutil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.DateTime;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task.RecurInterval;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Optional<DateTime> startDateTime;
    private Optional<DateTime> endDateTime;
    private UniqueTagList tags;
    private boolean isTimed;
    private boolean isActive;
    private boolean isRecurring;
    private RecurInterval interval;

    public TestTask() {
        tags = new UniqueTagList();
        this.startDateTime = Optional.empty();
        this.endDateTime = Optional.empty();
        this.interval = RecurInterval.NONE;
        this.isTimed = false;
        this.isActive = true;
        this.isRecurring = false;
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.tags = taskToCopy.getTags();
        this.startDateTime = taskToCopy.getStartDateTime();
        this.endDateTime = taskToCopy.getEndDateTime();
        this.interval = taskToCopy.getRecurInterval();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = Optional.of(startDateTime);
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = Optional.of(endDateTime);
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Optional<DateTime> getStartDateTime() {
        // TODO Auto-generated method stub
        return startDateTime;
    }

    @Override
    public Optional<DateTime> getEndDateTime() {
        // TODO Auto-generated method stub
        return endDateTime;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    //@@author A0142255M
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        if (this.getStartDateTime().isPresent()) {
            sb.append(" from " + sdf.format(this.getStartDateTime().get().getDate()));
        }
        if (this.getEndDateTime().isPresent()) {
            sb.append(" to " + sdf.format(this.getEndDateTime().get().getDate()));
        }
        if (this.getTags().iterator().hasNext()) {
            sb.append(" t/");
        }
        this.getTags().asObservableList().stream().forEach(s -> sb.append(s.tagName + " "));
        return sb.toString();
    }

    @Override
    public boolean getTimedStatus() {
        return isTimed;
    }
    //@@author

    @Override
    public boolean getActiveStatus() {
        return isActive;
    }

    @Override
    public boolean getRecurringStatus() {
        return isRecurring;
    }

    @Override
    public RecurInterval getRecurInterval() {
        return interval;
    }

    @Override
    public boolean isWithinDate(Date date) {
        // TODO Auto-generated method stub
        return false;
    }

}
