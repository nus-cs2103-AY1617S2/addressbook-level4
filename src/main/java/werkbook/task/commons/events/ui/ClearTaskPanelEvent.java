package werkbook.task.commons.events.ui;

import werkbook.task.commons.events.BaseEvent;

/**
 * Clears the Task List Panel
 */
public class ClearTaskPanelEvent extends BaseEvent {

    public ClearTaskPanelEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
