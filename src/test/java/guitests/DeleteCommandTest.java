package guitests;

import static org.junit.Assert.assertTrue;
import static project.taskcrusher.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import project.taskcrusher.testutil.TestTaskCard;
import project.taskcrusher.testutil.TestUtil;

public class DeleteCommandTest extends TaskcrusherGuiTest {

    @Test
    public void delete() {

        //delete the first in the list
        TestTaskCard[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
        targetIndex = currentList.length / 2;
        assertDeleteSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("delete " + currentList.length + 1);
        assertResultMessage("Invalid command format! \ndelete"
                + ": Deletes the task/event identified by the index number used in the last task/event listing.\n"
                + "Parameters: FLAG INDEX (must be a positive integer)\n"
                + "Example: " + "delete" + " e 1");

    }

    //@@author A0127737X
    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first task in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTaskCard[] currentList) {
        Arrays.sort(currentList);
        TestTaskCard taskToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestTaskCard[] expectedRemainder = TestUtil.removePersonFromList(currentList, targetIndexOneIndexed);
        Arrays.sort(expectedRemainder);

        commandBox.runCommand("delete t " + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(userInboxPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
