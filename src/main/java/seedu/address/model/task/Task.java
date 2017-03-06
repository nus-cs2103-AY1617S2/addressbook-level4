package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Date date;
    private Time time;
    private Description description;
    private Venue venue;
    private Priority priority;
    private boolean isFavorite;
    private Tag tag;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Date date, Time time, Description description, Tag tag, Venue venue, Priority priority, boolean isFavorite) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description;
        this.tag =  tag;
        this.priority = priority;
        this.isFavorite = isFavorite;
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDate(), source.getTime(), source.getDescription(), 
                source.getTag(), source.getVenue(), source.getPriority(), source.isFavorite());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setDate(Date date) {
        assert date != null;
        this.date = date;
    }

    @Override
    public Date getDate() {
        return date;
    }

    public void setTime(Time time) {
        assert time != null;
        this.time = time;
    }

    @Override
    public Time getTime() {
        return time;
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        assert tag != null;
        this.tag = tag;
    }
    
    @Override
    public Venue getVenue(){
        return venue;
    }
    
    public void setVenue(Venue venue){
        assert venue != null;
        this.venue = venue;
    }

    @Override
    public Priority getPriority(){
        return priority;
    }
    
    public void setPriority(Priority priority){
        assert priority != null;
        this.priority = priority;
    }
    
    @Override
    public boolean isFavorite(){
        return isFavorite;
    }
    
    public void setFavorite(boolean isFavorite){
        this.isFavorite = isFavorite;
    }
    
    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setDate(replacement.getDate());
        this.setTime(replacement.getTime());
        this.setDescription(replacement.getDescription());
        this.setTag(replacement.getTag());
        this.setVenue(replacement.getVenue());
        this.setPriority(replacement.getPriority());
        this.setFavorite(replacement.isFavorite());
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
        return Objects.hash(name, date, time, description, tag, venue, priority, isFavorite);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
