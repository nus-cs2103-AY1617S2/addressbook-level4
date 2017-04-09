package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.testutil.TestEvent;
import org.teamstbf.yats.testutil.TestUtil;

import guitests.guihandles.EventCardHandle;

public class AddCommandTest extends TaskManagerGuiTest {

	@Test
	public void add() {
		// add one event
		TestEvent[] currentList = td.getTypicalTasks();
		TestEvent eventToAdd = td.cower;
		assertAddSuccess(eventToAdd, currentList);
		currentList = TestUtil.addEventsToList(currentList, eventToAdd);

		// add another event
		eventToAdd = td.cower;
		assertAddSuccess(eventToAdd, currentList);
		currentList = TestUtil.addEventsToList(currentList, eventToAdd);

		// add duplicate person
		/*
		 * commandBox.runCommand(td.cower.getAddCommand());
		 * assertResultMessage(AddCommand.MESSAGE_DUPLICATE_EVENT);
		 * assertTrue(taskListPanel.isListMatching(currentList));
		 */

		// add to empty list
		commandBox.runCommand("reset");
		assertAddSuccess(td.duck);

		// invalid command
		commandBox.runCommand("adds Johnny");
		assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
	}

	private void assertAddSuccess(TestEvent eventToAdd, TestEvent... currentList) {
		if (eventToAdd == td.cower) {
			commandBox.runCommand("add Act like a craven -l The Wall -s 8 april 2017 11:59PM -e 8 april 2017 11:59PM -d fighting the wildings, but extremely scared -t LordCommanderJonSnow");
		}
		if (eventToAdd == td.duck) {
			commandBox.runCommand("add ahhhh -by 8 april 2017 11:59pm -l AHHH -d AHHHHHHHHH -t AHHHHHHHHHHHHHH");
		}
		// confirm the new card contains the right data
		EventCardHandle addedCard = taskListPanel.navigateToEvent(eventToAdd.getTitle().fullName);
		assertMatching(eventToAdd, addedCard);

		// confirm the list now contains all previous persons plus the new
		// person
		TestEvent[] expectedList = TestUtil.addEventsToList(currentList, eventToAdd);
		assertTrue(taskListPanel.isListMatching(expectedList));
	}
}
