package seedu.jobs.logic.calendar;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import seedu.jobs.model.calendar.EventCalendar;
import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;

import java.io.IOException;
import java.util.List;

public abstract class BasicCommandCalendar {
    
    protected static com.google.api.services.calendar.Calendar service;

    public abstract void execute() throws IOException, IllegalTimeException;	
    
    public String retrieveID (EventCalendar target) throws IOException {
    	String id = "";
    	String pageToken = null;
    	do {
    		Events events = service.events().list("primary").setPageToken(pageToken).execute();
    		List<Event> items = events.getItems();
    		for (Event event : items) {
    			if (event.getSummary().equals(target.getSummary())) {
    				id = event.getId();
    			}
    		}
    		pageToken = events.getNextPageToken();
    	} while (pageToken != null);
    	return id;
    }
}
