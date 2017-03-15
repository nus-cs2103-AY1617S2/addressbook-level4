package seedu.address.model.task;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Stores the details to edit the person with. Each non-empty field value will replace the
 * corresponding field value of the person.
 */

public class EditTaskDescriptor {
    private Optional<Description> description = Optional.empty();
    private Optional<Priority> priority = Optional.empty();
    private Optional<TaskDate> startDate = Optional.empty();
    private Optional<TaskDate> endDate = Optional.empty();
    private Optional<UniqueTagList> tags = Optional.empty();

    public EditTaskDescriptor() {}

    public EditTaskDescriptor(EditTaskDescriptor toCopy) {
        this.description = toCopy.getDescription();
        this.priority = toCopy.getPriority();
        this.startDate = toCopy.getStartDate();
        this.endDate = toCopy.getEndDate();
        this.tags = toCopy.getTags();
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyPresent(this.description, this.priority,
                                      this.startDate, this.endDate, this.tags);
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

    public void setStartDate(Optional<TaskDate> startDate) {
        assert startDate != null;
        this.startDate = startDate;
    }

    public Optional<TaskDate> getStartDate() {
        return startDate;
    }

    public void setEndDate(Optional<TaskDate> endDate) {
        assert endDate != null;
        this.endDate = endDate;
    }

    public Optional<TaskDate> getEndDate() {
        return endDate;
    }


    public void setTags(Optional<UniqueTagList> tags) {
        assert tags != null;
        this.tags = tags;
    }

    public Optional<UniqueTagList> getTags() {
        return tags;
    }
}
