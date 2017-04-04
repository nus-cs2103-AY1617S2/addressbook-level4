package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import guitests.guihandles.TaskCardHandle;
import seedu.taskit.commons.core.Messages;
import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.logic.commands.ListCommand;
import seedu.taskit.testutil.TaskBuilder;
import seedu.taskit.testutil.TestTask;
import seedu.taskit.testutil.TestUtil;

//@@author A0097141H
public class ListCommandTest extends AddressBookGuiTest {
	
	@Test
	public void list() {
		//list all tasks
		commandBox.runCommand("list all");
		assertResultMessage(ListCommand.MESSAGE_SUCCESS_ALL);
		assertListSize(taskListPanel.getNumberOfTasks());
		
		//list done tasks
		commandBox.runCommand("mark 6 done");
		commandBox.runCommand("mark 6 done");
		commandBox.runCommand("list done");
		assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "done"));
		assertListResult(td.cleaning,td.gymming);
		
		//list undone tasks
		commandBox.runCommand("list undone");
		assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "undone"));
		assertListResult(td.hw1,td.hw2,td.lunch,td.interview,td.shopping);
		
		//list today tasks (no tasks)
		commandBox.runCommand("list today");
		assertResultMessage(ListCommand.MESSAGE_NO_TASK_TODAY);
		
		//list today's tasks (populated with two tasks today)
		commandBox.runCommand("add task1 by today");
		commandBox.runCommand("add task2 by today 12pm");
        commandBox.runCommand("list today");
        assertListSize(2); //2 tasks added by today
        assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "today"));
		
		//list overdue tasksX
		commandBox.runCommand("add task3 by 04/04/17");
		commandBox.runCommand("list overdue");
		assertListSize(2); //because task1 has just been overdue.
		assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "overdue"));
		
		
	}
	
	private void assertListResult(TestTask... expectedHits) {
        assertListSize(expectedHits.length);
        Arrays.sort(expectedHits);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
