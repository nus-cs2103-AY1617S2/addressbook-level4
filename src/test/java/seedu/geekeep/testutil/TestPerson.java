package seedu.geekeep.testutil;

import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.Location;
import seedu.geekeep.model.task.StartDateTime;
import seedu.geekeep.model.task.EndDateTime;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Title;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private Title title;
    private Location location;
    private StartDateTime startDateTime;
    private EndDateTime endDateTime;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestPerson(TestPerson personToCopy) {
        this.title = personToCopy.getTitle();
        this.endDateTime = personToCopy.getEndDateTime();
        this.startDateTime = personToCopy.getStartDateTime();
        this.location = personToCopy.getLocation();
        this.tags = personToCopy.getTags();
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setStartDateTime(StartDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(EndDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public EndDateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public StartDateTime getStartDateTime() {
        return startDateTime;
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
        sb.append("p/" + this.getEndDateTime().value + " ");
        sb.append("e/" + this.getStartDateTime().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
