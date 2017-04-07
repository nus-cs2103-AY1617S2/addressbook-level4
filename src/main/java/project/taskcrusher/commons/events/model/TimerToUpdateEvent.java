package project.taskcrusher.commons.events.model;

import project.taskcrusher.commons.events.BaseEvent;

public class TimerToUpdateEvent extends BaseEvent {

    public TimerToUpdateEvent () {
    }

    @Override
    public String toString() {
        return "Timer to update BEFORE the list gets filtered";
    }

}
