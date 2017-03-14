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
public class TestPerson implements ReadOnlyActivity {

    private Description name;
    private Location address;
    private Email email;
    private Priority phone;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestPerson(TestPerson personToCopy) {
        this.name = personToCopy.getDescription();
        this.phone = personToCopy.getPriority();
        this.email = personToCopy.getEmail();
        this.address = personToCopy.getLocation();
        this.tags = personToCopy.getTags();
    }

    public void setName(Description name) {
        this.name = name;
    }

    public void setAddress(Location address) {
        this.address = address;
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
        return name;
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
        return address;
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
        sb.append("a/" + this.getLocation().value + " ");
        sb.append("p/" + this.getPriority().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
