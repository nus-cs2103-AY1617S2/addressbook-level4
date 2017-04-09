//@@author A0139961U
package seedu.tache.model.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.commons.util.CollectionUtil;
import seedu.tache.model.recurstate.RecurState;
import seedu.tache.model.recurstate.RecurState.RecurInterval;
import seedu.tache.model.tag.UniqueTagList;

/**
 * Represents a Task in the task Manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Optional<DateTime> startDateTime;
    private Optional<DateTime> endDateTime;
    private UniqueTagList tags;
    private boolean isActive;
    private RecurState recurState;

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
        this.recurState = new RecurState();
    }

    public Task(Name name, Optional<DateTime> startDateTime, Optional<DateTime> endDateTime,
                    UniqueTagList tags, boolean isActive, RecurInterval interval, List<Date> recurCompletedList) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.isActive = isActive;
        this.recurState = new RecurState(interval, recurCompletedList);
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDateTime(), source.getEndDateTime(), source.getTags(),
                    source.getActiveStatus(), source.getRecurState().getRecurInterval(),
                    source.getRecurState().getRecurCompletedList());
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
        if (recurState.isGhostRecurring()) {
            try {
                String time = "";
                if (startDateTime.isPresent()) {
                    time = " " + startDateTime.get().getTimeOnly();
                }
                return Optional.of(new DateTime(recurState.getRecurDisplayDate() + time));
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
        if (recurState.isGhostRecurring()) {
            try {
                String time = "";
                if (endDateTime.isPresent()) {
                    time = " " + endDateTime.get().getTimeOnly();
                }
                return Optional.of(new DateTime(recurState.getRecurDisplayDate() + time));
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
        if (startDateTime.isPresent() || endDateTime.isPresent()) {
            return true;
        } else {
            return false;
        }
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
    public RecurState getRecurState() {
        return recurState;
    }

    public void setRecurState(RecurState recurState) {
        this.recurState = recurState;
    }

    @Override
    public List<Task> getCompletedRecurList() {
        assert startDateTime.isPresent();
        assert endDateTime.isPresent();

        List<Task> completedRecurList = new ArrayList<Task>();
        List<Date> completedRecurDates = this.recurState.getCompletedRecurDates(startDateTime.get(),
                                                                                    endDateTime.get(), null);
        for (int i = 0; i < completedRecurDates.size(); i++) {
            Task temp = new Task(this);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String tempDate = df.format(completedRecurDates.get(i));
            temp.getRecurState().setRecurDisplayDate(tempDate);
            temp.setActiveStatus(false);
            completedRecurList.add(temp);
        }

        return completedRecurList;
    }

    @Override
    public List<Task> getUncompletedRecurList() {
        assert startDateTime.isPresent();
        assert endDateTime.isPresent();

        List<Task> uncompletedRecurList = new ArrayList<Task>();
        List<Date> uncompletedRecurDates = this.recurState.getUncompletedRecurDates(startDateTime.get(),
                                                                                    endDateTime.get(), null);
        for (int i = 0; i < uncompletedRecurDates.size(); i++) {
            Task temp = new Task(this);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String tempDate = df.format(uncompletedRecurDates.get(i));
            temp.getRecurState().setRecurDisplayDate(tempDate);
            uncompletedRecurList.add(temp);
        }

        return uncompletedRecurList;
    }

    @Override
    public List<Task> getUncompletedRecurList(Date filterEndDate) {
        assert startDateTime.isPresent();
        assert endDateTime.isPresent();

        List<Task> uncompletedRecurList = new ArrayList<Task>();
        List<Date> uncompletedRecurDates = this.recurState.getUncompletedRecurDates(startDateTime.get(),
                                                                                    endDateTime.get(), filterEndDate);
        for (int i = 0; i < uncompletedRecurDates.size(); i++) {
            Task temp = new Task(this);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String tempDate = df.format(uncompletedRecurDates.get(i));
            temp.getRecurState().setRecurDisplayDate(tempDate);
            uncompletedRecurList.add(temp);
        }

        return uncompletedRecurList;
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
        this.setActiveStatus(replacement.getActiveStatus());
        this.setRecurState(replacement.getRecurState());
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (this.startDateTime.isPresent() && this.endDateTime.isPresent()) {
            if ((this.startDateTime.get().getDate().before(date) ||
                    sdf.format(this.startDateTime.get().getDate()).equals(sdf.format(date))) &&
                    this.endDateTime.get().getDate().after(date) ||
                    sdf.format(this.endDateTime.get().getDate()).equals(sdf.format(date))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Comparator use to sort task based on endDate
     */
    public static Comparator<Task> taskDateComparator = new Comparator<Task>() {

        public int compare(Task task1, Task task2) {
            Date lastComparableDate = new Date(0);
            int result = 0;
            //ascending order
            if (!task1.getEndDateTime().isPresent() && task2.getEndDateTime().isPresent()) {
                result = lastComparableDate.compareTo(task2.getEndDateTime().get().getDate());
                lastComparableDate = task2.getEndDateTime().get().getDate();
            }
            if (task1.getEndDateTime().isPresent() && !task2.getEndDateTime().isPresent()) {
                result = task1.getEndDateTime().get().getDate().compareTo(lastComparableDate);
                lastComparableDate = task1.getEndDateTime().get().getDate();
            }
            if (task1.getEndDateTime().isPresent() && task2.getEndDateTime().isPresent()) {
                return task1.getEndDateTime().get().getDate().compareTo(task2.getEndDateTime().get().getDate());
            }
            return (result);
        }

    };
    //@@author
}
