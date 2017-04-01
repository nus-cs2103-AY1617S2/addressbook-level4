package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to scroll up or down
 */
public class ScrollingEvent extends BaseEvent {

    public static final String SCROLL_UP = "scroll-up";
    public static final String SCROLL_DOWN = "scroll-down";

    public final String scrollType;

    public ScrollingEvent(String scrollType) {
        this.scrollType = scrollType;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
