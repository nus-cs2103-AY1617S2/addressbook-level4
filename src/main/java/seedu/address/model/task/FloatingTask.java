package seedu.address.model.task;

import java.util.Optional;

import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager without deadline or starting time. not
 * null, field values are validated.
 */
public class FloatingTask extends Task {
    public FloatingTask(Name name, UniqueTagList tags, boolean isDone, boolean manualToday) {
        super(name, tags, isDone, manualToday);
    }

    public FloatingTask(ReadOnlyTask source) {
        super(source);
    }

    @Override
    public String getTaskDateTime() {
        return "";
    }

    @Override
    public String getTaskAbsoluteDateTime() {
        return "";
    }

    // @@author A0093999Y
    @Override
    public Optional<DateTime> getDeadline() {
        return Optional.empty();
    }

    @Override
    public Optional<DateTime> getStartingTime() {
        return Optional.empty();
    }

    @Override
    public boolean isToday() {
        return manualToday;
    }
}
