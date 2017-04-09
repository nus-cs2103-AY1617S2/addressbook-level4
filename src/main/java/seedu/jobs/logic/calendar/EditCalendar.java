package seedu.jobs.logic.calendar;

import java.io.IOException;

import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.model.task.Task;

public class EditCalendar extends BasicCommandCalendar {
	private final ReadOnlyTask initialTask;
	private final Task newTask;
	protected CalendarManager calendar;
//	private final com.google.api.services.calendar.Calendar service;

    public EditCalendar(ReadOnlyTask initialTarget, Task newTarget, CalendarManager calendar) {
    	System.out.println("editcalendar");
    	this.initialTask = initialTarget;
        this.newTask = newTarget;
        this.calendar = calendar;
        try {
			execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public void execute() throws IOException {
		System.out.println("execute");
		if(!(initialTask.getEndTime().toString()=="" && initialTask.getStartTime().toString()=="")) {
			System.out.println("test");
			calendar.DeleteTask(initialTask);
        }

        if(!(newTask.getEndTime().toString()=="" && newTask.getStartTime().toString()=="")) {
        	calendar.AddTask(newTask);
        }
	}
	
	
}
