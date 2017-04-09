//@@author A0138907W
package seedu.ezdo.commons.events.model;

import seedu.ezdo.commons.events.BaseEvent;

/**
 * Indicates that the IsSortedAscending variable in the model has changed.
 */
public class IsSortedAscendingChangedEvent extends BaseEvent {

    private final Boolean isSortedAscending;

    /**
     * Creates a IsSortedAscendingChangedEvent using the given isSortedAscending value.
     */
    public IsSortedAscendingChangedEvent(Boolean isSortedAscending) {
        this.isSortedAscending = isSortedAscending;
    }

    /**
     * Returns the simple name of IsSortedAscendingChangedEvent.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    /**
     * Returns the new isSortedAscending state.
     */
    public Boolean getNewIsSortedAscending() {
        return isSortedAscending;
    }

}
