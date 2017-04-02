package seedu.jobs.model.task;

import seedu.jobs.model.tag.UniqueTagList;
//@@author A0130979U
public interface ReadOnlyTask {

    Name getName();

    Time getStartTime();

    Time getEndTime();

    Period getPeriod();

    Description getDescription();

    UniqueTagList getTags();

    boolean isCompleted();

    void markComplete();

    /**
     * Returns true if both have the same state. (interfaces cannot override
     * .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                        && other.getName().equals(this.getName()) // state checks here onwards
                        && other.getStartTime().equals(this.getStartTime())
                        && other.getEndTime().equals(this.getEndTime()) && other.isCompleted() == (this.isCompleted()));
    }

    /**
     * Formats the task as text, showing all the details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName()).append("\n").append("From: ").append(getStartTime())
                .append(" To: ").append(getEndTime()).append("\n").append("Status : ")
                .append((!isCompleted()) ? "Not Completed" : "Completed")
                .append("\n").append("Description: ").append(getDescription());
        return builder.toString();
    }

}
