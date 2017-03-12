package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.logic.commands.MarkDoneCommand;
import seedu.taskboss.testutil.TaskBuilder;
import seedu.taskboss.testutil.TestTask;

public class MarkDoneCommandTest extends TaskBossGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void markTaskDone_success() throws Exception {
        int taskBossIndex = 8;

        TestTask markedDoneTask = new TaskBuilder().withName("Submit progress report")
               .withPriorityLevel("3").withStartDateTime("Feb 9, 2017 5pm")
               .withEndDateTime("10am Feb 24, 2017 5pm")
               .withInformation("notify department head")
               .withCategories("Done").build();

        assertMarkDoneSuccess(taskBossIndex, taskBossIndex, markedDoneTask);
    }

    @Test
    public void markDone_findThenMarkDone_success() throws Exception {
        commandBox.runCommand("find n/Submit progress report");

        int filteredTaskListIndex = 1;
        int taskBossIndex = 8;

        TestTask taskToMarkDone = expectedTasksList[taskBossIndex - 1];
        TestTask markedDoneTask = new TaskBuilder(taskToMarkDone).withCategories("Done").build();

        assertMarkDoneSuccess(filteredTaskListIndex, taskBossIndex, markedDoneTask);
    }

    @Test
    public void markDone_missingTaskIndex_failure() {
        commandBox.runCommand("done ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void markDone_invalidTaskIndex_failure() {
        commandBox.runCommand("done 8");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private void assertMarkDoneSuccess(int filteredTaskListIndex, int taskBossIndex,
            TestTask markedDoneTask) {
        commandBox.runCommand("done " + filteredTaskListIndex);


        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskBossIndex - 1] = markedDoneTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(MarkDoneCommand.MESSAGE_MARK_TASK_DONE_SUCCESS , markedDoneTask));
    }

}
