package seedu.address.testutil;

import java.util.Optional;

import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Title;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private Optional<Deadline> startTime;
    private Optional<Deadline> deadline;
    private UniqueLabelList labels;
    private Boolean isCompleted;

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
        this.isCompleted = taskToCopy.isCompleted();
    }

    public void setTitle(Title name) {
        this.title = name;
    }

    public void setDeadline(Optional<Deadline> deadline) {
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
    public Optional<Deadline> getDeadline() {
        return deadline == null ? Optional.empty() : deadline;
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
        String addCommand;
        if (this.getStartTime().isPresent() && this.getDeadline().isPresent()) {
            addCommand = getAddCommandWithInterval();
        } else if (this.getDeadline().isPresent()) {
            addCommand = getAddCommandWithDeadline();
        } else {
            addCommand = getAddCommandWithoutDate();
        }
        return addCommand;
    }

    private String getAddCommandWithoutDate() {
        StringBuilder sb = new StringBuilder();
<<<<<<< HEAD
        sb.append("ADD " + this.getTitle().title + " ");
        sb.append(" BY " + this.getDeadline().toString() + " ");
        this.getLabels().asObservableList().stream().forEach(s -> sb.append("#" + s.labelName + " "));
=======
        sb.append("add " + this.getTitle().title + " ");
        this.getLabels().asObservableList().stream().forEach(s -> sb.append("t/" + s.labelName + " "));
>>>>>>> V0.2-yesha
        return sb.toString();
    }

    private String getAddCommandWithDeadline() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTitle().title + " ");
        sb.append(" by " + this.getDeadline().get().toString() + " ");
        this.getLabels().asObservableList().stream().forEach(s -> sb.append("t/" + s.labelName + " "));
        return sb.toString();
    }

    private String getAddCommandWithInterval() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTitle().title + " ");
        sb.append(" from " + this.getStartTime().get().toString() + " ");
        sb.append(" to " + this.getDeadline().get().toString() + " ");
        this.getLabels().asObservableList().stream().forEach(s -> sb.append("t/" + s.labelName + " "));
        return sb.toString();
    }

    public void setStartTime(Optional<Deadline> startTime) {
        this.startTime = startTime;
    }

    @Override
    public Optional<Deadline> getStartTime() {
        return startTime == null ? Optional.empty() : startTime;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public Boolean isCompleted() {
        return isCompleted;
    }
}
