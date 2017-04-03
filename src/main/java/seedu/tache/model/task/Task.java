//@@author A0139961U
package seedu.tache.model.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.tache.commons.exceptions.IllegalValueException;
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
    private List<Date> recurCompletedList;
    private String recurDisplayDate;

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
        this.recurCompletedList = new ArrayList<Date>();
        this.recurDisplayDate = "";
    }

    public Task(Name name, Optional<DateTime> startDateTime, Optional<DateTime> endDateTime,
                    UniqueTagList tags, boolean isTimed, boolean isActive, boolean isRecurring,
                    RecurInterval interval, List<Date> recurCompletedList) {
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
        this.recurCompletedList = recurCompletedList;
        this.recurDisplayDate = "";
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDateTime(), source.getEndDateTime(), source.getTags(),
                    source.getTimedStatus(), source.getActiveStatus(), source.getRecurringStatus(),
                    source.getRecurInterval(), source.getRecurCompletedList());
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
        if (!recurDisplayDate.equals("")) {
            try {
                String time = "";
                if (startDateTime.isPresent()) {
                    time = " " + startDateTime.get().getTimeOnly();
                }
                return Optional.of(new DateTime(recurDisplayDate + time));
            } catch (IllegalValueException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return startDateTime;
    }

    public void setStartDateTime(Optional<DateTime> startDate) {
        this.startDateTime = startDate;
    }

    @Override
    public Optional<DateTime> getEndDateTime() {
        if (!recurDisplayDate.equals("")) {
            try {
                String time = "";
                if (endDateTime.isPresent()) {
                    time = " " + endDateTime.get().getTimeOnly();
                }
                return Optional.of(new DateTime(recurDisplayDate + time));
            } catch (IllegalValueException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
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
    //@@author A0139925U
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

    @Override
    public List<Date> getRecurCompletedList() {
        return this.recurCompletedList;
    }

    public void setRecurCompletedList(List<Date> recurCompletedList) {
        this.recurCompletedList = recurCompletedList;
    }

    public List<Task> getUncompletedRecurList() {
        List<Task> uncompletedRecurList = new ArrayList<Task>();
        if (startDateTime.isPresent() && isRecurring) {
            Date currentDate = new Date(startDateTime.get().getAmericanDateOnly()
                                            + " " + startDateTime.get().getTimeOnly());
            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTime(new Date());
            while (currentDate.before(calendarNow.getTime())
                    || (currentDate.getDate() == calendarNow.get(Calendar.DAY_OF_MONTH)
                    && (currentDate.getMonth() == calendarNow.get(Calendar.MONTH)
                    && (currentDate.getYear() == calendarNow.get(Calendar.YEAR))))) {
                Task temp = new Task(this);
                if (!temp.isRecurCompleted(currentDate)) {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    String tempDate = df.format(currentDate);
                    temp.setRecurDisplayDate(tempDate);
                    uncompletedRecurList.add(temp);
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                if (temp.interval == RecurInterval.DAY) {
                    calendar.add(Calendar.DATE, 1);
                    currentDate = calendar.getTime();
                } else if (temp.interval == RecurInterval.MONTH) {
                    calendar.add(Calendar.MONTH, 1);
                    currentDate = calendar.getTime();
                } else if (temp.interval == RecurInterval.YEAR) {
                    calendar.add(Calendar.YEAR, 1);
                    currentDate = calendar.getTime();
                }
            }
        }
        return uncompletedRecurList;

    }

    public boolean isRecurCompleted(Date recurCompleted) {
        DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
        for (int i = 0; i < getRecurCompletedList().size(); i++) {
            if (outputFormatter.format(getRecurCompletedList().get(i))
                                .equals(outputFormatter.format(recurCompleted))) {
                return true;
            }
        }
        return false;
    }

    public String getRecurDisplayDate() {
        return this.recurDisplayDate;
    }

    public void setRecurDisplayDate(String recurDisplayDate) {
        this.recurDisplayDate = recurDisplayDate;
    }
    //@@author
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
        this.setRecurCompletedList(replacement.getRecurCompletedList());
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
