package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Title getTitle();
    String getStart();
    String getEnd();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle())); // state checks here onwards
    }

    /**
     * Formats the task as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
    
    
    /**
     * Stringifies title and tags
     * @return String with title and tags
     */
    default String toStringTitleAndTagList() {
    	String titleAndTagString = getTitle().title + " ";
    	final StringBuilder builder = new StringBuilder();
    	builder.append(getTitle()).append(" ");
    	getTags().iterator().forEachRemaining(builder::append);
    	
    	titleAndTagString = builder.toString();
    	titleAndTagString = titleAndTagString.replace('[', ' ').replace(']', ' ');
    	
    	return titleAndTagString;
    }

}

