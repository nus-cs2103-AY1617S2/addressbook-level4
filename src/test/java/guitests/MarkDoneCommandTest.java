package guitests;

import org.junit.Test;
import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.logic.commands.MarkDoneCommand;
import org.teamstbf.yats.logic.commands.MarkUndoneCommand;
import org.teamstbf.yats.testutil.EventBuilder;
import org.teamstbf.yats.testutil.TestEvent;

import guitests.guihandles.EventCardHandle;

// @@author A0139448U
public class MarkDoneCommandTest extends TaskManagerGuiTest {

	TestEvent[] expectedTaskList = td.getTypicalTasks();

	private void assertMarkDoneSuccess(int filteredTaskListIndex, int taskManagerIndex,
			TestEvent editedTask) {
		commandBox.runCommand("mark " + filteredTaskListIndex);
		assertResultMessage(String.format(MarkDoneCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
		commandBox.runCommand("list done");
		// confirm the new card contains the right data
		EventCardHandle editedCard = taskListPanel.navigateToEvent(editedTask.getTitle().fullName);
		assertMatching(editedTask, editedCard);
	}

	private void assertMarkUndoneSuccess(int filteredTaskListIndex, int taskManagerIndex,
			TestEvent editedTask) {
		commandBox.runCommand("unmark " + filteredTaskListIndex);

		// confirm the new card contains the right data
		EventCardHandle editedCard = taskListPanel.navigateToEvent(editedTask.getTitle().fullName);
		assertMatching(editedTask, editedCard);

		// confirm the list now contains all previous events plus the event
		// with updated details
		expectedTaskList[taskManagerIndex - 1] = editedTask;
		assertResultMessage(String.format(MarkUndoneCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
	}

	@Test
	public void mark_findThenMarkThenUnmark_success() throws Exception {
		commandBox.runCommand("find Oxygen");

		int filteredEventListIndex = 1;
		int taskManagerIndex = 3;

		TestEvent eventToEdit = expectedTaskList[taskManagerIndex - 1];
		TestEvent editedEvent = new EventBuilder(eventToEdit).withIsDone("Yes").build();

		assertMarkDoneSuccess(filteredEventListIndex, taskManagerIndex, editedEvent);

		editedEvent = new EventBuilder(eventToEdit).withIsDone("No").build();

		assertMarkUndoneSuccess(filteredEventListIndex, taskManagerIndex, editedEvent);
	}

	@Test
	public void mark_invalidEventIndex_failure() {
		commandBox.runCommand("mark 10");
		assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	}

	@Test
	public void mark_missingEventIndex_failure() {
		commandBox.runCommand("mark fff");
		assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));
	}

	@Test
	public void unmark_invalidEventIndex_failure() {
		commandBox.runCommand("unmark 10");
		assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	}

	@Test
	public void unmark_missingEventIndex_failure() {
		commandBox.runCommand("unmark fff");
		assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MarkUndoneCommand.MESSAGE_USAGE));
	}

}
