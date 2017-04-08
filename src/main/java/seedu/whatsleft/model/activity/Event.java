package seedu.whatsleft.model.activity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.commons.util.CollectionUtil;
import seedu.whatsleft.model.tag.UniqueTagList;

//@@author A0148038A
/**
 * Represents an event in WhatsLeft
 */
public class Event implements ReadOnlyEvent {

    private Description description;
    private StartTime startTime;
    private StartDate startDate;
    private EndTime endTime;
    private EndDate endDate;
    private Location location;

    private UniqueTagList tags;

    /**
     * Create an event in WhatsLeft
     * Description and start date must be present and not null.
     * @param Description, StartTime, StartDate, EndTime, EndDate, Location, UniqueTagList
     * @throws IllegalValueException
     */
    public Event(Description description, StartTime startTime, StartDate startDate,
            EndTime endTime, EndDate endDate, Location location, UniqueTagList tags) {

        //check description and start date are present
        assert !CollectionUtil.isAnyNull(description, startDate);

        this.description = description;
        this.startTime = startTime;
        this.startDate = startDate;
        this.endTime = endTime;
        this.endDate = endDate;
        this.location = location;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getDescription(), source.getStartTime(), source.getStartDate(),
                source.getEndTime(), source.getEndDate(), source.getLocation(), source.getTags());
    }

    public void setDescription(Description description) {
        assert description != null; // description must be present
        this.description = description;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public void setStartTime(StartTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public StartTime getStartTime() {
        return startTime;
    }

    public void setStartDate(StartDate startDate) {
        assert startDate != null; // start date must be present
        this.startDate = startDate;
    }

    @Override
    public StartDate getStartDate() {
        return startDate;
    }

    public void setEndTime(EndTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
    }

    public void setEndDate(EndDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public EndDate getEndDate() {
        return endDate;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    //@@author A0121668A

    /**
     * Checks if start Date/Time is before end Date/Time
     */
    public static boolean isValidEndDateTime(EndTime et, EndDate ed, StartTime st, StartDate sd) {
        if (sd.getValue().isAfter(ed.getValue())) {
            return false;
        }
        if (sd.getValue().equals(ed.getValue()) && st.getValue().isAfter(et.getValue())) {
            return false;
        }
        return true;
    }

    //@@author
    /**
     * Replaces this event's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this event with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyEvent replacement) {
        assert replacement != null;

        this.setDescription(replacement.getDescription());
        this.setStartTime(replacement.getStartTime());
        this.setStartDate(replacement.getStartDate());
        this.setEndTime(replacement.getEndTime());
        this.setEndDate(replacement.getEndDate());
        this.setLocation(replacement.getLocation());
        this.setTags(replacement.getTags());
    }

    //@@author A0121668A
    @Override
    public boolean isOver() {
        if (LocalDate.now().isAfter(this.getEndDate().getValue())) {
            return true;
        } else if (LocalDate.now().isBefore(this.getEndDate().getValue())) {
            return false;
        } else {
            if (LocalTime.now().isAfter(this.getEndTime().getValue())) {
                return true;
            } else {
                return false;
            }
        }
    }
    //@@author A0121668A

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEvent // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyEvent) other));
    }

    @Override
    public int hashCode() {
        // use this method for custend fields hashing instead of implementing your own
        return Objects.hash(description, startTime, startDate, endTime, endDate, location, tags);
    }

    //@@author A0148038A
    @Override
    public String toString() {
        return getAsText();
    }

    /**
     * get description shown in the card in event list panel
     *
     * @return a string that represents description of the event
     */
    @Override
    public String getDescriptionToShow() {
        return getDescription().toString();
    }

    /**
     * get duration shown in the card in event list panel
     *
     * @return a string that represents duration of the event
     */
    @Override
    public String getDurationToShow() {
        return getStartTime().toString() + " " + getStartDate().toString()
               +  " ~ " + getEndTime().toString() + " " + getEndDate().toString();
    }

    /**
     * get location shown in the card in event list panel
     *
     * @return a string that represents location of the event
     */
    @Override
    public String getLocationToShow() {
        if (getLocation().toString() != null) {
            return "@" + getLocation().toString();
        } else {
            return null;
        }
    }

    /**
     * get tags shown in the card in event list panel
     *
     * @return a list of strings that represents tags of the event
     */
    @Override
    public List<String> getTagsToShow() {
        return tags
                .asObservableList()
                .stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList());
    }
}
