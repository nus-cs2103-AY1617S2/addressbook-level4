package seedu.address.commons.events.ui;

import java.time.LocalDateTime;

import seedu.address.commons.core.CalendarLayout;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.util.StringUtil;

//@@author A0124377A
public class UpdateCalendarEvent extends BaseEvent {
    private LocalDateTime displayedDateTime;
    private CalendarLayout calendarLayoutMode;

    public UpdateCalendarEvent(LocalDateTime displayedDateTime, CalendarLayout calendarLayoutMode) {
        this.displayedDateTime = displayedDateTime;
        this.calendarLayoutMode = calendarLayoutMode;
    }

    @Override
    public String toString() {
        return "Setting displayed time " + this.displayedDateTime.format(StringUtil.DATE_FORMATTER)
            + " With mode: " + calendarLayoutMode;
    }

    public LocalDateTime getDisplayedDateTime() {
        return displayedDateTime;
    }

    public CalendarLayout getCalendarLayoutMode() {
        return calendarLayoutMode;
    }
}
