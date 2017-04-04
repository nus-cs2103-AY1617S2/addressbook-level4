//@@author A0139925U
package seedu.tache.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.tache.commons.events.BaseEvent;
import seedu.tache.model.task.ReadOnlyTask;

/**
 * Represents a selection change in the Task List Panel
 */
public class PopulateRecurringGhostTaskEvent extends BaseEvent {


    private final ObservableList<ReadOnlyTask> allRecurringGhostTasks;

    public PopulateRecurringGhostTaskEvent(ObservableList<ReadOnlyTask> allRecurringGhostTasks) {
        this.allRecurringGhostTasks = allRecurringGhostTasks;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ObservableList<ReadOnlyTask> getAllRecurringGhostTasks() {
        return allRecurringGhostTasks;
    }
}
