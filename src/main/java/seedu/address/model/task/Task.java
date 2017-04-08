//@@author A0144885R
package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in Task Manager.
 *
 * Status should be updated everytime task's deadline changes
 */
public class Task implements ReadOnlyTask {

    private static final String DEFAULT_DESCRIPTION = "No description";

    //private IdentificationNumber ID;
    private Name name;
    private Description description;
    private Deadline deadline;
    private Status status;

    private UniqueTagList tags;

    /**
     * Name is required and must not be null
     */
    public Task(Name name, Object... params) {
        assert !CollectionUtil.isAnyNull(name);

        this.name = name;
        this.deadline = new Deadline();
        this.description = new Description(DEFAULT_DESCRIPTION);
        this.tags = new UniqueTagList();

        // Call update status immediately after creation
        this.status = new Status();

        // Optional parameters
        // ID tends to be set after Task creation,
        // so it is also included in optional params
        for (Object param : params) {
            if (param instanceof Description) {
                this.description = (Description) param;

            } else if (param instanceof Deadline) {
                this.deadline = (Deadline) param;

            } else if (param instanceof Status) {
                this.status = (Status) param;

            } else if (param instanceof UniqueTagList) {
                this.tags.mergeFrom((UniqueTagList) param);

            } else if (param instanceof Tag) {
                this.tags.add((Tag) param);
            }
        }
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDeadline(), source.getDescription(), source.getStatus(), source.getTags());
    }

    public Task(Name name2, Description description2, Deadline deadline2,
        UniqueTagList uniqueTagList) {
        // TODO Auto-generated constructor stub
    }

    /**
     * Getters and setters
     */

    /*
    public Task setID(IdentificationNumber ID) {
        this.ID = ID;
        return this;
    }

    @Override
    public IdentificationNumber getID() {
        return ID;
    }

    public boolean isIDUnassigned() {
        return ID.equals(DEFAULT_ID);
    }
    */

    public Task setName(Name name) {
        assert name != null;
        this.name = name;
        return this;
    }

    @Override
    public Name getName() {
        return name;
    }

    public Task setDeadline(Deadline deadline) {
        assert deadline != null;
        this.deadline = deadline;
        // Status value depends on Deadline value and should be updated here
        updateStatus();
        return this;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    public Task setDescription(Description description) {
        if (description == null) {
            this.description = new Description(DEFAULT_DESCRIPTION);
        } else {
            this.description = description;
        }
        return this;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public Task setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
        return this;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public Task resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setDeadline(replacement.getDeadline());
        this.setDescription(replacement.getDescription());
        this.setStatus(replacement.getStatus());
        this.setTags(replacement.getTags());
        return this;
    }

    @Override
    public Status getStatus() {
        return updateStatus();
    }

    public Task setStatus(Status status) {
        this.status = status;
        updateStatus();
        return this;
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
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, deadline, description);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
