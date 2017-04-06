package project.taskcrusher.commons.events.model;

import project.taskcrusher.commons.events.BaseEvent;
import project.taskcrusher.model.ReadOnlyUserInbox;

/** Indicates the AddressBook in the model has changed*/
public class UserInboxChangedEvent extends BaseEvent {

    public final ReadOnlyUserInbox data;

    public UserInboxChangedEvent(ReadOnlyUserInbox data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of events " + data.getEventList().size() +
                ", number of tags " + data.getTagList().size();
    }
}
