package t16b4.yats.commons.events.model;

import t16b4.yats.commons.events.BaseEvent;
import t16b4.yats.model.ReadOnlyTaskManager;

/** Indicates the AddressBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;

    public AddressBookChangedEvent(ReadOnlyTaskManager data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
