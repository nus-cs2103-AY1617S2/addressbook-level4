package seedu.task.model.task;

import java.util.ArrayList;

import seedu.task.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field
 * values are validated.
 */
public interface ReadOnlyTask {

    Description getDescription();

    Priority getPriority();

    Timing getStartTiming();

    Timing getStartTiming(int i);

    Timing getEndTiming();

    boolean isComplete();

    boolean isRecurring();

    void setRecurring(boolean b);

    RecurringFrequency getFrequency();

    void setFrequency(RecurringFrequency frequency);

    ArrayList<RecurringTaskOccurrence> getOccurrences();

    void setStartTiming(Timing startTiming);

    void setEndTiming(Timing endTiming);

    ArrayList<Integer> getOccurrenceIndexList();

    void setOccurrenceIndexList(ArrayList<Integer> list);

    public void removeOccurrence(int i);



    /**
     * The returned TagList is a deep copy of the internal TagList, changes on
     * the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override
     * .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDescription().equals(this.getDescription())
                && other.getPriority().equals(this.getPriority())
                && other.isComplete() == this.isComplete()
                && other.getStartTiming().equals(this.getStartTiming())
                && other.getEndTiming().equals(this.getEndTiming()));
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription()).append(" Priority: ").append(getPriority()).append(" Start Timing: ")
        .append(getStartTiming()).append(" End Timing: ").append(getEndTiming()).append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }



}
