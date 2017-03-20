package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.logic.commands.DoneCommand;
import seedu.tasklist.testutil.DeadlineTaskBuilder;
import seedu.tasklist.testutil.EventTaskBuilder;
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

    @Test
    public void done_EventTask_success() throws Exception {
        int doneTaskIndex = 1;

        TestTask doneTask = new EventTaskBuilder().
                withName("CS2103T tutorial").
                withStartDate("15/3/2017 15:00:10").
                withEndDate("15/3/2017 18:00:10").
                withComment("prepare V0.2 presentation").
                withPriority("high").
                withStatus(true).
                withTags("class").
                build();
        assertDoneSuccess(doneTaskIndex, doneTaskIndex, doneTask);
    }

    @Test
    public void done_findThenDone_success() throws Exception {
        commandBox.runCommand("find CS3245");
        int filteredTaskListIndex = 1;
        int doneTaskIndex = 2;

        TestTask doneTask = new FloatingTaskBuilder().
                withName("CS3245 homework 3").
                withComment("discuss with classmates").
                withPriority("high").
                withStatus(true).
                withTags("class").
                build();
        assertDoneSuccess(filteredTaskListIndex, doneTaskIndex, doneTask);
    }

    @Test
    public void done_missingIndex_failure() throws Exception {
        commandBox.runCommand("done");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void done_invalidIndex_failure() throws Exception {
        commandBox.runCommand("done 100");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
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
