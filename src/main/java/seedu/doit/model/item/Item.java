package seedu.doit.model.item;

import seedu.doit.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface Item {

    Name getName();

    Priority getPriority();

    Description getDescription();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Formats the task as text, showing all contact details.
     */
    public String getAsText();

}
