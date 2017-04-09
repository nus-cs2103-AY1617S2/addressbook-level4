package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.logic.commands.ListCommand;
import org.teamstbf.yats.testutil.TestEvent;

//@@author A0138952W

public class ListCommandTest extends TaskManagerGuiTest {

	@Test
	public void list_nonEmptyList() {
		/** lists all tasks in the list */
		assertListAllResult("list", td.abdicate, td.boop, td.oxygen, td.cower, td.duck);

		/** lists all done tasks in the list */
		assertListResult("list done");

		/**
		 * lists all tasks in the primary list that starts on 9th April
		 */
		assertListResult("list by start 08/04/2017", td.abdicate, td.oxygen, td.cower);

		/** lists all tasks in the primary list that ends on 8th April */
		assertListResult("list by end 08/04/2017", td.abdicate, td.oxygen, td.cower);

		/** lists all tasks in the primary list that has deadline 8th April */
		assertListResult("list by deadline 08/04/2017", td.boop, td.duck);

		/** lists all tasks in the primary list that has the location NUS */
		assertListResult("list by location The Wall", td.cower);

		/** lists all tasks in the primary list that has the tag Tag */
		assertListResult("list by tag Tag ONN", td.oxygen);
	}

	@Test
	public void list_emptyList() {
		commandBox.runCommand("reset");
		assertListAllResult("list"); // no result
	}

	@Test
	public void list_invalidCommand_fail() {
		commandBox.runCommand("listby start");
		assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
	}

	private void assertListResult(String command, TestEvent... expectedHits) {
		commandBox.runCommand(command);
		assertListSize(expectedHits.length);
		assertResultMessage(expectedHits.length + " tasks listed!");
		assertTrue(taskListPanel.isListMatching(expectedHits));
	}

	private void assertListAllResult(String command, TestEvent... expectedHits) {
		commandBox.runCommand(command);
		assertListSize(expectedHits.length);
		assertResultMessage(ListCommand.MESSAGE_SUCCESS);
	}
}
