package seedu.jobs.logic.calendar;

import java.io.IOException;

import seedu.jobs.model.calendar.EventCalendar;
import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.model.task.Task;

public class DeleteCalendar extends BasicCommandCalendar {
	public final EventCalendar targetEvent;

    public DeleteCalendar(ReadOnlyTask target) {
    	Task targetTask = new Task(target);
        this.targetEvent = new EventCalendar(targetTask);
        try {
			execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	public void execute() throws IOException {
		service.events().delete("primary", targetEvent.getId().toString()).execute();
	}
}