package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.testutil.TestEvent;
import org.teamstbf.yats.testutil.TestUtil;

import guitests.guihandles.EventCardHandle;

public class ScheduleCommandTest extends TaskManagerGuiTest {

	@Test
	public void schedule() {
		// schedule one event
		TestEvent[] currentList = td.getTypicalTasks();
		currentList = td.getTypicalTasks();
		assertScheduleTimingSuccess(td.sameDayScheduleChecker, currentList);
		currentList = TestUtil.addEventsToList(currentList, td.sameDayScheduleChecker);
		
		assertScheduleTimingSuccess(td.nextDayScheduleChecker, currentList);
		currentList = TestUtil.addEventsToList(currentList, td.nextDayScheduleChecker);
		
		TestEvent eventToAdd = td.fish;
		assertScheduleSuccess(eventToAdd, currentList);
		currentList = TestUtil.addEventsToList(currentList, eventToAdd);

		// schedule another event
		eventToAdd = td.goon;
		assertScheduleSuccess(eventToAdd, currentList);
		currentList = TestUtil.addEventsToList(currentList, eventToAdd);

		// schedule to empty list
		commandBox.runCommand("reset");
		assertScheduleSuccess(td.fish);

		// invalid command
		commandBox.runCommand("scheduled you");
		assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
	}

	private void assertScheduleSuccess(TestEvent eventToAdd, TestEvent... currentList) {
		commandBox.runCommand(eventToAdd.getScheduleCommand());
		
		// confirm the new card contains the right data
		EventCardHandle addedCard = taskListPanel.navigateToEvent(eventToAdd.getTitle().fullName);
		assertMatching(eventToAdd, addedCard);
		// confirm the list now contains all previous persons plus the new
		// person
		TestEvent[] expectedList = TestUtil.addEventsToList(currentList, eventToAdd);
		assertTrue(taskListPanel.isListMatchingWithoutOrder(expectedList));
	}
	
	private void assertScheduleTimingSuccess(TestEvent eventToAdd, TestEvent... currentList) {
		commandBox.runCommand(eventToAdd.getScheduleCommand());
		eventToAdd.setHours("");
		eventToAdd.setMinutes("");
		// confirm the new card contains the right data
		EventCardHandle addedCard = taskListPanel.navigateToEvent(eventToAdd.getTitle().toString());
		assertMatching(eventToAdd, addedCard);
		// confirm the list now contains all previous persons plus the new
		// person
		TestEvent[] expectedList = TestUtil.addEventsToList(currentList, eventToAdd);
		assertTrue(taskListPanel.isListMatchingWithoutOrder(expectedList));
	}
}

