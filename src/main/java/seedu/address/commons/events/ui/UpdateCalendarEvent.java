package seedu.address.commons.events.ui;

import java.time.LocalDateTime;

import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.util.StringUtil;

//@@author A0124377A
/**
 * Indicates request to change calendar view by number of weeks ahead.
 */
public class UpdateCalendarEvent extends BaseEvent {
    private LocalDateTime nextDateTime;
    private int weeksAhead;

    public UpdateCalendarEvent(LocalDateTime currDateTime, int weeksAhead) {
        this.nextDateTime = currDateTime.plusWeeks(weeksAhead);
    }

    @Override
    public String toString() {
        return "Setting updated calendar view " + this.nextDateTime.format(StringUtil.DATE_FORMATTER)
            + " With " + weeksAhead + " week(s) ahead.";
    }

    public LocalDateTime getNextDateTime() {
        return nextDateTime;
    }

    public int getWeeksAhead() {
        return weeksAhead;
    }
}
