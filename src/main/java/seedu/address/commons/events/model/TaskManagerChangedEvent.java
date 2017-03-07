package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
<<<<<<< HEAD
import seedu.address.model.ReadOnlyTaskManager;

/** Indicates the TaskManager in the model has changed*/
=======
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTaskManager;

/** Indicates the AddressBook in the model has changed*/
>>>>>>> parent of 9b5fb6b... test
public class TaskManagerChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;

    public TaskManagerChangedEvent(ReadOnlyTaskManager data) {
        this.data = data;
    }

    @Override
    public String toString() {
<<<<<<< HEAD
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
=======
        return "number of persons " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
>>>>>>> parent of 9b5fb6b... test
    }
}
