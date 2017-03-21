package seedu.address.model.person;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

public class Event implements ReadOnlyEvent {

    private Description description;
    private FromDate fromdate;
    private ToDate todate;
    private StartTime starttime;
    private EndTime endtime;
    private Location location;

    private UniqueTagList tags;

    /**
     * Description must be present, FromDate must be present.
     */
    public Event(Description description, FromDate fromdate, ToDate todate,
            StartTime starttime, EndTime endtime, Location location, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, fromdate, tags);
        this.description = description;
        this.fromdate = fromdate;
        this.todate = todate;
        this.starttime = starttime;
        this.endtime = endtime;
        this.location = location;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getDescription(), source.getFromDate(), source.getToDate(), source.getStartTime(),
                source.getEndTime(), source.getLocation(), source.getTags());
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public void setFromDate(FromDate fromdate) {
        assert fromdate != null;
        this.fromdate = fromdate;
    }

    @Override
    public FromDate getFromDate() {
        return fromdate;
    }

    public void setToDate(ToDate todate) {
        this.todate = todate;
    }

    @Override
    public ToDate getToDate() {
        return todate;
    }

    public void setStartTime(StartTime starttime) {
        //can be null
        this.starttime = starttime;
    }

    @Override
    public StartTime getStartTime() {
        return starttime;
    }

    public void setEndTime(EndTime endtime) {
        //can be null
        this.endtime = endtime;
    }

    @Override
    public EndTime getEndTime() {
        return endtime;
    }
    public void setLocation(Location location) {
        //can be null
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
        this.setFromDate(replacement.getFromDate());
        this.setToDate(replacement.getToDate());
        this.setStartTime(replacement.getStartTime());
        this.setEndTime(replacement.getEndTime());
        this.setLocation(replacement.getLocation());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEvent // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyEvent) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, fromdate, todate, starttime, endtime, location, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
