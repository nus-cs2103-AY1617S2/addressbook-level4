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
    private ArrayList<RecurringTaskOccurrence> occurrences;
    private boolean recurring;
    private UniqueTagList tags;
    private RecurringFrequency frequency;
    private ArrayList<Integer> occurrenceIndexList = new ArrayList<Integer>();

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.description = taskToCopy.getDescription();
        this.priority = taskToCopy.getPriority();
        this.occurrences = taskToCopy.getOccurrences();
        this.recurring = taskToCopy.isRecurring();
        this.tags = taskToCopy.getTags();
        this.frequency = taskToCopy.getFrequency();
        this.occurrenceIndexList = taskToCopy.getOccurrenceIndexList();
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setOccurrences(ArrayList<RecurringTaskOccurrence> occurrences) {
        this.occurrences = occurrences;
    }

    @Override
    public void setRecurring(boolean isRecurring) {
        this.recurring = isRecurring;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public void setFrequency(RecurringFrequency recurringFrequency) {
        this.frequency = recurringFrequency;
    }

    @Override
    public void setOccurrenceIndexList(ArrayList<Integer> list) {
        occurrenceIndexList = list;
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
    public ArrayList<RecurringTaskOccurrence> getOccurrences() {
        return occurrences;
    }

    @Override
    public boolean isRecurring() {
        return recurring;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public RecurringFrequency getFrequency() {
        return frequency;
    }

    @Override
    public ArrayList<Integer> getOccurrenceIndexList() {
        return occurrenceIndexList;
    }

    @Override
    public void setStartTiming(Timing startTiming) {
        assert startTiming != null;
        this.occurrences.get(0).setStartTiming(startTiming);
    }

    @Override
    public void setEndTiming(Timing endTiming) {
        assert endTiming != null;
        this.occurrences.get(0).setEndTiming(endTiming);
    }

    public void setComplete(boolean complete) {
        this.occurrences.get(0).setComplete(true);
    }

    @Override
    public Timing getStartTiming() {
        return getStartTiming(0);
    }

    @Override
    public Timing getStartTiming(int i) {
        return this.occurrences.get(i).getStartTiming();
    }

    @Override
    public Timing getEndTiming() {
        return this.occurrences.get(0).getEndTiming();
    }

    @Override
    public boolean isComplete() {
        return this.occurrences.get(0).isComplete();

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
        sb.append("r/" + this.getFrequency().frequency + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public void removeOccurrence(int i) {
        this.occurrences.remove(i);
    }

}
