package seedu.taskmanager.testutil;

import seedu.taskmanager.model.tag.UniqueTagList;
import seedu.taskmanager.model.task.Description;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.Title;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.ReadOnlyTask;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private Title title;
    private Description description;
    private EndDate endDate;
    private StartDate startDate;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestPerson(TestPerson personToCopy) {
        this.title = personToCopy.getTitle();
        this.startDate = personToCopy.getStartDate();
        this.endDate = personToCopy.getEndDate();
        this.description = personToCopy.getDescription();
        this.tags = personToCopy.getTags();
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setEndDate(EndDate endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public StartDate getStartDate() {
        return startDate;
    }

    @Override
    public EndDate getEndDate() {
        return endDate;
    }

    @Override
    public Description getDescription() {
        return description;
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
        sb.append("add " + this.getTitle().value + " ");
        sb.append("a/" + this.getDescription().value + " ");
        sb.append("p/" + this.getStartDate().value + " ");
        sb.append("e/" + this.getEndDate().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
