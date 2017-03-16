package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.testutil.TestEvent;

public class FindCommandTest extends TaskManagerGuiTest {

	@Test
	public void find_nonEmptyList() {
		assertFindResult("find Vscan"); // no results
		assertFindResult("find Act", td.boop, td.cower); // multiple results

		// find after deleting one result
		commandBox.runCommand("delete 1");
		assertFindResult("find Act", td.cower);
	}

	@Test
	public void find_emptyList() {
		commandBox.runCommand("clear");
		assertFindResult("find PAXEAST"); // no results
	}

	@Test
	public void find_invalidCommand_fail() {
		commandBox.runCommand("findmedical appointment");
		assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
	}

	private void assertFindResult(String command, TestEvent... expectedHits) {
		commandBox.runCommand(command);
		assertListSize(expectedHits.length);
		assertResultMessage(expectedHits.length + " tasks listed!");
		assertTrue(taskListPanel.isListMatching(expectedHits));
	}
}
