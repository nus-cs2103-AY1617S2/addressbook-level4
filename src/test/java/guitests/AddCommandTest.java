package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

	@Test
	public void add() {
		// add one task
		TestTask[] currentList = this.td.getTypicalTasks();
		TestTask personToAdd = this.td.hoon;
		assertAddSuccess(personToAdd, currentList);
		currentList = TestUtil.addTasksToList(currentList, personToAdd);

		// add another task
		personToAdd = this.td.ida;
		assertAddSuccess(personToAdd, currentList);
		currentList = TestUtil.addTasksToList(currentList, personToAdd);

		// add duplicate task
		this.commandBox.runCommand(this.td.hoon.getAddCommand());
		assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
		assertTrue(this.taskListPanel.isListMatching(currentList));

		// add to empty list
		this.commandBox.runCommand("clear");
		assertAddSuccess(this.td.alice);

		// invalid command
		this.commandBox.runCommand("adds Johnny");
		assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
	}

	private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) {
		this.commandBox.runCommand(personToAdd.getAddCommand());

		// confirm the new card contains the right data

		TaskCardHandle addedCard = this.taskListPanel.navigateToTask(personToAdd.getName().fullName);
		assertMatching(personToAdd, addedCard);

		// confirm the list now contains all previous persons plus the new task
		TestTask[] expectedList = TestUtil.addTasksToList(currentList, personToAdd);
		assertTrue(this.taskListPanel.isListMatching(expectedList));
	}

}
