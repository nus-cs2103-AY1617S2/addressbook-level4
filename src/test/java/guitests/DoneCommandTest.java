package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.ezdo.logic.commands.DoneCommand.MESSAGE_DONE_TASK_SUCCESS;

import org.junit.Test;

import seedu.ezdo.testutil.TestTask;
import seedu.ezdo.testutil.TestUtil;

public class DoneCommandTest extends EzDoGuiTest {

    @Test
    public void done_success() {

        //delete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        TestTask[] doneList = td.getTypicalDoneTasks();
        int targetIndex = 1;
        TestTask toDone = currentList[targetIndex - 1];
        assertDoneSuccess(targetIndex, currentList, doneList);

        //delete the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        doneList = TestUtil.addTasksToList(doneList, toDone);
        targetIndex = currentList.length;
        assertDoneSuccess(targetIndex, currentList, doneList);

        //invalid index
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        doneList = TestUtil.addTasksToList(doneList, toDone);
        commandBox.runCommand("done " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid"); 
    }

    private void assertDoneSuccess(int targetIndexOneIndexed, final TestTask[] currentList, final TestTask[] doneList) {
        
        TestTask taskToDone = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);
        TestTask[] expectedDone = TestUtil.addTasksToList(doneList, taskToDone);

        commandBox.runCommand("done " + targetIndexOneIndexed);

        //confirm the list now contains all done tasks including the one just marked as done
        assertTrue(taskListPanel.isListMatching(expectedDone));
        
        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToDone));
        
        //confirm the undone list does not contain the task just marked as done
        commandBox.runCommand("list");
        assertTrue(taskListPanel.isListMatching(expectedRemainder));
    }

}
