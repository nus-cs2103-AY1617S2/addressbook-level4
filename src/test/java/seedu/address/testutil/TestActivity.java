package seedu.address.testutil;

import seedu.address.model.person.Description;
import seedu.address.model.person.Email;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.person.ReadOnlyActivity;
import seedu.address.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestActivity implements ReadOnlyActivity {

    private Description description;
    private Location location;
    private Email email;
    private Priority phone;
    private UniqueTagList tags;

    public TestActivity() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code activityToCopy}.
     */
    public TestActivity(TestActivity activityToCopy) {
        this.description = activityToCopy.getDescription();
        this.phone = activityToCopy.getPriority();
        this.email = activityToCopy.getEmail();
        this.location = activityToCopy.getLocation();
        this.tags = activityToCopy.getTags();
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setPhone(Priority phone) {
        this.phone = phone;
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
        return phone;
    }

    @Override
    public Email getEmail() {
        return email;
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
        sb.append("e/" + this.getEmail().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
