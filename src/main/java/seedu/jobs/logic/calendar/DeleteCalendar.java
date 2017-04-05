package seedu.jobs.logic.calendar;

import java.io.IOException;

import seedu.jobs.model.calendar.EventCalendar;
import seedu.jobs.model.task.Task;

public class DeleteCalendar extends BasicCommandCalendar {
	public final EventCalendar targetEvent;

    public DeleteCalendar(Task target) {
        this.targetEvent = new EventCalendar(target);
    }
    
	public void execute() throws IOException {
		service.events().delete("primary", targetEvent.getId().toString()).execute();
	}
}