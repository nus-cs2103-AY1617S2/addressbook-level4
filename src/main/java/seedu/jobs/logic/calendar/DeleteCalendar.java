package seedu.jobs.logic.calendar;

import java.io.IOException;

import seedu.jobs.model.calendar.EventCalendar;
import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.model.task.Task;

public class DeleteCalendar extends BasicCommandCalendar {
	public final EventCalendar targetEvent;
//	com.google.api.services.calendar.Calendar service;

    public DeleteCalendar(ReadOnlyTask target, 
    		com.google.api.services.calendar.Calendar inputCalendar) {
    	Task targetTask = new Task(target);
    	service = inputCalendar;
        this.targetEvent = new EventCalendar(targetTask);
        try {
			execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	public void execute() throws IOException {
		String id = retrieveID(targetEvent.getSummary());
		service.events().delete("primary", id).execute();
	}
}
