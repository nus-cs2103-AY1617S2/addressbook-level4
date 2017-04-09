package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.teamstbf.yats.testutil.TestEvent;

//@@author A0138952W

public class ListCommandTest extends TaskManagerGuiTest {

	@Test
	public void list_nonEmptyList() {
		assertListResult("list"); // lists all tasks in the list
	}

	private void assertListResult(String command, TestEvent... expectedHits) {
		commandBox.runCommand(command);
		assertListSize(expectedHits.length);
		assertResultMessage(expectedHits.length + " tasks listed!");
		assertTrue(taskListPanel.isListMatching(expectedHits));
	}
}