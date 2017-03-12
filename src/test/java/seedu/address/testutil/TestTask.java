package seedu.address.testutil;

import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Title;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private Deadline startTime;
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
        this.startTime = taskToCopy.getStartTime();
        this.deadline = taskToCopy.getDeadline();
        this.labels = taskToCopy.getLabels();
    }

    public void setTitle(Title name) {
        this.title = name;
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
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
        sb.append(" by " + this.getDeadline().toString() + " ");
        this.getLabels().asObservableList().stream().forEach(s -> sb.append("t/" + s.labelName + " "));
        return sb.toString();
    }

    @Override
    public Deadline getStartTime() {
        return startTime;
    }
}
