package seedu.jobs.logic.calendar;

import java.io.IOException;
import java.util.List;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import seedu.jobs.model.calendar.EventCalendar;
import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;

public abstract class BasicCommandCalendar {

    protected static com.google.api.services.calendar.Calendar service;
    public abstract void execute() throws IOException, IllegalTimeException;

    public String retrieveID(EventCalendar target) throws IOException {
        String id = "";
        String pageToken = null;
        do {
            Events events = service.events().list("primary").setPageToken(pageToken).execute();
            List<Event> items = events.getItems();
            for (Event event : items) {
                if (event.getSummary().equals(target.getSummary()) &&
                        isEqualStartTime(event, target) &&
                        isEqualEndTime(event, target)) {
                    id = event.getId();
                }
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
        return id;
    }

    public boolean isEqualStartTime (Event event, EventCalendar target) {
        return event.getStart().getDateTime().toString().substring(0, 19).equals
                (target.getStartTime().toString().toString().substring(0, 19));
    }

    public boolean isEqualEndTime (Event event, EventCalendar target) {
        return event.getEnd().getDateTime().toString().substring(0, 19).equals
                (target.getEndTime().toString().toString().substring(0, 19));
    }
}
