package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.teamstbf.yats.model.item.ReadOnlyEvent;

public class SelectCommandTest extends TaskManagerGuiTest {


	@Test
	public void selectEvent_nonEmptyList() {

		assertSelectionInvalid(10); // invalid index
		assertNoEventSelected();

		assertSelectionSuccess(1); // first person in the list
		int eventCount = td.getTypicalTasks().length;
		assertSelectionSuccess(eventCount); // last person in the list
		int middleIndex = eventCount / 2;
		assertSelectionSuccess(middleIndex); // a person in the middle of the list

		assertSelectionInvalid(eventCount + 1); // invalid index
		assertEventSelected(middleIndex); // assert previous selection remains

		/* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
	}

	@Test
	public void selectEvent_emptyList() {
		commandBox.runCommand("reset");
		assertListSize(0);
		assertSelectionInvalid(1); //invalid index
	}

	private void assertSelectionInvalid(int index) {
		commandBox.runCommand("select " + index);
		assertResultMessage("The task index provided is invalid");
	}

	private void assertSelectionSuccess(int index) {
		commandBox.runCommand("select " + index);
		assertResultMessage("Selected Task: " + index);
		assertEventSelected(index);
	}

	private void assertEventSelected(int index) {
		assertEquals(taskListPanel.getSelectedEvent().size(), 1);
		ReadOnlyEvent selectedEvent = taskListPanel.getSelectedEvent().get(0);
		assertEquals(taskListPanel.getEvent(index - 1), selectedEvent);
		//TODO: confirm the correct page is loaded in the Browser Panel
	}

	private void assertNoEventSelected() {
		assertEquals(taskListPanel.getSelectedEvent().size(), 0);
	}

}
