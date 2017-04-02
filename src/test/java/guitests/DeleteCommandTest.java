package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_ACTIVITY_SUCCESS;

import org.junit.Test;

import seedu.address.testutil.TestEvent;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

//@@author A0148038A
/*
 * GUI test for DeleteCommand
 */
public class DeleteCommandTest extends WhatsLeftGuiTest {

    @Test
    public void deleteEvent() {

        //delete the first in the list
        TestEvent[] currentList = te.getTypicalEvents();
        int targetIndex = 1;
        assertDeleteEventSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeEventFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteEventSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeEventFromList(currentList, targetIndex);
        targetIndex = currentList.length / 2;
        assertDeleteEventSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("delete ev " + currentList.length + 1);
        assertResultMessage("The event index provided is invalid");

    }

    @Test
    public void deleteTask() {

        //delete the first in the list
        TestTask[] currentList = tt.getTypicalTasks();
        int targetIndex = 1;
        assertDeleteTaskSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteTaskSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length / 2;
        assertDeleteTaskSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("delete ts " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the delete command to delete the activity at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first activity in the list,
     * @param currentList A copy of the current list of activities (before deletion).
     */
    private void assertDeleteEventSuccess(int targetIndexOneIndexed, final TestEvent[] currentList) {
        TestEvent eventToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestEvent[] expectedRemainder = TestUtil.removeEventFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete ev " + targetIndexOneIndexed);

        //confirm the list now contains all previous events except the deleted event
        assertTrue(eventListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_ACTIVITY_SUCCESS, eventToDelete));
    }

    private void assertDeleteTaskSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete ts " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_ACTIVITY_SUCCESS, taskToDelete));
    }

}
