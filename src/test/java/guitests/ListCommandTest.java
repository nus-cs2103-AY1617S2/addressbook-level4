package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import guitests.guihandles.TaskCardHandle;
import seedu.taskit.commons.core.Messages;
import seedu.taskit.logic.commands.ListCommand;
import seedu.taskit.testutil.TestTask;
import seedu.taskit.testutil.TestUtil;

public class ListCommandTest extends AddressBookGuiTest {
	
	@Test
	public void list() {
		//list all tasks
		commandBox.runCommand("list all");
		assertResultMessage(ListCommand.MESSAGE_SUCCESS_ALL);
		assertListSize(taskListPanel.getNumberOfTasks());
		
//		//list undone tasks
//		commandBox.runCommand("list undone");
//		assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "undone"));
//		assertListResult(td.hw1,td.hw2,td.lunch,td.interview,td.shopping);

		
		System.out.println("done is: " + td.gymming.isDone());
		//list done tasks
		commandBox.runCommand("list done");
		assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "done"));
		assertListResult(td.gymming,td.cleaning);
		
		
		//list today tasks
		commandBox.runCommand("list today");
		assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "today"));
		
		//list overdue tasks
		commandBox.runCommand("list overdue");
		assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "overdue"));
	}
	
	private void assertListResult(TestTask... expectedHits) {
        assertListSize(expectedHits.length);
        Arrays.sort(expectedHits);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
