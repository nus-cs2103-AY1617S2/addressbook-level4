//@@author A0142255M
package seedu.tache.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.tache.commons.events.BaseEvent;
import seedu.tache.model.task.ReadOnlyTask;

/**
 * Indicates that the filtered list is changed.
 * For example, the user types "list completed" to show all completed tasks only.
 */
public class FilteredTaskListUpdatedEvent extends BaseEvent {

    public final ObservableList<ReadOnlyTask> filteredTaskList;

    public FilteredTaskListUpdatedEvent(ObservableList<ReadOnlyTask> filteredTaskList) {
        this.filteredTaskList = filteredTaskList;
    }

    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return filteredTaskList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
