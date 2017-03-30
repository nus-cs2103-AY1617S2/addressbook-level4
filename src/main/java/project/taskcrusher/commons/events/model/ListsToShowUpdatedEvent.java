package project.taskcrusher.commons.events.model;

import project.taskcrusher.commons.events.BaseEvent;

//@@author A0127737X
/**
 * Used to signal UserInboxPanel when one or both of the lists to show is/are empty.
 */
public class ListsToShowUpdatedEvent extends BaseEvent {

    public final boolean eventListToShowEmpty;
    public final boolean taskListToShowEmpty;

    public ListsToShowUpdatedEvent(boolean eventListToShowEmpty, boolean taskListToShowEmpty) {
        this.eventListToShowEmpty = eventListToShowEmpty;
        this.taskListToShowEmpty = taskListToShowEmpty;
    }

    @Override
    public String toString() {
        return "eventList empty =  " + eventListToShowEmpty + " taskList empty =  " + taskListToShowEmpty;
    }

}
