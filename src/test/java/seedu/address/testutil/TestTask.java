package seedu.address.testutil;

import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Title;
import seedu.address.model.person.ReadOnlyTask;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title name;
    private Deadline address;
    private UniqueLabelList labels;

    public TestTask() {
        labels = new UniqueLabelList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestTask(TestTask personToCopy) {
        this.name = personToCopy.getTitle();
        this.address = personToCopy.getDeadline();
        this.labels = personToCopy.getLabels();
    }

    public void setName(Title name) {
        this.name = name;
    }

    public void setAddress(Deadline address) {
        this.address = address;
    }

    public void setLabels(UniqueLabelList labels) {
        this.labels = labels;
    }

    @Override
    public Title getTitle() {
        return name;
    }

    @Override
    public Deadline getDeadline() {
        return address;
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
        sb.append("a/" + this.getDeadline().value + " ");
        this.getLabels().asObservableList().stream().forEach(s -> sb.append("t/" + s.labelName + " "));
        return sb.toString();
    }
}
