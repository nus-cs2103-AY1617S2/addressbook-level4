//@@author A0105748B
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.bulletjournal.logic.commands.FinishCommand.MESSAGE_FINISH_TASK_SUCCESS;

import org.junit.Test;

import seedu.bulletjournal.commons.exceptions.IllegalValueException;
import seedu.bulletjournal.model.task.Status;
import seedu.bulletjournal.testutil.TestTask;
import seedu.bulletjournal.testutil.TestUtil;

public class FinishCommandTest extends TodoListGuiTest {

    @Test
    public void finish() {

        //delete the first in the list
        TestTask[] currentList = td.getUndoneTasks();
        int targetIndex = 1;
        assertFinishSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertFinishSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length / 2;
        assertFinishSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("finish " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

        //Flexible finish command
        commandBox.runCommand("complete " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

        commandBox.runCommand("done " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

        commandBox.runCommand("finish " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the finish command to mark the task as done at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first task in the list,
     * @param currentList A copy of the current list of tasks (before command).
     */
    private void assertFinishSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToFinish = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        try {
            taskToFinish.setEmail(new Status("done"));
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("finish " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the finished task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_FINISH_TASK_SUCCESS, taskToFinish));
    }
}
