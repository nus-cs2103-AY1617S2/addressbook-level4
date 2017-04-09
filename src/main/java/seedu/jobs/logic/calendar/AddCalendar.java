package seedu.jobs.logic.calendar;

import java.io.IOException;
import java.util.Arrays;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import seedu.jobs.model.calendar.EventCalendar;
import seedu.jobs.model.task.Task;

public class AddCalendar extends BasicCommandCalendar {
    private EventCalendar toAdd;


    public AddCalendar (Task inputTask,
            com.google.api.services.calendar.Calendar inputCalendar) {
        toAdd = new EventCalendar(inputTask);
        service = inputCalendar;
        try {
            this.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void execute() throws IOException {
        Event event = new Event()
                .setSummary(toAdd.getSummary().toString())
                .setDescription(toAdd.getDescription().toString());

        DateTime startDateTime = new DateTime(toAdd.getStartTime().toString());
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Singapore");
        event.setStart(start);

        DateTime endDateTime = new DateTime(toAdd.getEndTime().toString());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Singapore");
        event.setEnd(end);
        
        if (!(toAdd.getPeriod().equals("0"))) {
        	String[] recurrence = new String[] {"RRULE:FREQ=WEEKLY;COUNT=" + toAdd.getPeriod()};
            event.setRecurrence(Arrays.asList(recurrence));
        }

        String calendarId = "primary";
        event = service.events().insert(calendarId, event).execute();
    }
}
