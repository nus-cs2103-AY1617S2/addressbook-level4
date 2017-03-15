package seedu.watodo.model.task;

import seedu.watodo.model.tag.UniqueTagList;

/**
 * Represents a Floating Task in the task manager.
 * Floating tasks do not have specific deadlines and can be done any time the user is free.
 * Guarantees: details are present and not null, field values are validated.
 */
public class FloatingTask extends Task {

    /**
     * Every field must be present and not null.
     */
    public FloatingTask(Description description, UniqueTagList tags) {
        super(description,tags);
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public FloatingTask(ReadOnlyTask source) {
        this(source.getDescription(), source.getTags());
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setDescription(replacement.getDescription());
        this.setTags(replacement.getTags());
    }


}
