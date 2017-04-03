package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class DeleteCommandTest extends TaskManagerGuiTest {

    @Test
    public void delete()
            throws IllegalArgumentException, IllegalValueException {

        // delete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);

        // delete the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        // delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length / 2;
        assertDeleteSuccess(targetIndex, currentList);

        // invalid index
        commandBox.runCommand("delete F100");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

    }

    /**
     * Runs the delete command to delete the task at specified index and
     * confirms the result is correct.
     *
     * @param targetIndexOneIndexed
     *            e.g. index 1 to delete the first task in the list,
     * @param currentList
     *            A copy of the current list of tasks (before deletion).
     * @throws IllegalValueException
     * @throws IllegalArgumentException
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed,
            final TestTask[] currentList)
            throws IllegalArgumentException, IllegalValueException {
        // update ui index
        TestUtil.assignUiIndex(currentList);
        TestTask taskToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as
                                                                        // array
                                                                        // uses
                                                                        // zero
                                                                        // indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList,
                targetIndexOneIndexed);

        todayTaskListPanel.clickOnListView();
        futureTaskListPanel.clickOnListView();
        commandBox.runCommand(
                "delete " + currentList[targetIndexOneIndexed - 1].getID());

        // confirm the list now contains all previous tasks except the deleted
        // task

        assertTrue(futureTaskListPanel.isListMatching(expectedRemainder));

        // confirm the result message is correct
        assertResultMessage(
                String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }
}
