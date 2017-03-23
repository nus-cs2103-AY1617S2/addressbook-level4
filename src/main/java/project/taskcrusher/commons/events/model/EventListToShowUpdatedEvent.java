package project.taskcrusher.commons.events.model;

import project.taskcrusher.commons.events.BaseEvent;

public class EventListToShowUpdatedEvent extends BaseEvent {

    public final boolean eventListToShowEmpty;

    public EventListToShowUpdatedEvent(boolean eventListToShowEmpty) {
        this.eventListToShowEmpty = eventListToShowEmpty;
    }

    @Override
    public String toString() {
        return "";
    }

}
