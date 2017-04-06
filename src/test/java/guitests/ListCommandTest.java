package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.taskit.logic.commands.ListCommand;
import seedu.taskit.testutil.TestTask;

public class ListCommandTest extends AddressBookGuiTest {

    //@@author A0097141H
    @Test
	public void list_allTasks_Success() {
		commandBox.runCommand("list all");
		assertResultMessage(ListCommand.MESSAGE_SUCCESS_ALL);
		assertListSize(taskListPanel.getNumberOfTasks());
    }

    @Test
    public void list_doneTasks_Success() {
		commandBox.runCommand("mark 6 done");
		commandBox.runCommand("mark 6 done");
		commandBox.runCommand("list done");
		assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "done"));
		assertListResult(td.cleaning,td.gymming);
    }

    @Test
    public void list_undoneTasks_Success() {
		commandBox.runCommand("list undone");
		assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "undone"));
		assertListResult(td.hw1,td.hw2,td.lunch,td.interview,td.shopping);
    }

    @Test
    public void list_todayTasks_Success() {
        //no task for today
		commandBox.runCommand("list today");
		assertResultMessage(ListCommand.MESSAGE_NO_TASK_TODAY);

		//list today's tasks (populated with two tasks today)
		commandBox.runCommand("add task1 by today");
		commandBox.runCommand("add task2 by today 12pm");
        commandBox.runCommand("list today");
        assertListSize(2); //2 tasks added by today
        assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "today"));
    }

    @Test
    public void list_overdueTasks_Success() {
		commandBox.runCommand("add task3 by 04/04/17");
		commandBox.runCommand("list overdue");
		assertListSize(2); //because task1 has just been overdue.
		assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "overdue"));
    }//@@author

	//@@author A0141872E
    @Test
    public void list_floatingTasks_Success() {
        commandBox.runCommand("list floating");
        assertListSize(10);
        assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "floating"));
    }

    @Test
    public void list_eventTasks_Success() {
        commandBox.runCommand("add task3 from 3pm to 4pm");
        commandBox.runCommand("list event");
        assertListSize(1);
        assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "event"));
    }

    @Test
    public void list_deadlineTasks_Success() {
        commandBox.runCommand("list deadline");
        assertListSize(3);
        assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "deadline"));
    }

    @Test
    public void list_alias_Success() {
        commandBox.runCommand("l deadline");
        assertListSize(3);
        assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "deadline"));
    }


	private void assertListResult(TestTask... expectedHits) {
        assertListSize(expectedHits.length);
        Arrays.sort(expectedHits);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
