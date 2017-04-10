package onlythree.imanager.commons.events.model;

import onlythree.imanager.commons.events.BaseEvent;

//@@author A0135998H
/** Indicates the task tab in the model has changed*/
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

