package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in Task Manager
 */
public class Task implements ReadOnlyTask {

    private static final String DEFAULT_DESCRIPTION = "";
    private static final IdentificationNumber DEFAULT_ID = IdentificationNumber.ZERO;

    private IdentificationNumber ID;
    private Name name;
    private Description description;
    private Deadline deadline;

    private UniqueTagList tags;

    /**
     * Name, ID, deadline are required and must not be null
     */
    public Task(Name name, Deadline deadline, Object... params) {
        assert !CollectionUtil.isAnyNull(name, deadline);

        this.ID = DEFAULT_ID;
        this.name = name;
        this.deadline = deadline;
        this.description = new Description(DEFAULT_DESCRIPTION);
        this.tags = new UniqueTagList();

        // Optional parameters
        // ID tends to be set after Task creation,
        // so it is also included in optional params
        for (Object param : params) {
            if (param instanceof Description) {
                this.description = (Description) param;

            } else if (param instanceof UniqueTagList) {
                this.tags.mergeFrom((UniqueTagList) param);

            } else if (param instanceof Tag) {
                this.tags.add((Tag) param);

            } else if (param instanceof IdentificationNumber) {
                this.ID = (IdentificationNumber) param;
            }
        }
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDeadline(), source.getID(), source.getDescription(), source.getTags());
    }

    public Task(Name name2, IdentificationNumber identificationNumber, Description description2, Deadline deadline2,
        UniqueTagList uniqueTagList) {
        // TODO Auto-generated constructor stub
    }

    /**
     * Getters and setters
     */
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

        this.setID(replacement.getID());
        this.setName(replacement.getName());
        this.setDeadline(replacement.getDeadline());
        this.setDescription(replacement.getDescription());
        this.setTags(replacement.getTags());
        return this;
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
