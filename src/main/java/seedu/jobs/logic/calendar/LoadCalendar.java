package seedu.jobs.logic.calendar;

import seedu.jobs.commons.core.UnmodifiableObservableList;
import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.model.task.Task;
import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;

public class LoadCalendar extends BasicCommandCalendar {
    private final UnmodifiableObservableList<ReadOnlyTask> internalList;

    public LoadCalendar(com.google.api.services.calendar.Calendar inputCalendar,
            UnmodifiableObservableList<ReadOnlyTask> list) throws IllegalTimeException {
        service = inputCalendar;
        internalList = list;
        execute();
    }

    public void execute() throws IllegalTimeException {
        //to load all tasks on internal list on Google Calendar
        for (ReadOnlyTask t : internalList) {
            new AddCalendar(new Task(t), service);
        }
    }
}
