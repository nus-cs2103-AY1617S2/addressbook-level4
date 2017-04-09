package seedu.jobs.commons.events.model;

import seedu.jobs.commons.core.UnmodifiableObservableList;
import seedu.jobs.commons.events.BaseEvent;
import seedu.jobs.model.task.ReadOnlyTask;

public class UndoCommandEvent extends BaseEvent {

    private UnmodifiableObservableList<ReadOnlyTask> filteredTaskList;

    public UndoCommandEvent(UnmodifiableObservableList<ReadOnlyTask> filteredTaskList) {
        this.filteredTaskList = filteredTaskList;
    }

    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return filteredTaskList;
    }

    @Override
    public String toString() {
        return "Undo changes to calendar";
    }

}
