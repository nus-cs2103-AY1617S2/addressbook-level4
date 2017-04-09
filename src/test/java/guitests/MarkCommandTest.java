package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.opus.logic.commands.EditCommand;
import seedu.opus.testutil.TaskBuilder;
import seedu.opus.testutil.TestTask;

//@@author A0124368A
public class MarkCommandTest extends TaskManagerGuiTest {

    private TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void markTestSuccess() throws Exception {
        int taskManagerIndex = 1;

        TestTask taskToMark = expectedTasksList[taskManagerIndex - 1];
        TestTask markedTask = new TaskBuilder(taskToMark).withStatus("complete")
                .withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00").build();

        // toggles to complete on the first mark
        assertMarkSuccess(taskManagerIndex, taskManagerIndex, markedTask);

        // toggles to incomplete on the second mark
        int updatedIndex = taskListPanel.getTaskIndex(markedTask) + 1; // +1 because it is the index reflected in the UI
        taskToMark = expectedTasksList[updatedIndex - 1];
        TestTask unmarkedTask = new TaskBuilder(taskToMark).withStatus("incomplete")
                .withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00").build();

        assertMarkSuccess(updatedIndex, updatedIndex, unmarkedTask);
    }

    /**
     * Checks whether the marked task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list.
     * @param taskManagerIndex index of task to edit in the task manager.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param editedTask the expected task after editing the task's details.
     */
    private void assertMarkSuccess(int filteredTaskListIndex, int taskManagerIndex,
            TestTask markedTask) {
        commandBox.runCommand("mark " + filteredTaskListIndex);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(markedTask.getName().fullName);
        assertMatching(markedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        moveToEnd(filteredTaskListIndex - 1);
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, markedTask));
    }

    /** Moves an element at index of expectedTasksList to the end of the list */
    private void moveToEnd(int index) {
        TestTask temp;
        for (int i = index + 1; i < expectedTasksList.length; i++) {
            temp = expectedTasksList[i - 1];
            expectedTasksList[i - 1] = expectedTasksList[i];
            expectedTasksList[i] = temp;
        }
    }

}
//@@author
