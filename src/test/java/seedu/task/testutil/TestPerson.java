package seedu.task.testutil;

import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.CompletionStatus;
import seedu.task.model.task.EndDate;
import seedu.task.model.task.Name;
import seedu.task.model.task.StartDate;
import seedu.task.model.task.ReadOnlyTask;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private Name name;
    private CompletionStatus address;
    private EndDate email;
    private StartDate phone;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestPerson(TestPerson personToCopy) {
        this.name = personToCopy.getName();
        this.phone = personToCopy.getStartDate();
        this.email = personToCopy.getEmail();
        this.address = personToCopy.getCompletionStatus();
        this.tags = personToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setAddress(CompletionStatus address) {
        this.address = address;
    }

    public void setEmail(EndDate email) {
        this.email = email;
    }

    public void setPhone(StartDate phone) {
        this.phone = phone;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public StartDate getStartDate() {
        return phone;
    }

    @Override
    public EndDate getEmail() {
        return email;
    }

    @Override
    public CompletionStatus getCompletionStatus() {
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
        sb.append("add " + this.getName().fullName + " ");
        sb.append("a/" + this.getCompletionStatus().value + " ");
        sb.append("p/" + this.getStartDate().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
