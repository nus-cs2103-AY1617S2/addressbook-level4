package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.taskit.logic.commands.EditCommand;
import seedu.taskit.logic.commands.ListCommand;
import seedu.taskit.testutil.TestTask;

public class ListCommandTest extends TaskManagerGuiTest {

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
		commandBox.runCommand("list done");
		assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "done"));
		assertListResult(td.cleaning);
    }

  //@@author A0141872E
    @Test
    public void list_undoneTasks_Success() {
		commandBox.runCommand("list undone");
		assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "undone"));
		assertListResult(td.getUndoneTypicalTasks());
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("list ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidFieldsSpecified_failure() {
        commandBox.runCommand("list task");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
    //@@author

  //@@author A0097141H
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
		assertListSize(1);
		assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_SPECIFIC, "overdue"));
    }

	private void assertListResult(TestTask... expectedHits) {
        assertListSize(expectedHits.length);
        Arrays.sort(expectedHits);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
