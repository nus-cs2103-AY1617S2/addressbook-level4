package t15b1.taskcrusher.commons.events.model;

import t15b1.taskcrusher.commons.events.BaseEvent;
import t15b1.taskcrusher.model.ReadOnlyUserInbox;

/** Indicates the AddressBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyUserInbox data;

    public AddressBookChangedEvent(ReadOnlyUserInbox data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
