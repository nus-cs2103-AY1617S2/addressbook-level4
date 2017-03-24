package seedu.ezdo.commons.events.model;

import seedu.ezdo.commons.events.BaseEvent;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.UniqueTaskList.SortCriteria;

public class SortCriteriaChangedEvent extends BaseEvent {

    private final SortCriteria sortCriteria;

    public SortCriteriaChangedEvent(SortCriteria sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public SortCriteria getNewSortCriteria() {
        return sortCriteria;
    }
}
