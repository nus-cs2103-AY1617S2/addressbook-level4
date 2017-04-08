//@@author A0135739W
package seedu.onetwodo.commons.events.ui;

import seedu.onetwodo.commons.events.BaseEvent;

/**
 * An event requesting to view all tags.
 */
public class ShowTagsRequestEvent extends BaseEvent {

    private final String tagsString;

    public ShowTagsRequestEvent (String tagsString) {
        this.tagsString = tagsString;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getTagsString() {
        return tagsString;
    }

}
