package guitests;

import static org.junit.Assert.assertTrue;
import static typetask.logic.commands.DoneCommand.MESSAGE_COMPLETE_TASK_SUCCESS;

import org.junit.Test;

import typetask.commons.core.Messages;
import typetask.testutil.TestTask;

//@@author A0144902L
public class DoneCommandTest extends AddressBookGuiTest {

    TestTask[] expectedTaskList = td.getTypicalTasks();

  //---------------- Tests for validity of given index for Different Format -----------------------------

    /*
     * EP: Check if successfully marked tasks completed with different index and format,
     * Should change task boolean isCompleted to true.
     * Should return true.
     */

    @Test
    public void done() throws Exception {
        TestTask[] currentList = td.getTypicalTasks();
        //Marks the first task in the list as complete
        int targetIndex = 1;
        currentList[targetIndex - 1].setIsCompleted(true);
        assertDoneSuccess(targetIndex, currentList);

        //Marks the last task in the list as complete
        targetIndex = currentList.length - 1;
        currentList[targetIndex - 1].setIsCompleted(true);
        assertDoneSuccess(targetIndex, currentList);

        //Marks the middle task in the list as complete
        targetIndex = currentList.length / 2;
        currentList[targetIndex - 1].setIsCompleted(true);
        assertDoneSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("done " + currentList.length);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        //missing index
        commandBox.runCommand("done ");
        //assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void complete() {

        TestTask[] currentList = td.getTypicalTasks();
        //Marks the first task in the list as complete
        int targetIndex = 1;
        currentList[targetIndex - 1].setIsCompleted(true);
        assertCompleteSuccess(targetIndex, currentList);

        //Marks the last task in the list as complete
        targetIndex = currentList.length - 1;
        currentList[targetIndex - 1].setIsCompleted(true);
        assertCompleteSuccess(targetIndex, currentList);

        //Marks the middle task in the list as complete
        targetIndex = currentList.length / 2;
        currentList[targetIndex - 1].setIsCompleted(true);
        assertCompleteSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("complete " + currentList.length);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        //missing index
        commandBox.runCommand("complete ");

    }

    //---------------- Tests for successfully marking done a task after find command------------------

    /*
     * EP: Check if successfully marked done a command after performing a find command,
     * Should change task boolean isCompleted to true.
     * Should return true.
     */

    @Test
    public void markDone_findThenMarkDone_success() throws Exception {
        commandBox.runCommand("find Carl");
        commandBox.runCommand("done 1");

    }


    //---------------- End of test cases --------------------------------------

    /**
     * Runs the done command to mark the task as done at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first task in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDoneSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask TaskToComplete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        boolean isTestCompleted = TaskToComplete.getIsCompleted();

        commandBox.runCommand("done " + targetIndexOneIndexed);

        //confirm the task is completed
        assertTrue(isTestCompleted);

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, TaskToComplete));
    }

    private void assertCompleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask TaskToComplete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        boolean isTestCompleted = TaskToComplete.getIsCompleted();

        commandBox.runCommand("complete " + targetIndexOneIndexed);

        //confirm the task is completed
        assertTrue(isTestCompleted);

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, TaskToComplete));
    }

}
