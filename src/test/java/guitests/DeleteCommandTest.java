package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.today.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.today.commons.core.Messages;
import seedu.today.commons.exceptions.IllegalValueException;
import seedu.today.model.task.Task;
import seedu.today.testutil.TestUtil;

public class DeleteCommandTest extends TaskManagerGuiTest {

    @Test
    public void delete() throws IllegalArgumentException, IllegalValueException {

        // delete the first in the list
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, todayList, futureList, completedList);

        // delete from the middle or end of the list
        todayList = TestUtil.removeTaskFromList(todayList, targetIndex);
        futureList = TestUtil.removeTaskFromList(futureList, targetIndex);
        completedList = TestUtil.removeTaskFromList(completedList, targetIndex);
        targetIndex = 2;
        assertDeleteSuccess(targetIndex, todayList, futureList, completedList);

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
    private void assertDeleteSuccess(int targetIndexOneIndexed, final Task[] todayList, final Task[] futureList,
            final Task[] completedList) throws IllegalArgumentException, IllegalValueException {
        // update ui index
        TestUtil.assignUiIndex(todayList);
        TestUtil.assignUiIndex(futureList);
        TestUtil.assignUiIndex(completedList);

        todayTaskListPanel.clickOnListView();
        futureTaskListPanel.clickOnListView();

        // Today
        Task taskToDelete = todayList[targetIndexOneIndexed - 1];
        Task[] expectedTodayList = TestUtil.removeTaskFromList(todayList, targetIndexOneIndexed);
        commandBox.runCommand("delete " + taskToDelete.getID());
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
        assertTrue(todayTaskListPanel.isListMatching(expectedTodayList));

        // Future
        taskToDelete = futureList[targetIndexOneIndexed - 1];
        Task[] expectedFutureList = TestUtil.removeTaskFromList(futureList, targetIndexOneIndexed);
        commandBox.runCommand("delete " + taskToDelete.getID());
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
        assertTrue(futureTaskListPanel.isListMatching(expectedFutureList));

        // Completed
        taskToDelete = completedList[targetIndexOneIndexed - 1];
        Task[] expectedCompletedList = TestUtil.removeTaskFromList(completedList, targetIndexOneIndexed);
        commandBox.runCommand("listcompleted");
        commandBox.runCommand("delete " + taskToDelete.getID());
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
        assertTrue(completedTaskListPanel.isListMatching(expectedCompletedList));
        commandBox.runCommand("list");
    }
}
