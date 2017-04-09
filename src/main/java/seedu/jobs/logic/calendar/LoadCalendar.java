package seedu.jobs.logic.calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
		System.out.println("executing");
		execute();
	}

	public void execute() throws IllegalTimeException {
		//to load all tasks on internal list on Google Calendar
        for (ReadOnlyTask t : internalList) {
        	System.out.println(t.getName().toString());
        	new AddCalendar(new Task(t), service);
        }
	}
}
