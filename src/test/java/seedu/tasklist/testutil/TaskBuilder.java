package seedu.tasklist.testutil;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 *
 */
public abstract class TaskBuilder {

    public abstract TaskBuilder withName(String name) throws IllegalValueException;

    public abstract TaskBuilder withTags(String ... tags) throws IllegalValueException;

    public abstract TaskBuilder withComment(String comment) throws IllegalValueException;

    public abstract TaskBuilder withStatus(Boolean completed) throws IllegalValueException;

    public abstract TaskBuilder withPriority(String priority) throws IllegalValueException;

    public abstract TestTask build();
}
