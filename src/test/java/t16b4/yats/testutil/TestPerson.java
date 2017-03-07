package t16b4.yats.testutil;

import t16b4.yats.model.item.Description;
import t16b4.yats.model.item.Timing;
import t16b4.yats.model.item.Title;
import t16b4.yats.model.item.Deadline;
import t16b4.yats.model.item.ReadOnlyItem;
import t16b4.yats.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyItem {

    private Title name;
    private Description address;
    private Timing email;
    private Deadline phone;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestPerson(TestPerson personToCopy) {
        this.name = personToCopy.getTitle();
        this.phone = personToCopy.getDeadline();
        this.email = personToCopy.getTiming();
        this.address = personToCopy.getDescription();
        this.tags = personToCopy.getTags();
    }

    public void setName(Title name) {
        this.name = name;
    }

    public void setAddress(Description address) {
        this.address = address;
    }

    public void setEmail(Timing email) {
        this.email = email;
    }

    public void setPhone(Deadline phone) {
        this.phone = phone;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Title getTitle() {
        return name;
    }

    @Override
    public Deadline getDeadline() {
        return phone;
    }

    @Override
    public Timing getTiming() {
        return email;
    }

    @Override
    public Description getDescription() {
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
        sb.append("add " + this.getTitle().fullName + " ");
        sb.append("a/" + this.getDescription().value + " ");
        sb.append("p/" + this.getDeadline().value + " ");
        sb.append("e/" + this.getTiming().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
