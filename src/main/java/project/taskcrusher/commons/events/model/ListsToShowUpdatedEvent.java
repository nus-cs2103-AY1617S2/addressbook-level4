package project.taskcrusher.commons.events.model;

import project.taskcrusher.commons.events.BaseEvent;

//@@author A0127737X
/**
 * Used to notify the UI about the number of items in each list so that it can display them to the user.
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
