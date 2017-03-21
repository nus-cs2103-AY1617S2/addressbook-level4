package seedu.taskboss.commons.events.ui;

import seedu.taskboss.commons.events.BaseEvent;
import seedu.taskboss.model.task.ReadOnlyTask;

/**
 * Represents a viewing change in the TaskBoss List Panel
 */
public class TaskPanelViewingChangedEvent extends BaseEvent {


    private final ReadOnlyTask newViewing;

    public TaskPanelViewingChangedEvent(ReadOnlyTask newViewing) {
        this.newViewing = newViewing;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyTask getNewViewing() {
        return newViewing;
    }
}
