package seedu.taskmanager.commons.events.model;

import seedu.taskmanager.commons.events.BaseEvent;
import seedu.taskmanager.model.ReadOnlyProcrastiNomore;

/** Indicates the AddressBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyProcrastiNomore data;

    public AddressBookChangedEvent(ReadOnlyProcrastiNomore data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
