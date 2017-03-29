package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.label.Label;

public class LeftPanelSelectionChangedEvent extends BaseEvent {

    private final Label newSelection;

    public LeftPanelSelectionChangedEvent(Label newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Label getNewSelection() {
        return newSelection;
    }

}
