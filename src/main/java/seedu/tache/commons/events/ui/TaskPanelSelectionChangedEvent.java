package seedu.tache.commons.events.ui;

import seedu.tache.commons.events.BaseEvent;

/**
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {



        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

        return newSelection;
    }
}
