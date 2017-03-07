package seedu.taskboss.model.task;

import java.util.Objects;

import seedu.taskboss.commons.util.CollectionUtil;
import seedu.taskboss.model.category.UniqueTagList;

/**
 * Represents a Task in the TaskBoss.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private PriorityLevel priorityLevel;
    private Email email;
    private Information information;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, PriorityLevel priorityLevel, Email email, Information information, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, priorityLevel, email, information, tags);
        
        this.name = name;
        this.priorityLevel = priorityLevel;
        this.email = email;
        this.information = information;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPriorityLevel(), source.getEmail(), source.getInformation(), source.getTags());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        assert priorityLevel != null;
        this.priorityLevel = priorityLevel;
    }

    @Override
    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    public void setEmail(Email email) {
        assert email != null;
        this.email = email;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    public void setInformation(Information information) {
        assert information != null;
        this.information = information;
    }

    @Override
    public Information getInformation() {
        return information;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setPriorityLevel(replacement.getPriorityLevel());
        this.setEmail(replacement.getEmail());
        this.setInformation(replacement.getInformation());
        this.setTags(replacement.getTags());
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
        return Objects.hash(name, priorityLevel, email, information, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
