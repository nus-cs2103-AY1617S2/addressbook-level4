package seedu.toluist.commons.events.ui;

import seedu.toluist.commons.events.BaseEvent;

/**
 * An event notifying that the state in UiStore has been changed
 */
public class UiStoreChangedEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
