package seedu.task.testutil;

import java.util.ArrayList;

import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.Priority;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.RecurringFrequency;
import seedu.task.model.task.RecurringTaskOccurrence;
import seedu.task.model.task.Timing;

// @@author A0163559U
/**
 * A mutable Task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description description;
    private Priority priority;
    private Timing startDate;
    private Timing endDate;
    private boolean complete;

    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.description = taskToCopy.getDescription();
        this.priority = taskToCopy.getPriority();
        this.complete = taskToCopy.isComplete();
        this.startDate = taskToCopy.getStartTiming();
        this.endDate = taskToCopy.getEndTiming();
        this.tags = taskToCopy.getTags();
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setStartDate(Timing startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Timing endDate) {
        this.endDate = endDate;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
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
    public Timing getStartTiming(int i) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Timing getEndTiming() {
        return endDate;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    // TODO add complete instance variable?
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
    public boolean isRecurring() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public RecurringFrequency getFrequency() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<RecurringTaskOccurrence> getOccurrences() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setStartTiming(Timing startTiming) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setEndTiming(Timing endTiming) {
        // TODO Auto-generated method stub

    }

    @Override
    public ArrayList<Integer> getOccurrenceIndexList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setOccurrenceIndexList(ArrayList<Integer> list) {
        // TODO Auto-generated method stub

    }

    //    public void setFrequency(Timing timing) {
    //        // TODO Auto-generated method stub
    //
    //    }

}
// @@author
