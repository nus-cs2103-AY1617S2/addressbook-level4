package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doist.logic.commands.FinishCommand.MESSAGE_FINISH_TASK_SUCCESS;
import static seedu.doist.logic.commands.FinishCommand.MESSAGE_TASK_ALREADY_FINISHED;

import java.util.ArrayList;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.doist.logic.commands.FinishCommand;
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
        targetIndex = currentList.length - 1;
        assertFinishSuccess(targetIndex, currentList);

        //finish from the middle of the list
        targetIndex = currentList.length / 2;
        assertFinishSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("finish " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

        // finish a task that has already been finished
        assertAlreadyFinished(currentList.length, currentList);
    }

    //@@author A0147980U
    @Test
    public void testFinishWithEmptyParameter() {
        commandBox.runCommand("finish ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                          FinishCommand.MESSAGE_USAGE));

        commandBox.runCommand("finish");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                          FinishCommand.MESSAGE_USAGE));
    }
    //@@author

    /**
     * Runs the finish command to finish the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to finish the first task in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertFinishSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask[] expectedTasks = currentList.clone();
        sortTasksByDefault(expectedTasks);

        TestTask taskToFinish = expectedTasks[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        taskToFinish.setFinishedStatus(true);
        commandBox.runCommand("finish " + targetIndexOneIndexed);

        //confirm the list matching
        assertTrue(taskListPanel.isListMatching(expectedTasks));

        //confirm that UI is showing finished
        TaskCardHandle finishedCard = taskListPanel.getTaskCardHandle(taskToFinish);
        assertTrue(finishedCard.isStyleInStyleClass("finished"));

        //confirm the result message is correct
        ArrayList<TestTask> tasksToFinish = new ArrayList<TestTask>();
        tasksToFinish.add(taskToFinish);
        assertResultMessage(String.format(MESSAGE_FINISH_TASK_SUCCESS, tasksToFinish));
    }

    private void assertAlreadyFinished(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask[] expectedTasks = currentList.clone();
        sortTasksByDefault(expectedTasks);
        TestTask taskToFinish = expectedTasks[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        taskToFinish.setFinishedStatus(true);

        commandBox.runCommand("finish " + targetIndexOneIndexed);

        //confirm the list matching
        assertTrue(taskListPanel.isListMatching(expectedTasks));

        //confirm the result message is correct
        ArrayList<TestTask> tasksToFinish = new ArrayList<TestTask>();
        tasksToFinish.add(taskToFinish);
        assertResultMessage(String.format(MESSAGE_TASK_ALREADY_FINISHED, tasksToFinish) + "\n");
    }

}
