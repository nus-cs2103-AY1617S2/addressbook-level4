package seedu.address.testutil;

import seedu.address.model.person.Description;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.person.ReadOnlyActivity;
import seedu.address.model.tag.UniqueTagList;

/**
 * A mutable activity object. For testing only.
 */
public class TestActivity implements ReadOnlyActivity {

    private Description description;
    private Location location;
    private Priority priority;
    private UniqueTagList tags;

    public TestActivity() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code activityToCopy}.
     */
    public TestActivity(TestActivity activityToCopy) {
        this.description = activityToCopy.getDescription();
        this.priority = activityToCopy.getPriority();
        this.location = activityToCopy.getLocation();
        this.tags = activityToCopy.getTags();
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Priority getPriority() {
        return priority;
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
        sb.append("add " + this.getDescription().description + " ");
        sb.append("l/" + this.getLocation().value + " ");
        sb.append("p/" + this.getPriority().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
