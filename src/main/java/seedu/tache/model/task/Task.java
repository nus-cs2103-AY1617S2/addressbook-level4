//@@author A0139961U
package seedu.tache.model.task;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import seedu.tache.commons.util.CollectionUtil;
import seedu.tache.model.tag.UniqueTagList;

/**
 * Represents a Task in the task Manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    public enum RecurInterval { NONE, DAY, WEEK, MONTH, YEAR };

    private Name name;
    private Optional<DateTime> startDateTime;
    private Optional<DateTime> endDateTime;
    private UniqueTagList tags;
    private boolean isActive;
    private boolean isTimed;
    private boolean isRecurring;
    private RecurInterval interval;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.startDateTime = Optional.empty();
        this.endDateTime = Optional.empty();
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isActive = true;
        this.isTimed = false;
        this.isRecurring = false;
        this.interval = RecurInterval.NONE;
    }

    public Task(Name name, Optional<DateTime> startDateTime, Optional<DateTime> endDateTime,
                    UniqueTagList tags, boolean isTimed, boolean isActive, boolean isRecurring,
                    RecurInterval interval) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.isActive = isActive;
        if (startDateTime.isPresent() || endDateTime.isPresent()) {
            this.isTimed = true;
        } else {
            this.isTimed = false;
        }
        this.isRecurring = isRecurring;
        this.interval = interval;
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDateTime(), source.getEndDateTime(), source.getTags(),
                    source.getTimedStatus(), source.getActiveStatus(), source.getRecurringStatus(),
                    source.getRecurInterval());
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Optional<DateTime> getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Optional<DateTime> startDate) {
        this.startDateTime = startDate;
    }

    @Override
    public Optional<DateTime> getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Optional<DateTime> endDate) {
        this.endDateTime = endDate;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    //@@author A0142255M
    @Override
    public boolean getTimedStatus() {
        return isTimed;
    }

    public void setTimedStatus(boolean isTimed) {
        this.isTimed = isTimed;
    }

    //@@author A0139961U
    @Override
    public boolean getActiveStatus() {
        return isActive;
    }

    public void setActiveStatus(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean getRecurringStatus() {
        return isRecurring;
    }

    public void setRecurringStatus(boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    @Override
    public RecurInterval getRecurInterval() {
        return interval;
    }

    public void setRecurInterval(RecurInterval interval) {
        this.interval = interval;
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;
        this.setName(replacement.getName());
        this.setStartDateTime(replacement.getStartDateTime());
        this.setEndDateTime(replacement.getEndDateTime());
        this.setTags(replacement.getTags());
        this.setTimedStatus(replacement.getTimedStatus());
        this.setActiveStatus(replacement.getActiveStatus());
        this.setRecurringStatus(replacement.getRecurringStatus());
        this.setRecurInterval(replacement.getRecurInterval());
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
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    //@@author A0139961U
    /**
     * Returns true if this task is within the given date
     * (StartDate is before @code date and EndDate is after @code date)
     */
    public boolean isWithinDate(Date date) {
        if (this.startDateTime.isPresent() && this.endDateTime.isPresent()) {
            if (this.startDateTime.get().getDate().before(date) &&
                    this.endDateTime.get().getDate().after(date)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
