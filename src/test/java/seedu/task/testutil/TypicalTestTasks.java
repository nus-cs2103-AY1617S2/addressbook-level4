package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

//@@author A0146757R
public class TypicalTestTasks {

	public TestTask apples, cereals, yam, zoo;

	public TypicalTestTasks() {
		try {
			apples = new TaskBuilder().withTaskName("Deliver apples").withTaskDate("120217").withTaskStartTime("1000")
					.withTaskEndTime("1200").withTaskDescription("Deliver to Crescent Road.").build();
			cereals = new TaskBuilder().withTaskName("Buy cereals").withTaskDate("020217").withTaskStartTime("0800")
					.withTaskEndTime("1000").withTaskDescription("Look for promo cereals.").build();

			// Manually added
			yam = new TaskBuilder().withTaskName("Grow yam").withTaskDate("100217").withTaskStartTime("0700")
					.withTaskEndTime("1700").withTaskDescription("Buy fertilizers.").build();
			zoo = new TaskBuilder().withTaskName("Visit zoo").withTaskDate("030217").withTaskStartTime("0800")
					.withTaskEndTime("1700").withTaskDescription("Bring Jesse along.").build();
		} catch (IllegalValueException e) {
			e.printStackTrace();
			assert false : "not possible";
		}
	}

	public static void loadTaskManagerWithSampleData(TaskManager ab) {
		for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
			try {
				ab.addJobTask(new Task(task));
			} catch (UniqueTaskList.DuplicateTaskException e) {
				assert false : "not possible";
			}
		}
	}

	public TestTask[] getTypicalTasks() {
		return new TestTask[] { apples, cereals, yam, zoo };
	}

	public TaskManager getTypicalTaskManager() {
		TaskManager ab = new TaskManager();
		loadTaskManagerWithSampleData(ab);
		return ab;
	}
}
// @@author
