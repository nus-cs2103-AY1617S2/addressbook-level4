package seedu.task.commons.events.ui;

<<<<<<< HEAD:src/main/java/seedu/address/commons/events/ui/PersonPanelSelectionChangedEvent.java
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyTask;
=======
import seedu.task.commons.events.BaseEvent;
import seedu.task.model.task.ReadOnlyTask;
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/task/commons/events/ui/PersonPanelSelectionChangedEvent.java

/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyTask newSelection;

    public PersonPanelSelectionChangedEvent(ReadOnlyTask newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyTask getNewSelection() {
        return newSelection;
    }
}
