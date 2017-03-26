package seedu.task.testutil;

import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.Priority;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.RecurringFrequency;
import seedu.task.model.task.Timing;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private Description description;
    private Timing startDate;
    private Timing endDate;
    private Priority priority;
    private boolean complete;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestPerson(TestPerson personToCopy) {
        this.description = personToCopy.getDescription();
        this.priority = personToCopy.getPriority();
        this.startDate = personToCopy.getStartTiming();
        this.endDate = personToCopy.getEndTiming();
        this.tags = personToCopy.getTags();
        this.complete = false;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setStartDate(Timing timing) {
        this.startDate = timing;
    }

    public void setEndDate(Timing timing) {
        this.endDate = timing;
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
    public Timing getStartTiming() {
        return startDate;
    }

    @Override
    public Timing getEndTiming() {
        return endDate;
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
        sb.append("p/" + this.getPriority().value + " ");
        sb.append("sd/" + this.getStartTiming().value + " ");
        sb.append("ed/" + this.getEndTiming().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public boolean isRecurring() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public RecurringFrequency getFrequency() {
        // TODO Auto-generated method stub
        return null;
    }
}
