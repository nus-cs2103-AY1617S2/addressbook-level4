package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.logic.commands.UndoneCommand;
import seedu.tasklist.testutil.DeadlineTaskBuilder;
import seedu.tasklist.testutil.EventTaskBuilder;
import seedu.tasklist.testutil.FloatingTaskBuilder;
import seedu.tasklist.testutil.TestTask;

/**
 * Tests the undone Command for 3 types of task
 */
//@@Author A0143355J
public class UndoneCommandTest extends TaskListGuiTest {

    @Test
    public void undoneFloatingTaskSuccess() throws Exception {
        commandBox.runCommand("done 3");
        int undoneTaskIndex = 3;

        TestTask doneTask = new FloatingTaskBuilder().
                withName("Buy groceries").
                withComment("go NTUC").
                withPriority("low").
                withStatus(false).
                build();
        assertUndoneSuccess(undoneTaskIndex, undoneTaskIndex, doneTask);
    }

    @Test
    public void undoneDeadlineTaskSuccess() throws Exception {
        commandBox.runCommand("done 5");
        int undoneTaskIndex = 5;

        TestTask undoneTask = new DeadlineTaskBuilder().
                withName("Implement undo for this").
                withDeadline("15/03/2017 18:00:10").
                withComment("By today").
                withPriority("high").
                withStatus(false).
                build();
        assertUndoneSuccess(undoneTaskIndex, undoneTaskIndex, undoneTask);
    }

    @Test
    public void undoneEventTaskSuccess() throws Exception {
        commandBox.runCommand("done 1");
        int undoneTaskIndex = 1;

        TestTask undoneTask = new EventTaskBuilder().
                withName("CS2103T tutorial").
                withStartDate("15/3/2017 15:00:10").
                withEndDate("15/3/2017 18:00:10").
                withComment("prepare V0.2 presentation").
                withPriority("high").
                withStatus(false).
                withTags("2103", "class").
                build();
        assertUndoneSuccess(undoneTaskIndex, undoneTaskIndex, undoneTask);
    }

    @Test
    public void undoneFindThenUndoneSuccess() throws Exception {
        commandBox.runCommand("done 2");
        commandBox.runCommand("find CS3245");
        int filteredTaskListIndex = 1;
        int undoneTaskIndex = 2;

        TestTask undoneTask = new FloatingTaskBuilder().
                withName("CS3245 homework 3").
                withComment("discuss with classmates").
                withPriority("low").
                withStatus(false).
                withTags("class").
                build();
        assertUndoneSuccess(filteredTaskListIndex, undoneTaskIndex, undoneTask);
    }

    @Test
    public void undoneMissingIndexFailure() throws Exception {
        commandBox.runCommand("undone");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void undoneInvalidIndexFailure() throws Exception {
        commandBox.runCommand("undone 100");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void undoneAlreadyCompletedFailure() throws Exception {
        commandBox.runCommand("undone 4");
        assertResultMessage(UndoneCommand.MESSAGE_UNDONE_ERROR);
    }


    /**
     * Runs done command to mark the task at the specified index as undone
     * Confirms the result is correct
     * @param filteredTaskListIndex index of task to mark as complete in filtered list
     * @param currentList index of task to mark as complete in FlexiTask.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param undoneTask is the expected task after marking a task as undone
     */
    private void assertUndoneSuccess(int filteredTaskListIndex, int taskListIndex, TestTask undoneTask) {
        commandBox.runCommand("undone " + filteredTaskListIndex);

        //Confirms the new card is marked as undone
        TaskCardHandle doneCard = taskListPanel.navigateToTask(undoneTask.getName().fullName);
        assertMatching(undoneTask, doneCard);

        TestTask[] expectedTasksList = td.getTypicalTasks();

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskListIndex - 1] = undoneTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(UndoneCommand.MESSAGE_UNDONE_TASK_SUCCESS, undoneTask));
    }
}
