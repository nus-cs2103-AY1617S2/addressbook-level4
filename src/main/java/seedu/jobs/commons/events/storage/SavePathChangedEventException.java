package seedu.jobs.commons.events.storage;

import seedu.jobs.commons.events.BaseEvent;

public class SavePathChangedEventException extends BaseEvent{

    @Override
    public String toString() {
        return "Invalid file path";
    }

}
