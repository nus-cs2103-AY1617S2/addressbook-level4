package seedu.task.model.task;

import java.util.Objects;

import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private StartDate startDate;
    private EndDate endDate;
    private CompletionStatus completionStatus;

    private UniqueTagList tags;

    /**
     * Only Name must be present and not null.
     */
    public Task(Name name, StartDate startDate, EndDate endDate, CompletionStatus completionStatus, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.completionStatus = completionStatus;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDate(), source.getEndDate(), source.getCompletionStatus(), source.getTags());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setStartDate(StartDate startDate) {
        assert startDate != null;
        this.startDate = startDate;
    }

    @Override
    public StartDate getStartDate() {
        return startDate;
    }

    public void setEndDate(EndDate endDate) {
        assert endDate != null;
        this.endDate = endDate;
    }

    @Override
    public EndDate getEndDate() {
        return endDate;
    }

    public void setCompletionStatus(CompletionStatus completionStatus) {
        assert completionStatus != null;
        this.completionStatus = completionStatus;
    }

    @Override
    public CompletionStatus getCompletionStatus() {
        return completionStatus;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setStartDate(replacement.getStartDate());
        this.setEndDate(replacement.getEndDate());
        this.setCompletionStatus(replacement.getCompletionStatus());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startDate, endDate, completionStatus, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
