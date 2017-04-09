//@@author A0139925U
package seedu.tache.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.tache.commons.events.BaseEvent;
import seedu.tache.model.task.ReadOnlyTask;

/**
 * Represents a selection change in the Task List Panel
 */
public class PopulateRecurringGhostTaskEvent extends BaseEvent {


    private final ObservableList<ReadOnlyTask> allUncompletedRecurringGhostTasks;
    private final ObservableList<ReadOnlyTask> allCompletedRecurringGhostTasks;

    public PopulateRecurringGhostTaskEvent(ObservableList<ReadOnlyTask> allUncompletedRecurringGhostTasks,
                                    ObservableList<ReadOnlyTask> allCompletedRecurringGhostTasks) {
        this.allUncompletedRecurringGhostTasks = allUncompletedRecurringGhostTasks;
        this.allCompletedRecurringGhostTasks = allCompletedRecurringGhostTasks;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ObservableList<ReadOnlyTask> getAllUncompletedRecurringGhostTasks() {
        return allUncompletedRecurringGhostTasks;
    }

    public ObservableList<ReadOnlyTask> getAllCompletedRecurringGhostTasks() {
        return allCompletedRecurringGhostTasks;
    }
}
