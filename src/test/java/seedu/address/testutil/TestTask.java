package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.IdentificationNumber;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Deadline deadline;
    private Description description;
    private IdentificationNumber id;
    private UniqueTagList tags;
    private Status status;

    public TestTask() {
        id = new IdentificationNumber();
        description = new Description();
        deadline = new Deadline();
        status = new Status();
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.deadline = taskToCopy.getDeadline();
        this.description = taskToCopy.getDescription();
        this.tags = taskToCopy.getTags();
        this.status = taskToCopy.getStatus();
        updateStatus();
    }

    public TestTask setName(Name name) {
        this.name = name;
        return this;
    }

    public TestTask setDeadline(Deadline deadline) {
        this.deadline = deadline;
        updateStatus();
        return this;
    }

    public TestTask setDescription(Description description) {
        this.description = description;
        return this;
    }

    public TestTask setID(IdentificationNumber id) {
        this.id = id;
        return this;
    }

    public TestTask setTags(UniqueTagList tags) {
        this.tags = tags;
        return this;
    }

    public TestTask setStatus(Status status) {
        this.status = status;
        updateStatus();
        return this;
    }

    @Override
    public Status getStatus() {
        return updateStatus();
    }

    public Status updateStatus() {
        String currentStatus = status.toString();
        if (currentStatus.equals(Status.DONE)) {
            // No change
            return status;
        } else {
            // Update status base on Deadline and current time
            try {
                if (deadline.isFloating()) {
                    return status = new Status(Status.FLOATING);

                } else if (deadline.isOverdue()) {
                    return status = new Status(Status.OVERDUE);

                } else if (deadline.isToday()) {
                    return status = new Status(Status.TODAY);

                } else if (deadline.isTomorrow()) {
                    return status = new Status(Status.TOMORROW);

                } else {
                    return status = new Status(Status.FUTURE);
                }
            } catch (IllegalValueException e) {
                // Impossible
                return status;
            }
        }
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    @Override
    public Description getDescription() {
        return description;
    }

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
        sb.append("add " + this.getName().toString() + " ");
        sb.append("dl/" + this.getDeadline().toString() + " ");
        sb.append("ds/" + this.getDescription().toString() + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
