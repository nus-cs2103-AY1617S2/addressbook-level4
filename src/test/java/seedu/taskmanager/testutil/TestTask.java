package seedu.taskmanager.testutil;

import java.util.Optional;

import seedu.taskmanager.model.tag.UniqueTagList;
import seedu.taskmanager.model.task.Description;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.Repeat;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.Status;
import seedu.taskmanager.model.task.Title;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    // @@author A0140032E
    private Optional<Description> description;
    private Optional<EndDate> endDate;
    private Optional<StartDate> startDate;
    private Optional<Repeat> repeat;
    // @@author
    private UniqueTagList tags;

    // @@author A0114269E
    private Status status;
    // @@author

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.title = taskToCopy.getTitle();
        this.startDate = taskToCopy.getStartDate();
        this.endDate = taskToCopy.getEndDate();
        this.description = taskToCopy.getDescription();
        this.repeat = taskToCopy.getRepeat();
        this.status = taskToCopy.getStatus();
        this.tags = taskToCopy.getTags();
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setDescription(Optional<Description> description) {
        this.description = description;
    }

    public void setEndDate(Optional<EndDate> endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(Optional<StartDate> startDate) {
        this.startDate = startDate;
    }

    // @@ author A0114269E
    public void setStatus(Status status) {
        this.status = status;
    }
    // @@ author

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public Optional<StartDate> getStartDate() {
        return startDate;
    }

    @Override
    public Optional<EndDate> getEndDate() {
        return endDate;
    }

    @Override
    public Optional<Description> getDescription() {
        return description;
    }

    // @@author A0140032E
    @Override
    public Optional<Repeat> getRepeat() {
        return repeat;
    }

    public void setRepeat(Optional<Repeat> repeat) {
        this.repeat = repeat;
    }
    // @@author

    // @@ author A0114269E
    public Status getStatus() {
        return status;
    }
    // @@ author

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTitle().value + " ");
        // @@author A0140032E
        this.getStartDate().ifPresent(x -> sb.append("s/" + x.toString()));
        this.getEndDate().ifPresent(x -> sb.append("e/" + x.toString()));
        this.getDescription().ifPresent(x -> sb.append("d/" + x.toString()));
        // @@author
        this.getTags().asObservableList().stream().forEach(s -> sb.append("#" + s.tagName + " "));
        return sb.toString();
    }

}
