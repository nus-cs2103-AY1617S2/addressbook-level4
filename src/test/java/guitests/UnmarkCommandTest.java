package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.logic.commands.UnmarkCommand;
import seedu.taskboss.model.task.Recurrence.Frequency;
import seedu.taskboss.testutil.TaskBuilder;
import seedu.taskboss.testutil.TestTask;

//@@author A0144904H
public class UnmarkCommandTest extends TaskBossGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    private TestTask[] expectedTasksList = td.getTypicalTasks();

  //---------------- Tests for validity of input taskBoss index --------------------------------------

    /*
     * EP: valid task index, should remove Done category
     * from the task's current categories.
     *
     * Should return true.
     */

    @Test
    public void unmarkTask_success() throws Exception {
        int taskBossIndex = 2;
        String commandType = "termination";

        TestTask terminatedTask = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Mar 22, 2017 5pm")
                .withEndDateTime("Mar 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave").build();

        assertUnmarkSuccess(commandType, false, false, taskBossIndex, taskBossIndex,
                terminatedTask);
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
     * should not affect any of the task's
     * current categories and will not remove the category "Done".
     *
     * Should show error message that command entered was in the wrong format
     * and an index should be entered.
     *
     * Should return false.
     */

    @Test
    public void unmark_missingTaskIndex_failure() {
        commandBox.runCommand("unmark ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
    }

    /*
     * EP: invalid task index where the index entered does
     * not exist in current task list, should not affect any of the task's
     * current categories and will not remove the category "Done".
     *
     * Should show error message that index entered is invalid.
     *
     * Should return false.
     */

    @Test
    public void unmark_invalidTaskIndex_failure() {
        commandBox.runCommand("um 9");
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
     * EP: valid format using long command, should remove the Done category of all task's
     * current categories
     *
     * Should return true.
     */

    @Test
    public void unmarkTerminatedTask_Long_success() throws Exception {
        int taskBossIndex = 2;
        String commandType = "termination";

        TestTask terminatedTask = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Mar 22, 2017 5pm")
                .withEndDateTime("Mar 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave").build();

        assertUnmarkSuccess(commandType, false, false, taskBossIndex, taskBossIndex, terminatedTask);
    }

    @Test
    public void unmarkMarkedTask_Long_success() throws Exception {
        int taskBossIndex = 5;
        String commandType = "marking";

        TestTask terminatedTask = new TaskBuilder().withName("Birthday party")
                .withInformation("311, Clementi Ave 2, #02-25")
                .withPriorityLevel("No")
                .withRecurrence(Frequency.NONE)
                .withStartDateTime("Feb 23, 2017 10pm")
                .withEndDateTime("Jun 28, 2017 5pm")
                .withCategories("Alltasks", "Friends", "Owesmoney").build();

        assertUnmarkSuccess(commandType, false, false, taskBossIndex,
                taskBossIndex, terminatedTask);
    }

    /*
     * EP: valid format using short command, should remove the Done category of all task's
     * current categories
     *
     * Should return true.
     */

    @Test
    public void unmarkTask_Short_Command_success() throws Exception {
        int taskBossIndex = 4;
        String commandType = "marking";

        TestTask terminatedTask = new TaskBuilder().withName("Debug code").withPriorityLevel("Yes")
                .withStartDateTime("Feb 20, 2017 11.30pm")
                .withEndDateTime("Apr 28, 2017 3pm")
                .withRecurrence(Frequency.NONE)
                .withInformation("10th street").build();

        assertUnmarkSuccess(commandType, false, true, taskBossIndex,
                taskBossIndex, terminatedTask);
    }

  //---------------- Tests for inputing wrong task type--------------------------------------------------------

    /*
     * EP: Check if successfully showed error message when task was not marked or
     * terminated.
     */
    @Test
    public void task_NotMarked_failure() {
        commandBox.runCommand("um 1");
        assertResultMessage(UnmarkCommand.ERROR_NOT_MARKED);
    }

  //---------------- Tests for successfully unmarking a task after find command--------------------------------------

    /*
     * EP: Check if successfully unmark task after performing a find command,
     * should remove the Done category of all task's
     * current categories
     * Should return true.
     */

    @Test
    public void unmarkTask_findThenUnmark_success() throws Exception {
        commandBox.runCommand("find clean");
        String commandType = "marking";

        int filteredTaskListIndex = 1;
        int taskBossIndex = 1;

        TestTask taskToTerminate = expectedTasksList[taskBossIndex - 1];
        TestTask terminatedTask = new TaskBuilder(taskToTerminate).build();

        assertUnmarkSuccess(commandType, true, false, filteredTaskListIndex,
                taskBossIndex, terminatedTask);
    }

    //---------------- Tests for successfully unmakring multiple tasks-----------------------

    @Test
    public void multiple_unmark_Long_Command_success() throws Exception {
        commandBox.runCommand("t 2 7");
        commandBox.runCommand("unmark 2 7");

        expectedTasksList[1] = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Mar 22, 2017 5pm")
                .withEndDateTime("Mar 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave").build();

        expectedTasksList[6] = new TaskBuilder().withName("Fix errors in report").withPriorityLevel("No")
                .withStartDateTime("Feb 28, 2017 1pm")
                .withEndDateTime("Dec 17, 2017 5pm")
                .withRecurrence(Frequency.WEEKLY)
                .withInformation("little tokyo")
                .withCategories("School").build();

        String expectedMessage = "1. " + expectedTasksList[6] + "2. " + expectedTasksList[1];
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_DONE_SUCCESS , expectedMessage));
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
    }

    @Test
    public void multiple_SpacesInBetween_unmark_Short_Command_success() throws Exception {
        commandBox.runCommand("m 4 5");
        commandBox.runCommand("um 4           5");
        expectedTasksList[3] = new TaskBuilder().withName("Debug code").withPriorityLevel("Yes")
                .withStartDateTime("Feb 20, 2017 11.30pm")
                .withEndDateTime("Apr 28, 2017 3pm")
                .withRecurrence(Frequency.NONE)
                .withInformation("10th street").build();
        expectedTasksList[4] = new TaskBuilder().withName("Birthday party")
                .withInformation("311, Clementi Ave 2, #02-25")
                .withPriorityLevel("No")
                .withRecurrence(Frequency.NONE)
                .withStartDateTime("Feb 23, 2017 10pm")
                .withEndDateTime("Jun 28, 2017 5pm")
                .withCategories("Alltasks", "Friends", "Owesmoney").build();

        TestTask[] markedDone = new TestTask[] {expectedTasksList[4], expectedTasksList[3]};
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_DONE_SUCCESS,
                getDesiredFormat(markedDone)));
    }

    @Test
    public void multiple_Unmark_Command_InvalidIndex() {
        commandBox.runCommand("um 1 2 100");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand("um 0 2 3");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

      //user inputs non-numeric index values
        commandBox.runCommand("unmark a 2 3");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));

        commandBox.runCommand("unmark ; 2 3");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
    }

    //---------------- End of test cases --------------------------------------

    private void assertUnmarkSuccess(String commandType, boolean runFind, boolean isShort,
            int filteredTaskListIndex, int taskBossIndex,
            TestTask terminatedTask) {

        if (commandType.equals("termination")) {
            if (isShort) {
                commandBox.runCommand("t " + filteredTaskListIndex);
                commandBox.runCommand("um " + filteredTaskListIndex);
            } else {
                commandBox.runCommand("t " + filteredTaskListIndex);
                commandBox.runCommand("unmark " + filteredTaskListIndex);
            }
        } else {
            if (isShort) {
                commandBox.runCommand("m " + filteredTaskListIndex);
                commandBox.runCommand("um " + filteredTaskListIndex);
            } else {
                commandBox.runCommand("m " + filteredTaskListIndex);
                commandBox.runCommand("unmark " + filteredTaskListIndex);
            }
        }

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskBossIndex - 1] = terminatedTask;
        if (runFind) {
            commandBox.runCommand("list ");
            assertTrue(taskListPanel.isListMatching(expectedTasksList));
            assertResultMessage("Listed all tasks");
        } else {
            assertTrue(taskListPanel.isListMatching(expectedTasksList));
            assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_DONE_SUCCESS ,
                            "1. " + terminatedTask));
        }

        assertTrue(taskListPanel.isListMatching(expectedTasksList));
    }

    //@@author A0143157J
    /**
     * Returns a formatted {@code Array} tasksToMarkDone,
     * so that each TestTask in the Array is numbered
     */
    private String getDesiredFormat(TestTask[] markedDoneTask) {
        int indexOne = 1;
        String numberingDot = ". ";
        int i = indexOne;
        StringBuilder builder = new StringBuilder();
        for (TestTask task : markedDoneTask) {
            builder.append(i + numberingDot).append(task.toString());
            i++;
        }
        return builder.toString();
    }
}
