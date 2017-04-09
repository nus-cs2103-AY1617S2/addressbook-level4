package seedu.jobs.logic.calendar;

import java.io.IOException;

import seedu.jobs.model.calendar.EventCalendar;
import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.model.task.Task;
import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;

public class DeleteCalendar extends BasicCommandCalendar {
    public final EventCalendar targetEvent;
//com.google.api.services.calendar.Calendar service;

    public DeleteCalendar(ReadOnlyTask target,
            om.google.api.services.calendar.Calendar inputCalendar) throws IllegalTimeException {
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
        String id = retrieveID(targetEvent);
        service.events().delete("primary", id).execute();
    }
}
