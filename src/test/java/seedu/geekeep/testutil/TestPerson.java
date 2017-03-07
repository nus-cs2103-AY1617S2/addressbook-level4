package seedu.geekeep.testutil;

import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.Location;
import seedu.geekeep.model.task.DateTime;
import seedu.geekeep.model.task.Phone;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Title;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private Title title;
    private Location location;
    private DateTime dateTime;
    private Phone phone;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestPerson(TestPerson personToCopy) {
        this.title = personToCopy.getTitle();
        this.phone = personToCopy.getPhone();
        this.dateTime = personToCopy.getDateTime();
        this.location = personToCopy.getLocation();
        this.tags = personToCopy.getTags();
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public Phone getPhone() {
        return phone;
    }

    @Override
    public DateTime getDateTime() {
        return dateTime;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTitle().fullTitle + " ");
        sb.append("a/" + this.getLocation().value + " ");
        sb.append("p/" + this.getPhone().value + " ");
        sb.append("e/" + this.getDateTime().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
