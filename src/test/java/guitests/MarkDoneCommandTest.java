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
        int taskBossIndex = 1;

        TestTask markedDoneTask = new TaskBuilder().withName("Carl Kurz").withPriorityLevel("Yes")
                .withStartDateTime("Feb 19, 2017 11pm")
                .withEndDateTime("Feb 28, 2017 5pm")
                .withInformation("wall street").withCategories("Done").build();

        assertMarkDoneSuccess(false, taskBossIndex, taskBossIndex, markedDoneTask);
    }

    //@@author A0144904H
    @Test
    public void markTaskDone_Short_Command_success() throws Exception {
        int taskBossIndex = 4;

        TestTask markedDoneTask = new TaskBuilder().withName("Daniel Meier").withPriorityLevel("Yes")
                .withStartDateTime("Feb 20, 2017 11.30pm")
                .withEndDateTime("Apr 28, 2017 3pm")
                .withInformation("10th street").withCategories("Done").build();

        assertMarkDoneSuccess(true, taskBossIndex, taskBossIndex, markedDoneTask);
    }

    @Test
    public void markDone_findThenMarkDone_success() throws Exception {
        commandBox.runCommand("find n/Carl");

        int filteredTaskListIndex = 1;
        int taskBossIndex = 1;

        TestTask taskToMarkDone = expectedTasksList[taskBossIndex - 1];
        TestTask markedDoneTask = new TaskBuilder(taskToMarkDone).withCategories("Done").build();

        assertMarkDoneSuccess(false, filteredTaskListIndex, taskBossIndex, markedDoneTask);
    }

    @Test
    public void markDone_missingTaskIndex_failure() {
        commandBox.runCommand("mark ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void markDone_invalidTaskIndex_failure() {
        commandBox.runCommand("mark 9");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private void assertMarkDoneSuccess(boolean isShort, int filteredTaskListIndex, int taskBossIndex,
            TestTask markedDoneTask) {

        //@@author A0144904H
        if (isShort) {
            commandBox.runCommand("m " + filteredTaskListIndex);
        } else {
            commandBox.runCommand("mark " + filteredTaskListIndex);
        }

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskBossIndex - 1] = markedDoneTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(MarkDoneCommand.MESSAGE_MARK_TASK_DONE_SUCCESS , markedDoneTask));
    }

}
