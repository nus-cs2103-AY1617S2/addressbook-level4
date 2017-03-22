package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;
public class AddCommandTest extends AddressBookGuiTest {

	@Test
	public void add() {
		//add one task
		TestTask[] currentList = td.getTypicalTasks();
		TestTask taskToAdd = td.ida;
		//        System.out.println(currentList[0]);
		assertAddSuccess(taskToAdd, currentList);
		currentList = TestUtil.addTasksToList(currentList, taskToAdd);

		//        //add another task
		taskToAdd = td.hoon;
		assertAddSuccess(taskToAdd, currentList);
		currentList = TestUtil.addTasksToList(currentList, taskToAdd);
		////
		//        //add duplicate task
		commandBox.runCommand(td.hoon.getAddCommand());

		assertTrue(taskListPanel.isListMatching(currentList));
		////
		////        //add to empty list
		commandBox.runCommand("clear");
		assertAddSuccess(td.alice);
		//
		////        //invalid command
		commandBox.runCommand("adds Johnny");
		assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
	}

	private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
		for(int i=0;i<currentList.length;i++)commandBox.runCommand(currentList[i].getAddCommand());
		commandBox.runCommand(taskToAdd.getAddCommand());


		//confirm the new card contains the right data
		TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getDescription().toString());
		assertMatching(taskToAdd, addedCard);

		//confirm the list now contains all previous tasks plus the new task
		TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
		assertTrue(taskListPanel.isListMatching(expectedList));
	}
}