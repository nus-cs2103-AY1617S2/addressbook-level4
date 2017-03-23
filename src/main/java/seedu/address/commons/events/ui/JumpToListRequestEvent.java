package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of persons
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;
    public final String targetType;
    //@@author A0110491U
    public JumpToListRequestEvent(int targetIndex, String targetType) {
        this.targetIndex = targetIndex;
        this.targetType = targetType;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
