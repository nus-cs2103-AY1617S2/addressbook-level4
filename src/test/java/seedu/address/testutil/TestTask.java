package seedu.address.testutil;

import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Title;
import seedu.address.model.person.ReadOnlyTask;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private Deadline deadline;
    private UniqueLabelList labels;

    public TestTask() {
        labels = new UniqueLabelList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.title = taskToCopy.getTitle();
        this.deadline = taskToCopy.getDeadline();
        this.labels = taskToCopy.getLabels();
    }

    public void setTitle(Title name) {
        this.title = name;
    }

    public void setDeadline(Deadline address) {
        this.deadline = address;
    }

    public void setLabels(UniqueLabelList labels) {
        this.labels = labels;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    @Override
    public UniqueLabelList getLabels() {
        return labels;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTitle().title + " ");
        sb.append("a/" + this.getDeadline().value.toString() + " ");
        this.getLabels().asObservableList().stream().forEach(s -> sb.append("t/" + s.labelName + " "));
        return sb.toString();
    }
}
