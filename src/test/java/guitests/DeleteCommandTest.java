package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskboss.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import java.util.ArrayList;

import org.junit.Test;

import seedu.taskboss.testutil.TestTask;
import seedu.taskboss.testutil.TestUtil;

public class DeleteCommandTest extends TaskBossGuiTest {

    @Test
    public void delete() {

        //delete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertDeleteSuccess(false, false, targetIndex, currentList);

        //repeat of previous test with '-' shortcut
        commandBox.runCommand("u");
        currentList = td.getTypicalTasks();
        targetIndex = 1;
        assertDeleteSuccess(true, false, targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(false, false, targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length / 2;
        assertDeleteSuccess(false, false, targetIndex, currentList);

        //delete the first in the list using short command
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = 1;
        assertDeleteSuccess(false, true, targetIndex, currentList);

        //invalid index
        commandBox.runCommand("delete " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first task in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(boolean isMinusSign, boolean isShortCommand, int targetIndexOneIndexed,
                      final TestTask[] currentList) {
        TestTask taskToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        if (isShortCommand) {
            commandBox.runCommand("d " + targetIndexOneIndexed);
        } else {
            if (isMinusSign) {
                commandBox.runCommand("- " + targetIndexOneIndexed);
            } else {
                commandBox.runCommand("delete " + targetIndexOneIndexed);
            }
        }

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));
        //@@author A0138961W
        //confirm the result message is correct
        ArrayList<TestTask> deletedTasks = new ArrayList<TestTask>();
        deletedTasks.add(taskToDelete);
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, deletedTasks));
    }

}
