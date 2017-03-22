//package guitests;
//
//import static org.junit.Assert.assertTrue;
//import static project.taskcrusher.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;
//
//import org.junit.Test;
//
//import project.taskcrusher.testutil.TestCard;
//import project.taskcrusher.testutil.TestUtil;
//
//public class DeleteCommandTest extends AddressBookGuiTest {
//
//    @Test
//    public void delete() {
//
//        //delete the first in the list
//        TestCard[] currentList = td.getTypicalTasks();
//        int targetIndex = 1;
//        assertDeleteSuccess(targetIndex, currentList);
//
//        //delete the last in the list
//        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
//        targetIndex = currentList.length;
//        assertDeleteSuccess(targetIndex, currentList);
//
//        //delete from the middle of the list
//        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
//        targetIndex = currentList.length / 2;
//        assertDeleteSuccess(targetIndex, currentList);
//
//        //invalid index
//        commandBox.runCommand("delete " + currentList.length + 1);
//        assertResultMessage("Invalid command format! \ndelete"
//                + ": Deletes the task/event identified by the index number used in the last task/event listing.\n"
//                + "Parameters: FLAG INDEX (must be a positive integer)\n"
//                + "Example: " + "delete" + " e 1");
//
//    }
//
//    /**
//     * Runs the delete command to delete the person at specified index and confirms the result is correct.
//     * @param targetIndexOneIndexed e.g. index 1 to delete the first person in the list,
//     * @param currentList A copy of the current list of persons (before deletion).
//     */
//    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestCard[] currentList) {
//        TestCard taskToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
//        TestCard[] expectedRemainder = TestUtil.removePersonFromList(currentList, targetIndexOneIndexed);
//
//        commandBox.runCommand("delete t " + targetIndexOneIndexed);
//
//        //confirm the list now contains all previous persons except the deleted person
//        assertTrue(userInboxPanel.isListMatching(expectedRemainder));
//
//        //confirm the result message is correct
//        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
//    }
//
//}
