package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.tasklist.logic.commands.DoneCommand;
import seedu.tasklist.testutil.DeadlineTaskBuilder;
import seedu.tasklist.testutil.FloatingTaskBuilder;
import seedu.tasklist.testutil.TestTask;

/**
 * Tests the done Command for 3 types of task
 * @author A0143355J
 */
public class DoneCommandTest extends TaskListGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertDoneSuccess.
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void done_FloatingTask_success() throws Exception {
        int doneTaskIndex = 3;

        TestTask doneTask = new FloatingTaskBuilder().
                withName("Buy groceries").
                withComment("go NTUC").
                withPriority("low").
                withStatus(true).
                build();
        assertDoneSuccess(doneTaskIndex, doneTaskIndex, doneTask);
    }

    @Test
    public void done_DeadlineTask_success() throws Exception {
        int doneTaskIndex = 5;

        TestTask doneTask = new DeadlineTaskBuilder().
                withName("Implement undo for this").
                withDeadline("15/03/2017 18:00:10").
                withComment("By today").
                withPriority("high").
                withStatus(true).
                build();
        assertDoneSuccess(doneTaskIndex, doneTaskIndex, doneTask);
    }


    /**
     * Runs done command to mark the task at the specified index as completed
     * Confirms the result is correct
     * @param filteredTaskListIndex index of task to mark as complete in filtered list
     * @param currentList index of task to mark as complete in the address book.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param doneTask is the expected task after marking a task as completed
     */
    private void assertDoneSuccess(int filteredTaskListIndex, int taskListIndex, TestTask doneTask) {
        commandBox.runCommand("done " + filteredTaskListIndex);

        //Confirms the new card is marked as done
        TaskCardHandle doneCard = taskListPanel.navigateToTask(doneTask.getName().fullName);
        assertMatching(doneTask, doneCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskListIndex - 1] = doneTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, doneTask));
    }
}
