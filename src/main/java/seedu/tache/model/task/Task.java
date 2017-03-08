package seedu.tache.model.task;

import java.util.Objects;

import seedu.tache.commons.util.CollectionUtil;
<<<<<<< HEAD
import seedu.tache.model.task.FloatingTask;
import seedu.tache.model.tag.UniqueTagList;

public class Task implements FloatingTask {
	
	private Name name;
	
	private UniqueTagList tags;
	
=======
import seedu.tache.model.tag.UniqueTagList;

/**
 * Represents a Task in the task Manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private UniqueTagList tags;

>>>>>>> ImplementTaskModels
    /**
     * Every field must be present and not null.
     */
    public Task(Name name, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
<<<<<<< HEAD
     * Creates a copy of the given FloatingTask.
     */
    public Task(FloatingTask source) {
=======
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
>>>>>>> ImplementTaskModels
        this(source.getName(), source.getTags());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
<<<<<<< HEAD
     * Replaces this person's tags with the tags in the argument tag list.
=======
     * Replaces this task's tags with the tags in the argument tag list.
>>>>>>> ImplementTaskModels
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
<<<<<<< HEAD
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(FloatingTask replacement) {
=======
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
>>>>>>> ImplementTaskModels
        assert replacement != null;

        this.setName(replacement.getName());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
<<<<<<< HEAD
                || (other instanceof FloatingTask // instanceof handles nulls
                && this.isSameStateAs((FloatingTask) other));
=======
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
>>>>>>> ImplementTaskModels
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
