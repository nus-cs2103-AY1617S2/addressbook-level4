package seedu.jobs.logic.calendar;

import java.io.IOException;
import java.util.Optional;
import java.util.OptionalInt;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import seedu.jobs.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.jobs.model.calendar.EventCalendar;
import seedu.jobs.model.calendar.TimeCalendar;
import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.model.task.Task;

public class EditCalendar extends BasicCommandCalendar {
	private final EventCalendar initialEvent;
	private final EventCalendar newEvent;
	private final EditTaskDescriptor newEventDesc;
//	private final com.google.api.services.calendar.Calendar service;

    public EditCalendar(ReadOnlyTask initial, EditTaskDescriptor newTarget,
    		com.google.api.services.calendar.Calendar inputCalendar) {
        Task initialTarget = new Task(initial);
        service = inputCalendar;
    	this.initialEvent = new EventCalendar(initialTarget);
        this.newEventDesc = new EditTaskDescriptor(newTarget);
        this.newEvent = new EventCalendar(initialTarget);
        inputSet();
        try {
			execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public void execute() throws IOException {
		//find event before changing the details
		String id = retrieveID(initialEvent.getSummary());
		System.out.println("TEST");
		Event event = service.events().get("primary", id).execute();
		
		eventEditor(event);
		service.events().update("primary", id, event).execute();
	}
	
	public void inputSet() {
		if (newEventDesc.getName().isPresent())
			newEvent.setSummary(newEventDesc.getName().get().toString());
		if (newEventDesc.getDescription().isPresent())
			newEvent.setDescription(newEventDesc.getDescription().get().toString());
		if (newEventDesc.getStart().isPresent())
			newEvent.setStartTime(new TimeCalendar(newEventDesc.getStart().get()));
		if (newEventDesc.getEnd().isPresent())
			newEvent.setEndTime(new TimeCalendar(newEventDesc.getEnd().get()));
		if (newEventDesc.getPeriod().isPresent())
			newEvent.setPeriod(newEventDesc.getPeriod().get().value);
	}
	
	public void eventEditor(Event event) {
		if (newEventDesc.getName().isPresent())
			event.setSummary(newEvent.getSummary());
		if (newEventDesc.getDescription().isPresent())
			event.setDescription(newEvent.getDescription());
		if (newEventDesc.getStart().isPresent())
			event.getStart().setDateTime(new DateTime(newEvent.getStartTime().toString()));
		if (newEventDesc.getEnd().isPresent())
			event.getEnd().setDateTime(new DateTime(newEvent.getEndTime().toString()));
		if (newEventDesc.getPeriod().isPresent());
			
	}
	
}
