package seedu.todolist.commons.events.model;

import seedu.todolist.commons.events.BaseEvent;

//@@author A0144240W
public class ViewListChangedEvent extends BaseEvent {
    public final String typeOfListView;

    public ViewListChangedEvent(String typeOfListView) {
        this.typeOfListView = typeOfListView;
    }

    @Override
    public String toString() {
        return typeOfListView + " tasks listed event";
    }

}

