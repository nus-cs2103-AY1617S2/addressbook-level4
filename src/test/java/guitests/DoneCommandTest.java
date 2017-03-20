package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.tasklist.logic.commands.DoneCommand;
import seedu.tasklist.testutil.TestTask;
import seedu.tasklist.testutil.TestUtil;


/**
 * Tests the done Command for 3 types of task
 * @author A0143355J
 */
public class DoneCommandTest extends TaskListGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertDoneSuccess.
    TestTask[] expectedTasksList = td.getTypicalTasks();


    /**
     * Runs done command to mark the task at the specified index as completed
     * Confirms the result is correct
     * @param filteredTaskListIndex index of task to mark as complete in filtered list
     * @param currentList index of task to mark as complete in the address book.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param doneTask is the expected task after marking a task as completed
     */
    private void assertDoneSuccess(int filteredTaskListIndex, int taskListIndex, TestTask doneTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex);

        //Confirms the new card is marked as done
        TaskCardHandle editedCard = taskListPanel.getTaskCardHandle(filteredTaskListIndex);
        assertMatching(doneTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskListIndex - 1] = doneTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, doneTask));
    }
}
