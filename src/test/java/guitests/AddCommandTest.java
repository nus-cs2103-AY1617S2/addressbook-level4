package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.AddCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

	@Test
	public void add() {

		/*
		 * TestTask[] currentList = td.getTypicalTasks(); TestTask personToAdd =
		 * td.hoon; assertAddSuccess(personToAdd, currentList); currentList =
		 * TestUtil.addPersonsToList(currentList, personToAdd);
		 */
		// add one task
		TestTask[] currentList = td.getTypicalTasks();
		TestTask taskToAdd = td.zoo;
		assertAddSuccess(taskToAdd, currentList);
		currentList = TestUtil.addTasksToList(currentList, taskToAdd);

		/*
		 * 
		 * personToAdd = td.ida; assertAddSuccess(personToAdd, currentList);
		 * currentList = TestUtil.addTasksToList(currentList, personToAdd);
		 */
		// add another person
		taskToAdd = td.yam;
		assertAddSuccess(taskToAdd, currentList);
		currentList = TestUtil.addTasksToList(currentList, taskToAdd);
		/*
		 * //add duplicate person
		 * commandBox.runCommand(td.hoon.getAddCommand());
		 * assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
		 * assertTrue(taskListPanel.isListMatching(currentList));
		 * 
		 * 
		 * 
		 * //add to empty list commandBox.runCommand("clear");
		 * assertAddSuccess(td.alice);
		 */
		// invalid command
		commandBox.runCommand("adds Johnny");
		assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
	}

	private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
		commandBox.runCommand(taskToAdd.getAddCommand());

		// confirm the new card contains the right data
		TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getTaskName().fullTaskName);
		assertMatching(taskToAdd, addedCard);

		// confirm the list now contains all previous persons plus the new
		// person
		TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
		assertTrue(taskListPanel.isListMatching(expectedList));
	}

}
