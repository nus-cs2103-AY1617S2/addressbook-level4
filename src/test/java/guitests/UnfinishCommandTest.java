package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.doist.logic.commands.UnfinishCommand.MESSAGE_TASK_ALREADY_NOT_FINISHED;
import static seedu.doist.logic.commands.UnfinishCommand.MESSAGE_UNFINISH_TASK_SUCCESS;

import java.util.ArrayList;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.doist.model.TodoList;
import seedu.doist.testutil.TestTask;
import seedu.doist.testutil.TypicalTestTasks;

//@@author A0140887W
public class UnfinishCommandTest extends DoistGUITest {

    @Override
    protected TodoList getInitialData() {
        TodoList ab = new TodoList();
        TypicalTestTasks.loadDoistWithSampleDataAllFinished(ab);
        return ab;
    }

    @Test
    public void finish() {

        //Unfinish the first in the list
        TestTask[] currentList = td.getAllFinishedTypicalTasks();
        int targetIndex = 1;
        assertFinishSuccess(targetIndex, currentList);

        //Unfinish from the middle of the list
        targetIndex = currentList.length / 2;
        assertFinishSuccess(targetIndex, currentList);

        //Unfinish the last in the list
        targetIndex = currentList.length;
        assertFinishSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("unfinish " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

        // Unfinish a task that is already not finished
        assertAlreadyUnfinished(1, currentList);
    }

    /**
     * Runs the finish command to finish the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to finish the first task in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertFinishSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask[] expectedTasks = currentList.clone();
        TestTask taskToUnfinish = expectedTasks[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        taskToUnfinish.setFinishedStatus(false);

        commandBox.runCommand("unfinish " + targetIndexOneIndexed);

        //confirm the list matching
        assertTrue(taskListPanel.isListMatching(expectedTasks));

        //confirm that UI is showing normal
        TaskCardHandle finishedCard = taskListPanel.getTaskCardHandle(taskToUnfinish);
        assertTrue(finishedCard.isStyleInStyleClass("normal") || finishedCard.isStyleInStyleClass("overdue"));

        //confirm the result message is correct
        ArrayList<TestTask> tasksToUnfinish = new ArrayList<TestTask>();
        tasksToUnfinish.add(taskToUnfinish);
        assertResultMessage(String.format(MESSAGE_UNFINISH_TASK_SUCCESS, tasksToUnfinish));
    }

    private void assertAlreadyUnfinished(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask[] expectedRemainder = currentList.clone();
        TestTask taskToUnfinish = expectedRemainder[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        taskToUnfinish.setFinishedStatus(false);

        commandBox.runCommand("unfinish " + targetIndexOneIndexed);

        //confirm the list matching
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        ArrayList<TestTask> tasksToUnfinish = new ArrayList<TestTask>();
        tasksToUnfinish.add(taskToUnfinish);
        assertResultMessage(String.format(MESSAGE_TASK_ALREADY_NOT_FINISHED, tasksToUnfinish) + "\n");
    }

}
