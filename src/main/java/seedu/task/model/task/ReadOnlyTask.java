package seedu.task.model.task;

import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;

public interface ReadOnlyTask {

    Title getTitle();
    Deadline getDeadline();
    Priority getPriority();
    Instruction getInstruction();

    public final String TASK_NAME_COMPLETED = "completed";
    public final String TASK_NAME_FLOATING = "floating";
    public final String TASK_NAME_NON_FLOATING = "non-floating";

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if this task is not completed yet.
     */
    public boolean isCompleted();

    /**
     * Returns true if this task is of type floating.
     */
    public boolean isFloating();

    /**
     * Mark this task as completed.
     */
    public void setAsCompleted();

    /**
     * Mark this task as incompleted.
     */
    public void setAsIncompleted();

    /**
     * Returns true if the given TagList coincides with this task's TagList.
     */
    default boolean isTagListCoincided(UniqueTagList tags) {
        UniqueTagList tagList = getTags();
        for (Tag tag : tags) {
            if (tagList.contains(tag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle()) // state checks here onwards
                && other.getDeadline().equals(this.getDeadline())
                && other.getPriority().equals(this.getPriority())
                && other.getInstruction().equals(this.getInstruction()));
    }

    /**
     * Formats the task as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Date: ")
                .append(getDeadline())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Instruction: ")
                .append(getInstruction())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a string representing the task's displayed task list.
     */
    default String getDisplayListName() {
        String listName = TASK_NAME_NON_FLOATING;
        if (isFloating()) {
            listName = TASK_NAME_FLOATING;
        }
        if (isCompleted()) {
            listName = TASK_NAME_COMPLETED;
        }
        return listName;
    }
}
