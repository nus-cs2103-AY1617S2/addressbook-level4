package seedu.task.model.util;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskDate;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.TaskTime;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
	public static Task[] getSampleTasks() {
		try {
			return new Task[] { new Task(new TaskName("Buy coke"), new TaskDate("2102017"), new TaskTime("0800"),
					new TaskTime("1000"), new String("Look out for lucky draws."))

			};
		} catch (IllegalValueException e) {
			throw new AssertionError("sample data cannot be invalid", e);
		}
	}

	public static ReadOnlyTaskManager getSampleTaskManager() {
		try {
			TaskManager sampleAB = new TaskManager();
			for (Task sampleTask : getSampleTasks()) {
				sampleAB.addTask(sampleTask);
			}
			return sampleAB;
		} catch (DuplicateTaskException e) {
			throw new AssertionError("sample data cannot contain duplicate tasks", e);
		}
	}
}
