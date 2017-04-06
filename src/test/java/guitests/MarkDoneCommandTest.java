package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.logic.commands.MarkDoneCommand;
import seedu.taskboss.model.task.Recurrence.Frequency;
import seedu.taskboss.testutil.TaskBuilder;
import seedu.taskboss.testutil.TestTask;

//@@author A0144904H
public class MarkDoneCommandTest extends TaskBossGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    //---------------- Tests for validity of input taskBoss index --------------------------------------

    /*
     * EP: valid task index, should remove all
     * task's current categories and add category "Done" in their place.
     *
     * Should return true.
     */

    @Test
    public void markTaskDone_success() throws Exception {
        int taskBossIndex = 1;

        TestTask markedDoneTask = new TaskBuilder().withName("Clean house").withPriorityLevel("Yes")
                .withStartDateTime("Feb 19, 2017 11pm")
                .withEndDateTime("Feb 28, 2017 5pm")
                .withInformation("wall street").withRecurrence(Frequency.NONE)
                .withCategories("Done").build();

        assertMarkDoneSuccess(false, false, taskBossIndex, taskBossIndex, markedDoneTask);
    }

    @Test
    public void markTaskDone_MultipleCategories_success() throws Exception {
        int taskBossIndex = 5;

        TestTask markedDoneTask = new TaskBuilder().withName("Birthday party")
                .withInformation("311, Clementi Ave 2, #02-25")
                .withPriorityLevel("No")
                .withRecurrence(Frequency.NONE)
                .withStartDateTime("Feb 23, 2017 10pm")
                .withEndDateTime("Jun 28, 2017 5pm")
                .withCategories("Done", "Friends", "Owesmoney").build();

        assertMarkDoneSuccess(false, false, taskBossIndex, taskBossIndex, markedDoneTask);
    }

    /*
     * Invalid index equivalence partitions for : 1) missing index
     * 2) index invalid for not existing in task list.
     *
     * The two test cases below test one invalid index input type at a time
     * for each of the two invalid possible cases.
     */

    /*
     * EP: invalid task index where index was not entered and is therefore missing,
     * should not any of the non-recurring task's
     * current categories and will not add category "Done". Or,
     * update the dates of the recurring tasks.
     *
     * Should show error message that command entered was in the wrong format
     * and an index should be entered.
     *
     * Should return false.
     */

    @Test
    public void markDone_missingTaskIndex_failure() {
        commandBox.runCommand("mark ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));
    }

    /*
     * EP: invalid task index where the index entered does
     * not exist in current task list, should not remove any of the task's
     * current categories and will not add category "Done".
     *
     * Should show error message that index entered is invalid.
     *
     * Should return false.
     */

    @Test
    public void markDone_invalidTaskIndex_failure() {
        commandBox.runCommand("mark 9");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    //---------------- Tests for format of Done command --------------------------------------

    /*
     * Valid format equivalence partitions for : 1) short command format
     * 2) long command format
     *
     * The two test cases below test one valid command format at a time
     * for each of the two valid possible command formats.
     */

    /*
     * EP: valid format using long command, should remove all task's
     * current categories and add category "Done" in their place.
     *
     * Should return true.
     */

    @Test
    public void markTaskDoneLong_success() throws Exception {
        int taskBossIndex = 1;

        TestTask markedDoneTask = new TaskBuilder().withName("Clean house").withPriorityLevel("Yes")
                .withStartDateTime("Feb 19, 2017 11pm")
                .withEndDateTime("Feb 28, 2017 5pm")
                .withInformation("wall street").withRecurrence(Frequency.NONE)
                .withCategories("Done").build();

        assertMarkDoneSuccess(false, false, taskBossIndex, taskBossIndex, markedDoneTask);
    }

    /*
     * EP: valid format using short command, should remove all task's
     * current categories and add category "Done" in their place.
     *
     * Should return true.
     */

    @Test
    public void markTaskDone_Short_Command_success() throws Exception {
        int taskBossIndex = 4;

        TestTask markedDoneTask = new TaskBuilder().withName("Debug code").withPriorityLevel("Yes")
                .withStartDateTime("Feb 20, 2017 11.30pm")
                .withEndDateTime("Apr 28, 2017 3pm")
                .withInformation("10th street").withRecurrence(Frequency.NONE)
                .withCategories("Done").build();

        assertMarkDoneSuccess(false, true, taskBossIndex, taskBossIndex, markedDoneTask);
    }

    //---------------- Tests for successfully marking done a task after find command------------------------

    /*
     * EP: Check if successfully marked done a command after performing a find command,
     * should should remove all task's current categories and add category "Done" in their place.
     * Should return true.
     */

    @Test
    public void markDone_findThenMarkDone_success() throws Exception {
        commandBox.runCommand("find Clean house");

        int filteredTaskListIndex = 1;
        int taskBossIndex = 1;

        TestTask taskToMarkDone = expectedTasksList[taskBossIndex - 1];
        TestTask markedDoneTask = new TaskBuilder(taskToMarkDone).withCategories("Done").build();

        assertMarkDoneSuccess(true, false, filteredTaskListIndex, taskBossIndex, markedDoneTask);
    }

    //---------------- Tests for successfully marking done multiple tasks-----------------------

    /*
     * EP: Check if successfully marked multiple tasks done,
     * should should remove all task's current categories and add category "Done" in their place.
     * Recurring tasks' dates will be updated
     * Should return true.
     */
    @Test
    public void multiple_markTaskDone_Long_Command_success() throws Exception {
        commandBox.runCommand("mark 2 4");

        expectedTasksList[1] = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Mar 22, 2017 5pm")
                .withEndDateTime("Mar 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave").build();

        expectedTasksList[3] = new TaskBuilder().withName("Debug code").withPriorityLevel("Yes")
                .withStartDateTime("Feb 20, 2017 11.30pm")
                .withEndDateTime("Apr 28, 2017 3pm")
                .withRecurrence(Frequency.NONE)
                .withInformation("10th street")
                .withCategories("Done").build();

        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(MarkDoneCommand.MESSAGE_MARK_TASK_DONE_SUCCESS , "1. " +
                expectedTasksList[1] + "\n" + "2. " + expectedTasksList[3]));
    }

    @Test
    public void multiple_SpacesInBetween_markTaskDone_Short_Command_success() throws Exception {
        commandBox.runCommand("m 2       4 ");
        expectedTasksList[1] = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Mar 22, 2017 5pm")
                .withEndDateTime("Mar 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave").build();
        expectedTasksList[3] = new TaskBuilder().withName("Debug code").withPriorityLevel("Yes")
                .withStartDateTime("Feb 20, 2017 11.30pm")
                .withEndDateTime("Apr 28, 2017 3pm")
                .withRecurrence(Frequency.NONE)
                .withInformation("10th street")
                .withCategories("Done").build();

        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(MarkDoneCommand.MESSAGE_MARK_TASK_DONE_SUCCESS , "1. " +
                expectedTasksList[1] + "\n" + "2. " + expectedTasksList[3]));
    }

    @Test
    public void multiple_markTaskDone_Command_InvalidIndex() {
        commandBox.runCommand("m 1 2 100");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand("mark 0 2 3");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        //user inputs non-numeric index values
        commandBox.runCommand("mark a 2 3");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));

        commandBox.runCommand("mark ; 2 3");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));
    }

    //---------------- End of test cases --------------------------------------

    private void assertMarkDoneSuccess(boolean runFind, boolean isShort, int filteredTaskListIndex, int taskBossIndex,
            TestTask markedDoneTask) {

        if (isShort) {
            commandBox.runCommand("m " + filteredTaskListIndex);
        } else {
            commandBox.runCommand("mark " + filteredTaskListIndex);
        }

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskBossIndex - 1] = markedDoneTask;
        if (runFind) {
            commandBox.runCommand("list ");
            assertTrue(taskListPanel.isListMatching(expectedTasksList));
            assertResultMessage("Listed all tasks");
        } else {
            assertTrue(taskListPanel.isListMatching(expectedTasksList));
            assertResultMessage(String.format(MarkDoneCommand.MESSAGE_MARK_TASK_DONE_SUCCESS ,
                    "1. " + markedDoneTask));
        }

        assertTrue(taskListPanel.isListMatching(expectedTasksList));
    }
}

