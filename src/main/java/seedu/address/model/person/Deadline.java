package seedu.address.model.person;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

public class Deadline implements ReadOnlyDeadline {

    private Description description;
    private ByDate bydate;
    private EndTime bytime;
    private Location location;

    private UniqueTagList tags;

    /**
     * Description must be present, ByDate must be present.
     */
    public Deadline(Description description, ByDate bydate, EndTime bytime,
            Location location, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, bydate, tags);
        this.description = description;
        this.bydate = bydate;
        this.bytime = bytime;
        this.location = location;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyDeadline.
     */
    public Deadline(ReadOnlyDeadline source) {
        this(source.getDescription(), source.getByDate(),
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

    public void setByDate(ByDate bydate) {
        assert bydate != null;
        this.bydate = bydate;
    }

    @Override
    public ByDate getByDate() {
        return bydate;
    }

    public void setEndTime(EndTime bytime) {
        //can be null
        this.bytime = bytime;
    }

    @Override
    public EndTime getEndTime() {
        return bytime;
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
     * Replaces this deadline's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this event with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyDeadline replacement) {
        assert replacement != null;

        this.setDescription(replacement.getDescription());
        this.setByDate(replacement.getByDate());
        this.setEndTime(replacement.getEndTime());
        this.setLocation(replacement.getLocation());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyDeadline // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyDeadline) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, bydate, bytime, location, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
