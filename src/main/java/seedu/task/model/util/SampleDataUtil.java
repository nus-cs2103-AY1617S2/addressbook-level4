package seedu.task.model.util;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.TaskStatus;
import seedu.task.model.task.TaskTime;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskDate;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

//@@author A0146757R
public class SampleDataUtil {
    public static Task[] getSampleTasks() {
	try {
	    return new Task[] {
		    new Task(new TaskName("Buy apple juice"), new TaskDate("010117"), new TaskTime("0800"),
			    new TaskTime("1000"), new String("Look out for lucky draws."), new TaskStatus("Ongoing"),
			    new UniqueTagList()),
		    new Task(new TaskName("Buy blueberry juice"), new TaskDate("020117"), new TaskTime("0900"),
			    new TaskTime("1001"), new String("Look out for lucky dips."), new TaskStatus("Ongoing"),
			    new UniqueTagList()),
		    new Task(new TaskName("Buy coke"), new TaskDate("030117"), new TaskTime("0930"),
			    new TaskTime("1002"), new String("Look out for traffic."), new TaskStatus("Ongoing"),
			    new UniqueTagList()),
		    new Task(new TaskName("Buy isotonic drinks"), new TaskDate("040117"), new TaskTime("1000"),
			    new TaskTime("1100"), new String("Look out for promotions."), new TaskStatus("Ongoing"),
			    new UniqueTagList()),
		    new Task(new TaskName("Buy orange juice"), new TaskDate("050117"), new TaskTime("1010"),
			    new TaskTime("1100"), new String("Look out for sweets along the way."),
			    new TaskStatus("Ongoing"), new UniqueTagList()) };

	} catch (IllegalValueException e) {
	    throw new AssertionError("sample data cannot be invalid", e);
	}
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
	try {
	    TaskManager sampleTM = new TaskManager();
	    for (Task sampleTask : getSampleTasks()) {
		sampleTM.addJobTask(sampleTask);
	    }
	    return sampleTM;
	} catch (DuplicateTaskException e) {
	    throw new AssertionError("sample data cannot contain duplicate tasks", e);
	}
    }
}
//@@author
