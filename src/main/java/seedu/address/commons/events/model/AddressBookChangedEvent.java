package seedu.address.commons.events.model;

//import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.task.ReadOnlyTask;

/** Indicates the AddressBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;
    public UnmodifiableObservableList<ReadOnlyTask> nonFloatingTasks;
    public UnmodifiableObservableList<ReadOnlyTask> floatingTasks;
    public UnmodifiableObservableList<ReadOnlyTask> completedTasks;

    //UNDO OR REDO HERE
    public AddressBookChangedEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    public void setNonFloatingTasks(UnmodifiableObservableList<ReadOnlyTask> nonFloatingTasks) {
        this.nonFloatingTasks = nonFloatingTasks;
    }

    public void setFloatingTasks(UnmodifiableObservableList<ReadOnlyTask> floatingTasks) {
        this.floatingTasks = floatingTasks;
    }

    public void setCompletedTasks(UnmodifiableObservableList<ReadOnlyTask> completedTasks) {
        this.completedTasks = completedTasks;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
