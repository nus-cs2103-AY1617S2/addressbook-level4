package guitests;

import org.junit.Test;
import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.logic.commands.BatchMarkDoneCommand;
import org.teamstbf.yats.logic.commands.BatchUnmarkDoneCommand;
import org.teamstbf.yats.testutil.EventBuilder;
import org.teamstbf.yats.testutil.TestEvent;

import guitests.guihandles.EventCardHandle;

// @@author A0139448U
public class BatchMarkDoneCommandTest extends TaskManagerGuiTest{

	TestEvent[] expectedTaskList = td.getTypicalTasks();

	private void assertMarkDoneSuccess(int[] filteredEventListIndex, TestEvent[] editedTask) {
		commandBox.runCommand("mark " + filteredEventListIndex[0] + " " + filteredEventListIndex[1] + " " + filteredEventListIndex[2]);
		assertResultMessage(String.format(BatchMarkDoneCommand.MESSAGE_EDIT_TASK_SUCCESS, 3));
		commandBox.runCommand("list done");
		// confirm the new card contains the right data
		for (int i = 0; i < filteredEventListIndex.length; i++) {
			EventCardHandle editedCard = taskListPanel.navigateToEvent(editedTask[i].getTitle().fullName);
			assertMatching(editedTask[i], editedCard);
		}
	}

	private void assertMarkUndoneSuccess(int[] filteredTaskListIndex, TestEvent[] editedTask) {
		commandBox.runCommand("unmark " + filteredTaskListIndex[0] + " " + filteredTaskListIndex[1] + " " + filteredTaskListIndex[2]);

		// confirm the new card contains the right data
		for (int i = 0; i < filteredTaskListIndex.length; i++) {
			EventCardHandle editedCard = taskListPanel.navigateToEvent(editedTask[i].getTitle().fullName);
			assertMatching(editedTask[i], editedCard);
		}
		// confirm the list now contains all previous events plus the event
		// with updated details
		assertResultMessage(String.format(BatchUnmarkDoneCommand.MESSAGE_EDIT_TASK_SUCCESS, 3));
	}

	@Test
	public void batch_markDone_markUndone_success() throws Exception {

		int[] filteredEventListIndex = {1, 2, 3};
		TestEvent[] eventToEdit = {expectedTaskList[filteredEventListIndex[0] - 1], expectedTaskList[filteredEventListIndex[1] - 1], expectedTaskList[filteredEventListIndex[2] - 1]};
		TestEvent[] editedEvent = {new EventBuilder(eventToEdit[0]).withIsDone("Yes").build(), new EventBuilder(eventToEdit[1]).withIsDone("Yes").build(), new EventBuilder(eventToEdit[2]).withIsDone("Yes").build()};

		assertMarkDoneSuccess(filteredEventListIndex, editedEvent);

		TestEvent[] unmarkedEvent = {new EventBuilder(eventToEdit[0]).withIsDone("No").build(), new EventBuilder(eventToEdit[1]).withIsDone("No").build(), new EventBuilder(eventToEdit[2]).withIsDone("No").build()};

		assertMarkUndoneSuccess(filteredEventListIndex, unmarkedEvent);
	}

	@Test
	public void batchMark_invalidEventIndex_failure() {
		commandBox.runCommand("mark 1 d 3");
		assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, BatchMarkDoneCommand.MESSAGE_USAGE));
	}

	@Test
	public void batchUnmark_invalidEventIndex_failure() {
		commandBox.runCommand("mark 1 2 3");
		commandBox.runCommand("unmark 1 d 3");
		assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, BatchUnmarkDoneCommand.MESSAGE_USAGE));
	}

	@Test
	public void batchMark_missingEventIndex_failure() {
		commandBox.runCommand("mark 1 10 3");
		assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	}

	@Test
	public void batchUnmark_missingEventIndex_failure() {
		commandBox.runCommand("mark 1 2 3");
		commandBox.runCommand("unmark 1 10 3");
		assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	}

}
