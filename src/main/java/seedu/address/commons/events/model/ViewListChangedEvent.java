package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

//@@author A0135998H
public class ViewListChangedEvent extends BaseEvent {

    // TODO change name listView
    public final String listView;

    // TODO change parameter name typeOfListView
    public ViewListChangedEvent(String typeOfListView) {
        listView = typeOfListView;
    }

    @Override
    public String toString() {
        return listView + "tasks listed event";
    }
}
