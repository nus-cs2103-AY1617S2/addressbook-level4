package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

//@@author A0135998H
public class ViewListChangedEvent extends BaseEvent {

    public final String listView;

    public ViewListChangedEvent(String typeOfListView) {
        this.listView = typeOfListView;
    }

    @Override
    public String toString() {
        return listView + "tasks listed event";
    }
}
