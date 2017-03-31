package guitests;

import static org.junit.Assert.assertTrue;
import static savvytodo.logic.commands.MarkCommand.MESSAGE_MARK_TASK_SUCCESS;

import java.util.LinkedList;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import savvytodo.model.task.Status;
import savvytodo.testutil.TestTask;

//@@author A0140016B
public class MarkCommandTest extends TaskManagerGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertMarkSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void mark() {

        //mark the first in the list
        int targetIndex = 1;
        TestTask markedTask = expectedTasksList[targetIndex - 1];
        markedTask.setCompleted(new Status(true));
        assertMarkSuccess(targetIndex, targetIndex, markedTask);

        //mark the last in the list
        targetIndex = expectedTasksList.length;
        TestTask markedTask2 = expectedTasksList[targetIndex - 1];
        markedTask2.setCompleted(new Status(true));
        assertMarkSuccess(targetIndex, targetIndex, markedTask2);

        //mark from the middle of the list
        targetIndex = expectedTasksList.length / 2;
        TestTask markedTask3 = expectedTasksList[targetIndex - 1];
        markedTask3.setCompleted(new Status(true));
        assertMarkSuccess(targetIndex, targetIndex, markedTask3);

        //invalid index
        commandBox.runCommand("mark " + expectedTasksList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    @Test
    public void markMultiple() {

        LinkedList<Integer> targetIndices = new LinkedList<Integer>();
        LinkedList<TestTask> markedTasks = new LinkedList<TestTask>();

        //mark the first in the list
        int targetIndex = 1;
        TestTask markedTask = expectedTasksList[targetIndex - 1];
        markedTask.setCompleted(new Status(true));
        targetIndices.add(targetIndex);
        markedTasks.add(markedTask);

        //mark the last in the list
        targetIndex = expectedTasksList.length;
        TestTask markedTask2 = expectedTasksList[targetIndex - 1];
        markedTask2.setCompleted(new Status(true));
        targetIndices.add(targetIndex);
        markedTasks.add(markedTask2);

        //mark from the middle of the list
        targetIndex = expectedTasksList.length / 2;
        TestTask markedTask3 = expectedTasksList[targetIndex - 1];
        markedTask3.setCompleted(new Status(true));
        targetIndices.add(targetIndex);
        markedTasks.add(markedTask3);

        assertMarkMultipleSuccess(targetIndices, markedTasks);

    }

    /**
     * Checks whether the marked tasks has the correct updated details.
     *
     * @param targetIndices
     *            indices of task to mark in filtered list
     * @param markedTasks
     *            the expected task after marking the task's details
     */
    private void assertMarkMultipleSuccess(LinkedList<Integer> targetIndices, LinkedList<TestTask> markedTasks) {
        StringBuilder indices = new StringBuilder();

        for (Integer markedTaskIndex : targetIndices) {
            indices.append(markedTaskIndex  + " ");
        }
        commandBox.runCommand("mark " + indices);


        StringBuilder resultSb = new StringBuilder();

        for (TestTask markedTask : markedTasks) {
            // confirm the new card contains the right data
            TaskCardHandle editedCard = taskListPanel.navigateToTask(markedTask.getName().name);
            assertMatching(markedTask, editedCard);

            expectedTasksList[targetIndices.peek() - 1] = markedTask;
            resultSb.append(String.format(MESSAGE_MARK_TASK_SUCCESS, targetIndices.pop()));
        }

        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(resultSb.toString());
    }

    /**
     * Checks whether the marked task has the correct updated details.
     *
     * @param filteredTaskListIndex
     *            index of task to mark in filtered list
     * @param markedTaskIndex
     *            index of task to mark in the task manager. Must refer to the
     *            same task as {@code filteredTaskListIndex}
     * @param detailsToMark
     *            details to mark the task with as input to the mark command
     * @param markedTask
     *            the expected task after marking the task's details
     */
    private void assertMarkSuccess(int filteredTaskListIndex, int markedTaskIndex, TestTask markedTask) {
        commandBox.runCommand("mark " + filteredTaskListIndex);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(markedTask.getName().name);
        assertMatching(markedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with
        // updated details
        expectedTasksList[markedTaskIndex - 1] = markedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(MESSAGE_MARK_TASK_SUCCESS, markedTaskIndex));
    }

}
//@@author A0140016B
