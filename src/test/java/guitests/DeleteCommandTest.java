package guitests;

import static org.junit.Assert.assertTrue;
import static project.taskcrusher.logic.commands.DeleteCommand.MESSAGE_DELETE_EVENT_SUCCESS;
import static project.taskcrusher.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import project.taskcrusher.testutil.TestEventCard;
import project.taskcrusher.testutil.TestTaskCard;
import project.taskcrusher.testutil.TestUtil;

//@@author A0127737X
public class DeleteCommandTest extends TaskcrusherGuiTest {

    @Test
    public void deleteTasks() {

        //delete the first in the list
        TestTaskCard[] currentTaskList = td.getTypicalTasks();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentTaskList);

        //delete the last in the list
        currentTaskList = TestUtil.removeTaskFromList(currentTaskList, targetIndex);
        targetIndex = currentTaskList.length;
        assertDeleteSuccess(targetIndex, currentTaskList);

        //delete from the middle of the list
        currentTaskList = TestUtil.removeTaskFromList(currentTaskList, targetIndex);
        targetIndex = currentTaskList.length / 2;
        assertDeleteSuccess(targetIndex, currentTaskList);

        //invalid index
        commandBox.runCommand("delete " + currentTaskList.length + 1);
        assertResultMessage("Invalid command format! \ndelete"
                + ": Deletes the task/event identified by the index number used in the last task/event listing.\n"
                + "Parameters: FLAG INDEX (must be a positive integer)\n"
                + "Example: " + "delete" + " e 1");

    }

    @Test
    public void deleteEvents() {
        TestEventCard[] currentEventList = td.getTypicalEvents();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentEventList);

        //delete the last in the list
        currentEventList = TestUtil.removeEventFromList(currentEventList, targetIndex);
        targetIndex = currentEventList.length;
        assertDeleteSuccess(targetIndex, currentEventList);

        //delete from the middle of the list
        currentEventList = TestUtil.removeEventFromList(currentEventList, targetIndex);
        targetIndex = currentEventList.length / 2;
        assertDeleteSuccess(targetIndex, currentEventList);

        //invalid index
        commandBox.runCommand("delete " + currentEventList.length + 1);
        assertResultMessage("Invalid command format! \ndelete"
                + ": Deletes the task/event identified by the index number used in the last task/event listing.\n"
                + "Parameters: FLAG INDEX (must be a positive integer)\n"
                + "Example: " + "delete" + " e 1");
    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first task in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTaskCard[] currentList) {
        Arrays.sort(currentList);
        TestTaskCard taskToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestTaskCard[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);
        Arrays.sort(expectedRemainder);

        commandBox.runCommand("delete t " + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(userInboxPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    /**
     * Runs the delete command to delete the event at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first event in the list,
     * @param currentList A copy of the current list of events (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestEventCard[] currentList) {
        Arrays.sort(currentList);
        TestEventCard taskToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestEventCard[] expectedRemainder = TestUtil.removeEventFromList(currentList, targetIndexOneIndexed);
        Arrays.sort(expectedRemainder);

        commandBox.runCommand("delete e " + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(userInboxPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_EVENT_SUCCESS, taskToDelete));
    }

}
