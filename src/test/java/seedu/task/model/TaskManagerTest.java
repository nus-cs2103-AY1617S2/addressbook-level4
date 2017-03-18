package seedu.task.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.task.model.TaskManager;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.testutil.TypicalTestTasks;

public class TaskManagerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private final TaskManager taskManager = new TaskManager();

	@Test
	public void constructor() {
		assertEquals(Collections.emptyList(), taskManager.getTaskList());
	}

	@Test
	public void resetData_null_throwsAssertionError() {
		thrown.expect(AssertionError.class);
		taskManager.resetData(null);
	}

	@Test
	public void resetData_withValidReadOnlyTaskManager_replacesData() {
		TaskManager newData = new TypicalTestTasks().getTypicalTaskManager();
		taskManager.resetData(newData);
		assertEquals(newData, taskManager);
	}

	@Test
	public void resetData_withDuplicateTasks_throwsAssertionError() {
		TypicalTestTasks td = new TypicalTestTasks();
		// Repeat td.apples twice
		List<Task> newTasks = Arrays.asList(new Task(td.apples), new Task(td.apples));
		TaskManagerStub newData = new TaskManagerStub(newTasks);

		thrown.expect(AssertionError.class);
		taskManager.resetData(newData);
	}

	/**
	 * A stub ReadOnlyTaskManager whose tasks lists can violate interface
	 * constraints.
	 */
	private static class TaskManagerStub implements ReadOnlyTaskManager {
		private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();

		TaskManagerStub(Collection<? extends ReadOnlyTask> tasks) {
			this.tasks.setAll(tasks);
		}

		@Override
		public ObservableList<ReadOnlyTask> getTaskList() {
			return tasks;
		}
	}

}
