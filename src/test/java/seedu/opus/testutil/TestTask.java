package seedu.opus.testutil;

import java.util.Optional;

import seedu.opus.model.tag.UniqueTagList;
import seedu.opus.model.task.DateTime;
import seedu.opus.model.task.Name;
import seedu.opus.model.task.Note;
import seedu.opus.model.task.Priority;
import seedu.opus.model.task.ReadOnlyTask;
import seedu.opus.model.task.Status;


/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    //@@author A0126345J
    private Name name;
    private Note note;
    private Status status;
    private Priority priority;
    private DateTime startTime;
    private DateTime endTime;
    private UniqueTagList tags;
    //@@author

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        //@@author A0124368A
        this.name = taskToCopy.getName();
        this.priority = taskToCopy.getPriority().orElse(null);
        this.status = taskToCopy.getStatus();
        this.note = taskToCopy.getNote().orElse(null);
        this.startTime = taskToCopy.getStartTime().orElse(null);
        this.endTime = taskToCopy.getEndTime().orElse(null);
        this.tags = taskToCopy.getTags();
        //@@author A0124368A
    }

    //@@author A0126345J
    public void setName(Name name) {
        this.name = name;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }
    //@@author

    //@@author A0124368A
    @Override
    public Optional<Priority> getPriority() {
        return Optional.ofNullable(priority);
    }
    //@@author

    @Override
    public Status getStatus() {
        return status;
    }

    //@@author A0124368A
    @Override
    public Optional<Note> getNote() {
        return Optional.ofNullable(note);
    }

    @Override
    public Optional<DateTime> getStartTime() {
        return Optional.ofNullable(startTime);
    }

    @Override
    public Optional<DateTime> getEndTime() {
        return Optional.ofNullable(endTime);
    }
    //@@author

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    //@@author A0124368A
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");

        if (this.getNote().isPresent()) {
            sb.append("n/" + this.getNote().get().toString() + " ");
        }

        if (this.getPriority().isPresent()) {
            sb.append("p/" + this.getPriority().get().toString());
        }

        sb.append("s/" + this.getStatus().value + " ");

        if (this.getStartTime().isPresent()) {
            sb.append("b/" + this.getStartTime().get().toString() + " ");
        }

        if (this.getEndTime().isPresent()) {
            sb.append("e/" + this.getEndTime().get().toString() + " ");
        }

        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
    //@@author
}
