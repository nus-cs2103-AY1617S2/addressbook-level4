package seedu.task.model.task;

import java.util.Optional;

import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.UniqueTagList;

/**
 * Stores the details to edit the task with. Each non-empty field value will replace the
 * corresponding field value of the task.
 */

public class EditTaskDescriptor {
    private Optional<Description> description = Optional.empty();
    private Optional<Priority> priority = Optional.empty();
    private Optional<Timing> startTiming = Optional.empty();
    private Optional<Timing> endTiming = Optional.empty();
    private Optional<Boolean> recurring = Optional.empty();
    private Optional<UniqueTagList> tags = Optional.empty();
    private Optional<RecurringFrequency> frequency = Optional.empty();


    public EditTaskDescriptor() {}

    public EditTaskDescriptor(EditTaskDescriptor toCopy) {
        this.description = toCopy.getDescription();
        this.priority = toCopy.getPriority();
        this.startTiming = toCopy.getStartTiming();
        this.endTiming = toCopy.getEndTiming();
        this.tags = toCopy.getTags();
        this.recurring = toCopy.isRecurring();
        this.frequency = toCopy.getFrequency();
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyPresent(this.description, this.priority,
                this.startTiming, this.endTiming, this.tags, this.frequency);
    }

    public void setDescription(Optional<Description> description) {
        assert description != null;
        this.description = description;
    }

    public Optional<Description> getDescription() {
        return description;
    }

    public void setPriority(Optional<Priority> priority) {
        assert priority != null;
        this.priority = priority;
    }

    public Optional<Priority> getPriority() {
        return priority;
    }

    public void setStartTiming(Optional<Timing> startTiming) {
        assert startTiming != null;
        this.startTiming = startTiming;
    }

    public Optional<Timing> getStartTiming() {
        return startTiming;
    }

    public void setEndTiming(Optional<Timing> endTiming) {
        assert endTiming != null;
        this.endTiming = endTiming;
    }

    public Optional<Timing> getEndTiming() {
        return endTiming;
    }


    public void setTags(Optional<UniqueTagList> tags) {
        assert tags != null;
        this.tags = tags;
    }

    public Optional<UniqueTagList> getTags() {
        return tags;
    }

    public void setRecurring(Optional<Boolean> recurring) {
        assert recurring != null;
        this.recurring = recurring;
    }

    public Optional<Boolean> isRecurring() {
        return recurring;
    }

    public Optional<RecurringFrequency> getFrequency() {
        return frequency;
    }

    public void setFrequency(Optional<RecurringFrequency> frequency) {
        this.frequency = frequency;
    }
}
