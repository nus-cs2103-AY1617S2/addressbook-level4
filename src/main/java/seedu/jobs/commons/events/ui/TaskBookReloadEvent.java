package seedu.jobs.commons.events.ui;

import seedu.jobs.commons.events.BaseEvent;

public class TaskBookReloadEvent extends BaseEvent {

    @Override
    public String toString() {
        return "Reinitializing model manager";
    }

}
