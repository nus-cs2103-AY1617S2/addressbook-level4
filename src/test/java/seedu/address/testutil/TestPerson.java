package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Date;
import seedu.address.model.task.EndDate;
import seedu.address.model.task.Group;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.ReadOnlyPerson;

/**
 * A mutable person object. For testing only.
 */
//@@author A0164032U
public class TestPerson implements ReadOnlyPerson {

    private Name name;
    private Group group;
    private StartDate start;
    private EndDate end;
    private UniqueTagList tags;

    public TestPerson() {
        start = null;
        end = null;
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestPerson(TestPerson personToCopy) {
        this.name = personToCopy.getName();
        this.start = personToCopy.getStartDate();
        this.end = personToCopy.getEndDate();
        this.group = personToCopy.getGroup();
        this.tags = personToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    //@@ author A0164032U
    public void setStartDate(StartDate start) {
        this.start = start;
    }
    
    public void setEndDate(EndDate end) {
        this.end = end;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }

    //author A0164032U
    @Override
    public StartDate getStartDate() {
        return start;
    }
    
    @Override
    public EndDate getEndDate() {
        return end;
    }

    @Override
    public Group getGroup() {
        return group;
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
        sb.append("s/" + this.getStartDate().inputValue + " ");
        sb.append("d/" + this.getEndDate().inputValue + " ");
        sb.append("g/" + this.getGroup().value + " ");
        //this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    public boolean hasPassed() {
        return false;
    }

    public java.util.Date getEndTime() {
        return null;
    }

    public java.util.Date getStartTime() {
        return null;
    }
}
