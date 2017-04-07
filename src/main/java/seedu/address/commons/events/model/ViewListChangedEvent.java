package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

//@@author A0135998H
public class ViewListChangedEvent extends BaseEvent {

    public final String typeOfListView;

    public ViewListChangedEvent(String typeOfListView) {
        this.typeOfListView = typeOfListView;
    }

    public String getTypeOfListView() {
        return typeOfListView;
    }

    @Override
    public String toString() {
        return typeOfListView + " tasks listed event";
    }
}

