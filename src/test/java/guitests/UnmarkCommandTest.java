package guitests;

import static org.junit.Assert.assertTrue;
import static savvytodo.logic.commands.UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS;

import java.util.LinkedList;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import savvytodo.model.task.Status;
import savvytodo.testutil.TestTask;;


//@@author A0140016B
public class UnmarkCommandTest extends TaskManagerGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertMarkSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void umark() {

        //unmark the first in the list
        int targetIndex = 1;
        TestTask umarkedTask = expectedTasksList[targetIndex - 1];
        umarkedTask.setCompleted(new Status());
        assertMarkSuccess(targetIndex, targetIndex, umarkedTask);

        //unmark the last in the list
        targetIndex = expectedTasksList.length;
        TestTask umarkedTask2 = expectedTasksList[targetIndex - 1];
        umarkedTask2.setCompleted(new Status());
        assertMarkSuccess(targetIndex, targetIndex, umarkedTask2);

        //unmark from the middle of the list
        targetIndex = expectedTasksList.length / 2;
        TestTask umarkedTask3 = expectedTasksList[targetIndex - 1];
        umarkedTask3.setCompleted(new Status());
        assertMarkSuccess(targetIndex, targetIndex, umarkedTask3);

        //invalid index
        commandBox.runCommand("unmark " + expectedTasksList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    @Test
    public void unmarkMultiple() {

        LinkedList<Integer> targetIndices = new LinkedList<Integer>();
        LinkedList<TestTask> unmarkedTasks = new LinkedList<TestTask>();

        //mark the first in the list
        int targetIndex = 1;
        TestTask unmarkedTask = expectedTasksList[targetIndex - 1];
        unmarkedTask.setCompleted(new Status());
        targetIndices.add(targetIndex);
        unmarkedTasks.add(unmarkedTask);

        //mark the last in the list
        targetIndex = expectedTasksList.length;
        TestTask unmarkedTask2 = expectedTasksList[targetIndex - 1];
        unmarkedTask2.setCompleted(new Status());
        targetIndices.add(targetIndex);
        unmarkedTasks.add(unmarkedTask2);

        //mark from the middle of the list
        targetIndex = expectedTasksList.length / 2;
        TestTask unmarkedTask3 = expectedTasksList[targetIndex - 1];
        unmarkedTask3.setCompleted(new Status());
        targetIndices.add(targetIndex);
        unmarkedTasks.add(unmarkedTask3);

        assertUnmarkMultipleSuccess(targetIndices, unmarkedTasks);

    }

    /**
     * Checks whether the unmarked tasks has the correct updated details.
     *
     * @param targetIndices
     *            indices of task to unmark in filtered list
     * @param unmarkedTasks
     *            the expected task after unmarking the task's details
     */
    private void assertUnmarkMultipleSuccess(LinkedList<Integer> targetIndices, LinkedList<TestTask> unmarkedTasks) {
        StringBuilder indices = new StringBuilder();

        for (Integer unmarkedTaskIndex : targetIndices) {
            indices.append(unmarkedTaskIndex  + " ");
        }
        commandBox.runCommand("mark " + indices);
        commandBox.runCommand("unmark " + indices);


        StringBuilder resultSb = new StringBuilder();

        for (TestTask unmarkedTask : unmarkedTasks) {
            // confirm the new card contains the right data
            TaskCardHandle editedCard = taskListPanel.navigateToTask(unmarkedTask.getName().name);
            assertMatching(unmarkedTask, editedCard);

            expectedTasksList[targetIndices.peek() - 1] = unmarkedTask;
            resultSb.append(String.format(MESSAGE_UNMARK_TASK_SUCCESS, targetIndices.pop()));
        }

        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(resultSb.toString());
    }

    /**
     * Checks whether the unmarked task has the correct updated details.
     *
     * @param filteredTaskListIndex
     *            index of task to  in filtered list
     * @param umarkedTaskIndex
     *            index of task to unmark in the task manager. Must refer to the
     *            same task as {@code filteredTaskListIndex}
     * @param detailsToMark
     *            details to unmark the task with as input to the unmark command
     * @param umarkedTask
     *            the expected task after unmarking the task's details
     */
    private void assertMarkSuccess(int filteredTaskListIndex, int umarkedTaskIndex, TestTask umarkedTask) {
        commandBox.runCommand("mark " + filteredTaskListIndex);
        commandBox.runCommand("unmark " + filteredTaskListIndex);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(umarkedTask.getName().name);
        assertMatching(umarkedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with
        // updated details
        expectedTasksList[umarkedTaskIndex - 1] = umarkedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(MESSAGE_UNMARK_TASK_SUCCESS, umarkedTaskIndex));
    }

}
//@@author A0140016B
