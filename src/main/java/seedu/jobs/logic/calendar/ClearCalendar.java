package seedu.jobs.logic.calendar;

import java.io.IOException;

public class ClearCalendar extends BasicCommandCalendar {


    public ClearCalendar(com.google.api.services.calendar.Calendar inputCalendar) {
        service = inputCalendar;
        try {
            execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void execute() throws IOException {
        service.calendars().clear("primary").execute();
    }
}
