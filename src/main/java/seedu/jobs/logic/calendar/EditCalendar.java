package seedu.jobs.logic.calendar;

import java.io.IOException;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import seedu.jobs.model.calendar.EventCalendar;
import seedu.jobs.model.task.Task;

public class EditCalendar extends BasicCommandCalendar {
	private final EventCalendar initialEvent;
	private final EventCalendar newEvent;
	

    public EditCalendar(Task initialTarget, Task newTarget) {
        this.initialEvent = new EventCalendar(initialTarget);
        this.newEvent = new EventCalendar(newTarget);
    }
    
    
	public void execute() throws IOException {
		//find event before changing the details
		Event event = service.events().get("primary", initialEvent.getId().toString()).execute();
		event.setSummary(newEvent.getSummary());
		event.setDescription(newEvent.getDescription());
		event.getStart().setDateTime(new DateTime(newEvent.getStartTime().toString()));
		event.getEnd().setDateTime(new DateTime(newEvent.getEndTime().toString()));
		event.setId(newEvent.getId().toString());
	}
	
}