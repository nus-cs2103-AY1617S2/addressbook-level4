package project.taskcrusher.commons.events.model;

import project.taskcrusher.commons.events.BaseEvent;

//@@author A0127737X
/**
 * Used to signal UserInboxPanel when one or both of the lists to show is/are empty.
 */
public class ListsToShowUpdatedEvent extends BaseEvent {

    public final int eventCount;
    public final int taskCount;

    public ListsToShowUpdatedEvent(int eventCount, int taskCount) {
        this.eventCount = eventCount;
        this.taskCount = taskCount;
    }

    @Override
    public String toString() {
        return "eventCount =  " + eventCount + " taskCount =  " + taskCount;
    }

}
