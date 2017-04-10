package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.tache.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.tache.commons.core.Messages;
import seedu.tache.logic.commands.DeleteCommand;
import seedu.tache.testutil.TestTask;
import seedu.tache.testutil.TestUtil;

public class DeleteCommandTest extends TaskManagerGuiTest {

    @Test
    public void delete_recurringTask_failure() {
        commandBox.runCommand("clear");
        commandBox.runCommand("add test from 9 april to 16 april every day");

        commandBox.runCommand("delete 1");
        assertResultMessage(DeleteCommand.MESSAGE_PART_OF_RECURRING_TASK);

        commandBox.runCommand("delete 2");
        assertResultMessage(DeleteCommand.MESSAGE_PART_OF_RECURRING_TASK);

        commandBox.runCommand("delete 3");
        assertResultMessage(DeleteCommand.MESSAGE_PART_OF_RECURRING_TASK);

        commandBox.runCommand("delete 7");
        assertResultMessage(DeleteCommand.MESSAGE_PART_OF_RECURRING_TASK);
    }

    @Test
    public void delete() {

        //delete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length / 2;
        assertDeleteSuccess(targetIndex, currentList);

        //@@author A0142255M
        //invalid index
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " " + currentList.length + 1);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

    }

    @Test
    public void delete_invalidParameter_failure() {
        commandBox.runCommand("delete second task");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void delete_shortCommand_success() {
        commandBox.runCommand(DeleteCommand.SHORT_COMMAND_WORD + " 1");
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        TestTask taskToDelete = currentList[targetIndex - 1];
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndex);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    @Test
    public void delete_invalidCommand_success() {
        commandBox.runCommand("detele");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    //@@author

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first task in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
