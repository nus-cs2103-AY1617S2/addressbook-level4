package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;

public class FindCommandTest extends TaskManagerGuiTest {
	// needs working on
	@Test
	public void find_nonEmptyList() {
		assertFindResult("find corn"); // no results
		// assertFindResult("find Meier", td.benson, td.daniel); // multiple
		// results not done

		// find after deleting one result
		commandBox.runCommand("delete 1");
		// notdone assertFindResult("find Meier", td.daniel);
	}

	@Test
	public void find_emptyList() {
		commandBox.runCommand("clear");
		assertFindResult("find Jean"); // no results
	}

	@Test
	public void find_invalidCommand_fail() {
		commandBox.runCommand("findchickenwings");
		assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
	}

	private void assertFindResult(String command, TestTask... expectedHits) {
		commandBox.runCommand(command);
		assertListSize(expectedHits.length);
		assertResultMessage(expectedHits.length + " tasks listed!");
		assertTrue(taskListPanel.isListMatching(expectedHits));
	}
}
