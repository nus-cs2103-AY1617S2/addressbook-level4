package seedu.jobs.logic.calendar;

import java.io.IOException;

import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.model.task.Task;
import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;

public class EditCalendar extends BasicCommandCalendar {
    private final ReadOnlyTask initialTask;
    private final Task newTask;
    protected CalendarManager calendar;
//private final com.google.api.services.calendar.Calendar service;

    public EditCalendar(ReadOnlyTask initialTarget, Task newTarget, CalendarManager calendar)
            throws IllegalTimeException {
        this.initialTask = initialTarget;
        this.newTask = newTarget;
        this.calendar = calendar;
        try {
            execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void execute() throws IOException, IllegalTimeException {

        if (!(initialTask.getEndTime().toString().equals("") && initialTask.getStartTime().toString().equals(""))) {
            calendar.DeleteTask(initialTask);
        }

        if (!(newTask.getEndTime().toString().equals("") && newTask.getStartTime().toString().equals(""))) {
            calendar.AddTask(newTask);
        }
    }

}
