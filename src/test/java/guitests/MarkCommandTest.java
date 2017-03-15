package guitests;

import static org.junit.Assert.assertTrue;
import static werkbook.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import werkbook.task.commons.core.Messages;
import werkbook.task.logic.commands.MarkCommand;
import werkbook.task.testutil.TaskBuilder;
import werkbook.task.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class MarkCommandTest extends TaskListGuiTest {

    // The list of tasks in the task list panel is expected to match this
    // list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTaskList = td.getTypicalTasks();

    @Test
    public void mark_task_success() throws Exception {
        int taskListIndex = 1;

        TestTask markedTask = new TaskBuilder().withName("Walk the dog")
                .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                .withEndDateTime("01/01/2016 1000").withTags("Complete").build();

        assertMarkSuccess(taskListIndex, taskListIndex, markedTask, true);
    }

    @Test
    public void unmark_task_success() throws Exception {
        int taskListIndex = 1;

        TestTask markedTask = new TaskBuilder().withName("Walk the dog")
                .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                .withEndDateTime("01/01/2016 1000").withTags("Complete").build();

        assertMarkSuccess(taskListIndex, taskListIndex, markedTask, true);

        // Now mark again, Complete should revert back to incomplete
        markedTask = new TaskBuilder().withName("Walk the dog")
                .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                .withEndDateTime("01/01/2016 1000").withTags("Incomplete").build();

        assertMarkSuccess(taskListIndex, taskListIndex, markedTask, false);
    }

    @Test
    public void mark_findThenMark_success() throws Exception {
        commandBox.runCommand("find fish");

        int filteredTaskListIndex = 1;
        int taskListIndex = 5;

        TestTask taskToEdit = expectedTaskList[taskListIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withName("Walk the fish")
                .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                .withEndDateTime("01/01/2016 1000").withTags("Incomplete").build();

        assertMarkSuccess(filteredTaskListIndex, taskListIndex, editedTask, false);
    }

    @Test
    public void mark_invalidCommand_failure() {
        commandBox.runCommand("mark This task");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void mark_invalidTaskIndex_failure() {
        commandBox.runCommand("mark 20");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Checks whether the marked task has the correct updated status.
     *
     * @param filteredTaskListIndex index of task to mark in filtered list
     * @param taskListIndex index of task to mark in the task list. Must refer
     *            to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to mark the task with as input to the edit
     *            command
     * @param markedTask the expected task after editing the task's details
     */
    private void assertMarkSuccess(int filteredTaskListIndex, int taskListIndex, TestTask markedTask,
            boolean hasMarked) {
        commandBox.runCommand("mark " + filteredTaskListIndex);

        // confirm the new card contains the right data
        TaskCardHandle existingTask = taskListPanel.navigateToTask(markedTask.getName().toString());
        assertMatching(markedTask, existingTask);

        // confirm the list now contains all previous tasks plus the task
        // with updated details
        expectedTaskList[taskListIndex - 1] = markedTask;
        assertTrue(taskListPanel.isListMatching(expectedTaskList));

        if (hasMarked) {
            assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, markedTask.getName()));
        } else {
            assertResultMessage(String.format(MarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, markedTask.getName()));
        }
    }
}
