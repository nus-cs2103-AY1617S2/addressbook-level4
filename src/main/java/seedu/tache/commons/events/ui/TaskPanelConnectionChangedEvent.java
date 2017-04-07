//@@author A0139925U
package seedu.tache.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.tache.commons.events.BaseEvent;
import seedu.tache.model.task.ReadOnlyTask;

/**
 * Represents a selection change in the Task List Panel.
 */
public class TaskPanelConnectionChangedEvent extends BaseEvent {


    private final ObservableList<ReadOnlyTask> newConnection;

    public TaskPanelConnectionChangedEvent(ObservableList<ReadOnlyTask> newConnection) {
        this.newConnection = newConnection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ObservableList<ReadOnlyTask> getNewConnection() {
        return newConnection;
    }
}
