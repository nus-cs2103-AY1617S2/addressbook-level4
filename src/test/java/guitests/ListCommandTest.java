package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskit.commons.core.Messages;
import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.logic.commands.ListCommand;
import seedu.taskit.testutil.TaskBuilder;
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

		System.out.println("list size is: " + taskListPanel.getNumberOfTasks());
		System.out.println("gymming is: " + td.gymming.isDone());
		System.out.println("cleaning is: " + td.cleaning.isDone());
		System.out.println("hw1 is: " + td.hw1.isDone());
		//list done tasks
		commandBox.runCommand("mark 6 done");
		commandBox.runCommand("mark 6 done");
		commandBox.runCommand("list done");
		assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "done"));
		assertListResult(td.cleaning,td.gymming);
		
		
		//list today tasks (no tasks)
		commandBox.runCommand("list today");
		assertResultMessage(ListCommand.MESSAGE_NO_TASK_TODAY);
		
		//list today's tasks (populated with two tasks today)
		
		TestTask taskToAdd = td.golfing;
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
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
