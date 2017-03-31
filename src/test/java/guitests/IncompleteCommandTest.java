package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.IncompleteCommand;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class IncompleteCommandTest extends AddressBookGuiTest {

    TestTask[] originalTaskList = td.getTypicalTasks();

    @Test
    public void incomplete_invalidIndexNumber_failure() {
        int indexNumber = originalTaskList.length + 1;
        assertIncompleteFailure(indexNumber);
    }

    @Test
    public void incomplete_MissingIndexNumber_failure() {
        commandBox.runCommand("incomplete");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, IncompleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void incomplete_validIndexNumber_success() {
        commandBox.runCommand(td.hoon.getAddCommand());
        int indexNumber = originalTaskList.length + 1;
        assertIncompleteSuccess(indexNumber, td.hoon);
    }

    /**
     * Runs the complete command to mark as complete a task specified at an invalid index and confirms
     * the index is incorrect.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertIncompleteFailure(int targetIndexOneIndexed) {
        commandBox.runCommand("incomplete " + targetIndexOneIndexed);

        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

    }

    /**
     * Runs the complete command to mark as complete a specified task  at an invalid index and confirms
     * the index is incorrect.
     * @param targetIndexOneIndexed e.g. index 1 to mark as complete the first task in the list,
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertIncompleteSuccess(int targetIndexOneIndexed, TestTask editedTask) {
        commandBox.runCommand("complete " + targetIndexOneIndexed);
        commandBox.runCommand("incomplete 1");
        TestTask[] expectedTaskList = TestUtil.addTasksToList(originalTaskList, editedTask);

        assertResultMessage(String.format(IncompleteCommand.MESSAGE_MARK_INCOMPLETE_SUCCESS, editedTask));
        //assertTrue(taskListPanel.isListMatching(expectedTaskList));

    }

}

