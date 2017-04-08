package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.logic.commands.AddCommand;
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
     * EP: valid task index,
     * should add category "Done" to the recurring task's current category list.
     * - test for long and short command formats
     * - test multiple and single terminate
     */

    //long command format
    //single terminate
    @Test
    public void terminateTask_validIndex_longCommandformat_success() throws Exception {
        int taskBossIndex = 2;

        TestTask terminatedTask = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Feb 22, 2017 5pm")
                .withEndDateTime("Feb 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave")
                .withCategories(AddCommand.BUILT_IN_DONE, AddCommand.BUILT_IN_ALL_TASKS).build();

        assertTerminateSuccess(false, taskBossIndex, taskBossIndex, terminatedTask, expectedTasksList);
    }

    //multiple terminate
    @Test
    public void multiple_terminate_validIndexes_longCommandformat_success() throws Exception {
        commandBox.runCommand("terminate 2 7");

        expectedTasksList[1] = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Feb 22, 2017 5pm")
                .withEndDateTime("Feb 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave")
                .withCategories(AddCommand.BUILT_IN_DONE, AddCommand.BUILT_IN_ALL_TASKS).build();

        expectedTasksList[6] = new TaskBuilder().withName("Fix errors in report").withPriorityLevel("No")
                .withStartDateTime("Feb 21, 2017 1pm")
                .withEndDateTime("Dec 10, 2017 5pm")
                .withRecurrence(Frequency.WEEKLY)
                .withInformation("little tokyo")
                .withCategories("School", AddCommand.BUILT_IN_DONE, AddCommand.BUILT_IN_ALL_TASKS).build();

        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        TestTask[] terminatedTasks = new TestTask[] {expectedTasksList[6], expectedTasksList[1]};
        assertResultMessage(String.format(TerminateCommand.MESSAGE_MARK_RECURRING_TASK_DONE_SUCCESS,
                getDesiredFormat(terminatedTasks)));
    }

    //short command format
    //single terminate
    @Test
    public void terminateTask_validIndex_shortCommandformat_success() throws Exception {
        int taskBossIndex = 2;

        TestTask terminatedTask = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Feb 22, 2017 5pm")
                .withEndDateTime("Feb 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave")
                .withCategories(AddCommand.BUILT_IN_DONE, AddCommand.BUILT_IN_ALL_TASKS).build();

        assertTerminateSuccess(true, taskBossIndex, taskBossIndex, terminatedTask, expectedTasksList);
    }

    //multiple terminate
    @Test
    public void multiple_terminate_validIndexes_shortCommandformat_success() throws Exception {
        commandBox.runCommand("t 2 7");

        expectedTasksList[1] = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Feb 22, 2017 5pm")
                .withEndDateTime("Feb 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave")
                .withCategories(AddCommand.BUILT_IN_DONE, AddCommand.BUILT_IN_ALL_TASKS).build();

        expectedTasksList[6] = new TaskBuilder().withName("Fix errors in report").withPriorityLevel("No")
                .withStartDateTime("Feb 21, 2017 1pm")
                .withEndDateTime("Dec 10, 2017 5pm")
                .withRecurrence(Frequency.WEEKLY)
                .withInformation("little tokyo")
                .withCategories("School", "Done", AddCommand.BUILT_IN_ALL_TASKS).build();

        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        TestTask[] terminatedTasks = new TestTask[] {expectedTasksList[6], expectedTasksList[1]};
        assertResultMessage(String.format(TerminateCommand.MESSAGE_MARK_RECURRING_TASK_DONE_SUCCESS,
                getDesiredFormat(terminatedTasks)));
    }


    /*
     * EP: missing task index,
     * should show error message: invalid command format
     * and display a message demonstrating the correct way to write the command
     */
    @Test
    public void terminate_missingTaskIndex_failure() {
        //long command format
        commandBox.runCommand("terminate ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TerminateCommand.MESSAGE_USAGE));

        //short command format
        commandBox.runCommand("t ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TerminateCommand.MESSAGE_USAGE));
    }

    /*
     * EP: invalid task index,
     * should show error message: invalid index
     * - test short and long command format
     * - test multiple and single terminate
     */

    //single terminate
    @Test
    public void terminate_invalidTaskIndex_failure() {

        //long command format

        commandBox.runCommand("terminate 9");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand("terminate -1");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // non-numeric inputs
        commandBox.runCommand("terminate ^");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TerminateCommand.MESSAGE_USAGE));

        commandBox.runCommand("terminate b");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TerminateCommand.MESSAGE_USAGE));

        //short command format

        commandBox.runCommand("t 10");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand("t 0");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // non-numeric inputs
        commandBox.runCommand("t ^");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TerminateCommand.MESSAGE_USAGE));

        commandBox.runCommand("t b");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TerminateCommand.MESSAGE_USAGE));
    }

    //multiple terminate
    @Test
    public void multiple_terminate_InvalidIndexes_failure() {
        //long command format
        commandBox.runCommand("terminate 1 2 100");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand("terminate 0 2 3");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        //user inputs non-numeric index values
        commandBox.runCommand("terminate a 2 3");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TerminateCommand.MESSAGE_USAGE));

        commandBox.runCommand("terminate ; 2 3");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TerminateCommand.MESSAGE_USAGE));

        //short command format
        commandBox.runCommand("t 1 2 100");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand("t 0 2 3");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        //user inputs non-numeric index values
        commandBox.runCommand("t a 2 3");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TerminateCommand.MESSAGE_USAGE));

        commandBox.runCommand("t ; 2 3");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TerminateCommand.MESSAGE_USAGE));
    }

    //---------------- Tests for corner cases --------------------------------------------------------

    /*
     * EP: terminating a terminated task,
     * should show error message: cannot terminate terminated tasks
     * - test short and long command format
     * - test multiple and single terminate
     */

    //long command format

    //single terminate
    @Test
    public void terminate_taskTerminated_longCommandFormat_failure() {
        commandBox.runCommand("terminate 2");
        commandBox.runCommand("terminate 2");
        assertResultMessage(TerminateCommand.ERROR_TERMINATED_TASK);
    }

    @Test
    public void multiple_terminate_taskTerminated_longCommandFormat_failure() {
        commandBox.runCommand("terminate 2 7");
        commandBox.runCommand("terminate 6 7");
        assertResultMessage(TerminateCommand.ERROR_TERMINATED_TASK);
    }

    //long command format

    //single terminate
    @Test
    public void terminate_taskTerminated_shortCommandFormat_failure() {
        commandBox.runCommand("terminate 2");
        commandBox.runCommand("terminate 2");
        assertResultMessage(TerminateCommand.ERROR_TERMINATED_TASK);
    }

    @Test
    public void multiple_terminate_taskTerminated_shortCommandFormat_failure() {
        commandBox.runCommand("terminate 2 7");
        commandBox.runCommand("terminate 6 7");
        assertResultMessage(TerminateCommand.ERROR_TERMINATED_TASK);
    }

    /*
     * EP: terminating multiple recurring tasks with spaces between indexes,
     * should add category "Done" to all the tasks's current category lists.
     */
    @Test
    public void multiple_SpacesInBetween_terminateTask_success() throws Exception {
        commandBox.runCommand("t 2       6 ");
        expectedTasksList[1] = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Feb 22, 2017 5pm")
                .withEndDateTime("Feb 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave")
                .withCategories("Done", AddCommand.BUILT_IN_ALL_TASKS).build();
        expectedTasksList[5] = new TaskBuilder().withName("Game project player testing").withPriorityLevel("Yes")
                .withStartDateTime("Jan 1, 2017 5pm")
                .withEndDateTime("Nov 28, 2017 5pm")
                .withRecurrence(Frequency.DAILY)
                .withInformation("4th street")
                .withCategories("Done", AddCommand.BUILT_IN_ALL_TASKS).build();

        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        TestTask[] terminatedTasks = new TestTask[] {expectedTasksList[5], expectedTasksList[1]};
        assertResultMessage(String.format(TerminateCommand.MESSAGE_MARK_RECURRING_TASK_DONE_SUCCESS,
                getDesiredFormat(terminatedTasks)));
    }

    //---------------- Tests terminate after find command -----------------------------------------

    /*
     * EP: terminating after finding a task,
     * should add category "Done" to the task's current category list.
     */
    @Test
    public void terminate_findThenTerminate_success() throws Exception {
        commandBox.runCommand("find ensure");

        int filteredTaskListIndex = 1;
        int taskBossIndex = 2;

        TestTask taskToMarkDone = expectedTasksList[taskBossIndex - 1];
        TestTask markedDoneTask = new TaskBuilder(taskToMarkDone).withCategories(AddCommand.BUILT_IN_DONE,
                AddCommand.BUILT_IN_ALL_TASKS).build();
        TestTask[] expectedList = { markedDoneTask };

        assertTerminateSuccess(false, filteredTaskListIndex, taskBossIndex, markedDoneTask, expectedList);
    }

    //---------------- Test for different types of tasks --------------------------------------


    /*
     * EP: terminating non-recurring task,
     * should show message: cannot terminate a non-recurring task.
     */
    @Test
    public void terminate_nonRecurring_failure() throws Exception {
        commandBox.runCommand("t 1");
        assertResultMessage(TerminateCommand.ERROR_TASK_NOT_RECURRING);
    }

    /*
     * EP: terminating recurring task,
     * should add category "Done" to the task's current category list.
     */
    @Test
    public void terminate_recurring_success() throws Exception {
        int taskBossIndex = 2;

        TestTask markedDoneTask =  new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Feb 22, 2017 5pm")
                .withEndDateTime("Feb 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave")
                .withCategories(AddCommand.BUILT_IN_DONE, AddCommand.BUILT_IN_ALL_TASKS).build();

        assertTerminateSuccess(false, taskBossIndex, taskBossIndex, markedDoneTask, expectedTasksList);
    }

    /*
     * EP: terminating recurring and non-recurring tasks at the same time,
     * should show message: cannot terminate a non-recurring task.
     */
    @Test
    public void terminate_mixTypes_failure() throws Exception {
        commandBox.runCommand("t 2 4");
        assertResultMessage(TerminateCommand.ERROR_TASK_NOT_RECURRING);
    }

    //---------------- End of test cases --------------------------------------

    private void assertTerminateSuccess(boolean isShort, int filteredTaskListIndex, int taskBossIndex,
            TestTask terminatedTask, TestTask[] resultList) {

        //@@author A0144904H
        if (isShort) {
            commandBox.runCommand("t " + filteredTaskListIndex);
        } else {
            commandBox.runCommand("terminate " + filteredTaskListIndex);
        }

        expectedTasksList[taskBossIndex - 1] = terminatedTask;
        assertTrue(taskListPanel.isListMatching(resultList));
        assertResultMessage(String.format(TerminateCommand.MESSAGE_MARK_RECURRING_TASK_DONE_SUCCESS ,
                            "1. " + terminatedTask));

        assertTrue(taskListPanel.isListMatching(resultList));
    }

    //@@author
    /**
     * Returns a formatted {@code Array} tasksToMarkDone,
     * so that each TestTask in the Array is numbered
     */
    private String getDesiredFormat(TestTask[] terminatedTask) {
        int indexOne = 1;
        String numberingDot = ". ";
        int i = indexOne;
        StringBuilder builder = new StringBuilder();
        for (TestTask task : terminatedTask) {
            builder.append(i + numberingDot).append(task.toString());
            i++;
        }
        return builder.toString();
    }
}
