package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_ACTIVITY_SUCCESS;

import org.junit.Test;

import seedu.address.testutil.TestActivity;
import seedu.address.testutil.TestUtil;

public class DeleteCommandTest extends WhatsLeftGuiTest {

    @Test
    public void delete() {

        //delete the first in the list
        TestActivity[] currentList = td.getTypicalActivities();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeActivityFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeActivityFromList(currentList, targetIndex);
        targetIndex = currentList.length / 2;
        assertDeleteSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("delete ev " + currentList.length + 1);
        assertResultMessage("The Event index provided is invalid");

    }

    /**
     * Runs the delete command to delete the activity at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first activity in the list,
     * @param currentList A copy of the current list of activities (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestActivity[] currentList) {
        TestActivity activityToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestActivity[] expectedRemainder = TestUtil.removeActivityFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete ev " + targetIndexOneIndexed);

        //confirm the list now contains all previous activities except the deleted activity
        //assertTrue(activityListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_ACTIVITY_SUCCESS, activityToDelete));
    }

}
