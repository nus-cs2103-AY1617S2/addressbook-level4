package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.doist.logic.commands.FinishCommand.MESSAGE_FINISH_TASK_SUCCESS;
import static seedu.doist.logic.commands.FinishCommand.MESSAGE_TASK_ALREADY_FINISHED;

import java.util.ArrayList;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.doist.testutil.TestTask;

//@@author A0140887W
public class FinishCommandTest extends DoistGUITest {

    @Test
    public void finish() {

        //finish the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertFinishSuccess(targetIndex, currentList);

        //finish the last in the list
        targetIndex = currentList.length;
        assertFinishSuccess(targetIndex, currentList);

        //finish from the middle of the list
        targetIndex = currentList.length / 2;
        assertFinishSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("finish " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

        // finish a task that has already been finished
        assertAlreadyFinished(1, currentList);
    }

    /**
     * Runs the finish command to finish the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to finish the first task in the list,
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertFinishSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask[] expectedRemainder = currentList.clone();
        TestTask taskToFinish = expectedRemainder[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        taskToFinish.setFinishedStatus(true);

        commandBox.runCommand("finish " + targetIndexOneIndexed);

        //confirm the list matching
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm that UI is showing finished
        TaskCardHandle finishedCard = taskListPanel.getTaskCardHandle(targetIndexOneIndexed - 1);
        assertTrue(finishedCard.isStyleInStyleClass("finished"));

        //confirm the result message is correct
        ArrayList<TestTask> tasksToFinish = new ArrayList<TestTask>();
        tasksToFinish.add(taskToFinish);
        assertResultMessage(String.format(MESSAGE_FINISH_TASK_SUCCESS, tasksToFinish));
    }

    private void assertAlreadyFinished(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask[] expectedRemainder = currentList.clone();
        TestTask taskToFinish = expectedRemainder[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        taskToFinish.setFinishedStatus(true);

        commandBox.runCommand("finish " + targetIndexOneIndexed);

        //confirm the list matching
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        ArrayList<TestTask> tasksToFinish = new ArrayList<TestTask>();
        tasksToFinish.add(taskToFinish);
        assertResultMessage(String.format(MESSAGE_TASK_ALREADY_FINISHED, tasksToFinish) + "\n");
    }

}
