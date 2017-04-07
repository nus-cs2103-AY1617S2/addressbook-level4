package seedu.task.model.task;

import java.util.Objects;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask, Comparable<ReadOnlyTask> {

    public static final String MESSAGE_TASK_CONSTRAINTS =
            "Start date/time must be earlier than end date/time";
    public static final String EVENTID_VALIDATION_REGEX = "[a-z,0-9]+";
    public static final String MESSAGE_EVENTID_CONSTRAINTS = "Event Id must only contain lowercase letters"
            + "and digits 0-9. Event ID must also be between 5-1024 characters.";

    private Name name;
    private Date startDate;
    private Date endDate;
    private Remark remark;
    private Location location;
    private boolean isDone;
    private String eventId;
    private UniqueTagList tags;

    /**
     * Name field must be present and not null.
     * @throws IllegalValueException
     */
    public Task(Name name, Date startDate, Date endDate, Remark remark,
        Location location, UniqueTagList tags, boolean isDone, String eventId) throws IllegalValueException {
        assert !CollectionUtil.isAnyNull(name, startDate, endDate, remark, location, tags);

        if (!isValidDates(startDate, endDate)) {
            throw new IllegalValueException(MESSAGE_TASK_CONSTRAINTS);
        }

        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remark = remark;
        this.location = location;
        this.isDone = isDone;
        this.eventId = eventId;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /***
     * *
     * @param startDate
     * @param endDate
     * @return true if one of two dates is null. if both not null, only returns
     *         true if startDate is strictly before endDate
     */
    private boolean isValidDates(Date startDate, Date endDate) {
        return (startDate.isNull() || endDate.isNull()) ? true
                : startDate.getDateValue().before(endDate.getDateValue());
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     * @throws IllegalValueException
     */
    public Task(ReadOnlyTask source) throws IllegalValueException {
        this(source.getName(), source.getStartDate(), source.getEndDate(), source.getRemark(),
                source.getLocation(), source.getTags(), source.isDone(), source.getEventId());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    //@@author A0140063X
    public void setStartDate(Date startDate) {
        assert startDate != null;
        this.startDate = startDate;
    }

    //@@author A0140063X
    @Override
    public Date getStartDate() {
        return startDate;
    }

    //@@author A0140063X
    public void setEndDate(Date endDate) {
        assert endDate != null;
        this.endDate = endDate;
    }

    //@@author A0140063X
    @Override
    public Date getEndDate() {
        return endDate;
    }

    //@@author
    public void setRemark(Remark remark) {
        assert remark != null;
        this.remark = remark;
    }

    @Override
    public Remark getRemark() {
        return remark;
    }

    public void setLocation(Location location) {
        assert location != null;
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return location;
    }
    //@@author A0139975J
    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }
    //@@author A0139975J
    @Override
    public boolean isDone() {
        return isDone;
    }
    //@@author
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    //@@author A0140063X
    @Override
    public String getEventId() {
        return eventId;
    }

    //@@author A0140063X
    /**
     *
     * @param eventId   Event Id to set.
     * @throws IllegalValueException    If eventId is invalid.
     */
    public void setEventId(String eventId) throws IllegalValueException {
        assert eventId != null && eventId.trim() != "";
        if (!isValidEventId(eventId)) {
            throw new IllegalValueException(MESSAGE_EVENTID_CONSTRAINTS);
        }
        this.eventId = eventId;
    }

    //@@author A0140063X
    /**
     * Checks if eventId is valid
     *
     * @param eventId   Event Id to check.
     * @return          True if valid.
     */
    private boolean isValidEventId(String eventId) {
        if ((eventId.length() < 5) || (eventId.length() > 1024)) {
            return false;
        }

        return eventId.matches(EVENTID_VALIDATION_REGEX);
    }

    //@@author
    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setStartDate(replacement.getStartDate());
        this.setEndDate(replacement.getEndDate());
        this.setRemark(replacement.getRemark());
        this.setLocation(replacement.getLocation());
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
        return Objects.hash(name, endDate, remark, location, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    //@@author A0142487Y
    @Override
    public int compareTo(ReadOnlyTask o) {
        //Same end date then compare according to names lexicographically
        if ((this.getEndDate() == null && o.getEndDate() == null)
                || (this.getEndDate().equals(o.getEndDate()))) {
            return this.getName().fullName.compareTo(o.getName().fullName);
        } else {
            if (this.getEndDate().isNull()) return 1;
            if (o.getEndDate().isNull()) return -1;
            return (Date.isBefore(this.getEndDate(), o.getEndDate())) ? -1 : 1;
        }
    }

}
