package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.logic.commands.TerminateCommand;
import seedu.taskboss.model.task.Recurrence.Frequency;
import seedu.taskboss.testutil.TaskBuilder;
import seedu.taskboss.testutil.TestTask;

//@@author A0144904H
public class TerminateCommandTest extends TaskBossGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    private TestTask[] expectedTasksList = td.getTypicalTasks();

  //---------------- Tests for validity of input taskBoss index --------------------------------------

    /*
     * EP: valid task index, should remove all
     * task's current categories and add category "Done" in their place.
     *
     * Should return true.
     */

    @Test
    public void terminateTask_success() throws Exception {
        int taskBossIndex = 2;

        TestTask terminatedTask = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Feb 22, 2017 5pm")
                .withEndDateTime("Feb 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave")
                .withCategories("Done").build();

        assertTerminateSuccess(false, false, taskBossIndex, taskBossIndex, terminatedTask);
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
     * should not any of the task's
     * current categories and will not add category "Done".
     *
     * Should show error message that command entered was in the wrong format
     * and an index should be entered.
     *
     * Should return false.
     */

    @Test
    public void terminate_missingTaskIndex_failure() {
        commandBox.runCommand("terminate ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TerminateCommand.MESSAGE_USAGE));
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
    public void terminate_invalidTaskIndex_failure() {
        commandBox.runCommand("t 9");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

  //---------------- Tests for format of terminate command --------------------------------------

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
    public void terminateTask_Long_success() throws Exception {
        int taskBossIndex = 2;

        TestTask terminatedTask = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Feb 22, 2017 5pm")
                .withEndDateTime("Feb 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave")
                .withCategories("Done").build();

        assertTerminateSuccess(false, false, taskBossIndex, taskBossIndex, terminatedTask);
    }

    /*
     * EP: valid format using short command, should remove all task's
     * current categories and add category "Done" in their place.
     *
     * Should return true.
     */

    @Test
    public void terminateTask_Short_Command_success() throws Exception {
        int taskBossIndex = 6;

        TestTask terminatedTask = new TaskBuilder().withName("Game project player testing").withPriorityLevel("Yes")
                .withStartDateTime("Jan 1, 2017 5pm")
                .withEndDateTime("Nov 28, 2017 5pm")
                .withRecurrence(Frequency.DAILY)
                .withInformation("4th street")
                .withCategories("Done").build();

        assertTerminateSuccess(false, true, taskBossIndex, taskBossIndex, terminatedTask);
    }

  //---------------- Tests for inputing wrong task type--------------------------------------------------------

    /*
     * EP: Check if successfully showed error message when task is non-recurring.
     */
    @Test
    public void task_NotRecurring_failure() {
        commandBox.runCommand("t 1");
        assertResultMessage(TerminateCommand.ERROR_TASK_NOT_RECURRING);
    }

  //---------------- Tests for successfully ending a task after find command--------------------------------------

    /*
     * EP: Check if successfully end a recurring task after performing a find command,
     * should should remove all task's current categories and add category "Done" in their place.
     * Should return true.
     */

    @Test
    public void termiateTask_findThenTerminate_success() throws Exception {
        commandBox.runCommand("find ensure");

        int filteredTaskListIndex = 1;
        int taskBossIndex = 2;

        TestTask taskToTerminate = expectedTasksList[taskBossIndex - 1];
        TestTask terminatedTask = new TaskBuilder(taskToTerminate).withCategories("Done").build();

        assertTerminateSuccess(true, false, filteredTaskListIndex, taskBossIndex, terminatedTask);
    }

    //---------------- Tests for successfully ending multiple recurring tasks-----------------------

    @Test
    public void multiple_Terminate_Long_Command_success() throws Exception {
        commandBox.runCommand("terminate 2 7");

        expectedTasksList[1] = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Feb 22, 2017 5pm")
                .withEndDateTime("Feb 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave")
                .withCategories("Done").build();

        expectedTasksList[6] = new TaskBuilder().withName("Fix errors in report").withPriorityLevel("No")
                .withStartDateTime("Feb 21, 2017 1pm")
                .withEndDateTime("Dec 10, 2017 5pm")
                .withRecurrence(Frequency.WEEKLY)
                .withInformation("little tokyo")
                .withCategories("School", "Done").build();

        String expectedMessage = "[" + expectedTasksList[1] + ", " + expectedTasksList[6] + "]";
        assertResultMessage(String.format(TerminateCommand.MESSAGE_MARK_RECURRING_TASK_DONE_SUCCESS , expectedMessage));
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
    }

    @Test
    public void multiple_SpacesInBetween_Terminate_Short_Command_success() throws Exception {
        commandBox.runCommand("t 2       6 ");
        expectedTasksList[1] = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Feb 22, 2017 5pm")
                .withEndDateTime("Feb 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave")
                .withCategories("Done").build();
        expectedTasksList[5] = new TaskBuilder().withName("Game project player testing").withPriorityLevel("Yes")
                .withStartDateTime("Jan 1, 2017 5pm")
                .withEndDateTime("Nov 28, 2017 5pm")
                .withRecurrence(Frequency.DAILY)
                .withInformation("4th street")
                .withCategories("Done").build();

        assertTrue(taskListPanel.isListMatching(expectedTasksList));
    }

    @Test
    public void multiple_Terminate_Command_InvalidIndex() {
        commandBox.runCommand("t 1 2 100");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand("t 0 2 3");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    //---------------- End of test cases --------------------------------------

    private void assertTerminateSuccess(boolean runFind, boolean isShort, int filteredTaskListIndex, int taskBossIndex,
            TestTask terminatedTask) {

        //@@author A0144904H
        if (isShort) {
            commandBox.runCommand("t " + filteredTaskListIndex);
        } else {
            commandBox.runCommand("terminate " + filteredTaskListIndex);
        }

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskBossIndex - 1] = terminatedTask;
        if (runFind) {
            commandBox.runCommand("list ");
            assertTrue(taskListPanel.isListMatching(expectedTasksList));
            assertResultMessage("Listed all tasks");
        } else {
            assertTrue(taskListPanel.isListMatching(expectedTasksList));
            assertResultMessage(String.format(TerminateCommand.MESSAGE_MARK_RECURRING_TASK_DONE_SUCCESS ,
                            "[" + terminatedTask + "]"));
        }

        assertTrue(taskListPanel.isListMatching(expectedTasksList));
    }
}
