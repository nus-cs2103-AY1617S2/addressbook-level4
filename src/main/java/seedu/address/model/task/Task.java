package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.label.UniqueLabelList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Title title;
    private Deadline deadline;

    private UniqueLabelList labels;

    /**
     * Every field must be present and not null.
     */
    public Task(Title title, Deadline deadline, UniqueLabelList labels) {
        assert !CollectionUtil.isAnyNull(title, deadline, labels);
        this.title = title;
        this.deadline = deadline;
        this.labels = new UniqueLabelList(labels); // protect internal labels from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTitle(), source.getDeadline(), source.getLabels());
    }

    public void setName(Title name) {
        assert name != null;
        this.title = name;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    public void setDeadline(Deadline deadline) {
        assert deadline != null;
        this.deadline = deadline;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    @Override
    public UniqueLabelList getLabels() {
        return new UniqueLabelList(labels);
    }

    /**
     * Replaces this person's labels with the labels in the argument label list.
     */
    public void setLabels(UniqueLabelList replacement) {
        labels.setLabels(replacement);
    }

    /**
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getTitle());
        this.setDeadline(replacement.getDeadline());
        this.setLabels(replacement.getLabels());
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
        return Objects.hash(title, deadline, labels);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
