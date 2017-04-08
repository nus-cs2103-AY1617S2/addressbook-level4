package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.logic.commands.AddCommand;
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
     * EP: valid task index,
     * should remove Done category from all tasks' current category lists.
     * - test for long and short command formats
     * - test multiple and single unmark
     */

    //long command format

    //single unmark
    @Test
    public void unmark_validIndex_longCommandFormat_success() throws Exception {
        int taskBossIndex = 2;
        String commandType = "termination";

        TestTask unmarkedTask = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Mar 22, 2017 5pm")
                .withEndDateTime("Mar 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave")
                .withCategories(AddCommand.BUILT_IN_ALL_TASKS).build();

        assertUnmarkSuccess(commandType, false, taskBossIndex, taskBossIndex,
                unmarkedTask, expectedTasksList);
    }

    //multiple unmark
    @Test
    public void multiple_unmark_validIndexes_longCommandFormat_success() throws Exception {
        commandBox.runCommand("m 4 5");
        commandBox.runCommand("unmark 4           5");
        expectedTasksList[3] = new TaskBuilder().withName("Debug code").withPriorityLevel("Yes")
                .withStartDateTime("Feb 20, 2017 11.30pm")
                .withEndDateTime("Apr 28, 2017 3pm")
                .withRecurrence(Frequency.NONE)
                .withInformation("10th street")
                .withCategories(AddCommand.BUILT_IN_ALL_TASKS).build();
        expectedTasksList[4] = new TaskBuilder().withName("Birthday party")
                .withInformation("311, Clementi Ave 2, #02-25")
                .withPriorityLevel("No")
                .withRecurrence(Frequency.NONE)
                .withStartDateTime("Feb 23, 2017 10pm")
                .withEndDateTime("Jun 28, 2017 5pm")
                .withCategories(AddCommand.BUILT_IN_ALL_TASKS, "Friends", "Owesmoney").build();

        TestTask[] unmarked = new TestTask[] {expectedTasksList[4], expectedTasksList[3]};
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_DONE_SUCCESS,
                getDesiredFormat(unmarked)));
    }

    //short command format

    //single unmark
    @Test
    public void unmark_validIndex_shortCommandFormat_success() throws Exception {
        int taskBossIndex = 2;
        String commandType = "termination";

        TestTask unmarkedTask = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Mar 22, 2017 5pm")
                .withEndDateTime("Mar 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave")
                .withCategories(AddCommand.BUILT_IN_ALL_TASKS).build();

        assertUnmarkSuccess(commandType, true, taskBossIndex, taskBossIndex,
                unmarkedTask, expectedTasksList);
    }

    //multiple mark done
    @Test
    public void multiple_unmark_validIndexes_shortCommandFormat_success() throws Exception {
        commandBox.runCommand("m 4 5");
        commandBox.runCommand("um 4           5");
        expectedTasksList[3] = new TaskBuilder().withName("Debug code").withPriorityLevel("Yes")
                .withStartDateTime("Feb 20, 2017 11.30pm")
                .withEndDateTime("Apr 28, 2017 3pm")
                .withRecurrence(Frequency.NONE)
                .withInformation("10th street")
                .withCategories(AddCommand.BUILT_IN_ALL_TASKS).build();
        expectedTasksList[4] = new TaskBuilder().withName("Birthday party")
                .withInformation("311, Clementi Ave 2, #02-25")
                .withPriorityLevel("No")
                .withRecurrence(Frequency.NONE)
                .withStartDateTime("Feb 23, 2017 10pm")
                .withEndDateTime("Jun 28, 2017 5pm")
                .withCategories(AddCommand.BUILT_IN_ALL_TASKS, "Friends", "Owesmoney").build();

        TestTask[] unmarked = new TestTask[] {expectedTasksList[4], expectedTasksList[3]};
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_DONE_SUCCESS,
                getDesiredFormat(unmarked)));
    }


    /*
     * EP: missing task index,
     * should show error message: invalid command format
     * and display a message demonstrating the correct way to write the command
     */
    @Test
    public void unmark_missingTaskIndex_failure() {
        //long command format
        commandBox.runCommand("unmark ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));

        //short command format
        commandBox.runCommand("um ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
    }

    /*
     * EP: invalid task index,
     * should show error message: invalid index
     * - test short and long command format
     * - test multiple and single unmark
     */

    //single unmark
    @Test
    public void unmark_invalidTaskIndex_failure() {

        //long command format

        commandBox.runCommand("unmark 9");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand("unmark -1");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // non-numeric inputs
        commandBox.runCommand("unmark ^");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));

        commandBox.runCommand("unmark b");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));

        //short command format

        commandBox.runCommand("um 10");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand("um 0");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // non-numeric inputs
        commandBox.runCommand("um ^");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));

        commandBox.runCommand("um b");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
    }

    //multiple unmark
    @Test
    public void multiple_unmark_InvalidIndexes_failure() {
        //long command format
        commandBox.runCommand("unmark 1 2 100");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand("unmark 0 2 3");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        //user inputs non-numeric index values
        commandBox.runCommand("unmark a 2 3");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));

        commandBox.runCommand("unmark ; 2 3");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));

        //short command format
        commandBox.runCommand("um 1 2 100");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand("um 0 2 3");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        //user inputs non-numeric index values
        commandBox.runCommand("um a 2 3");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));

        commandBox.runCommand("um ; 2 3");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
    }

    //---------------- Tests for corner cases --------------------------------------------------------

    /*
     * EP: unmarking multiple tasks with spaces between indexes,
     * should remove Done category from all tasks' current category lists.
     */
    @Test
    public void multiple_SpacesInBetween_unmarkTask_success() throws Exception {
        commandBox.runCommand("m 4 5");
        commandBox.runCommand("um 4           5");
        expectedTasksList[3] = new TaskBuilder().withName("Debug code").withPriorityLevel("Yes")
                .withStartDateTime("Feb 20, 2017 11.30pm")
                .withEndDateTime("Apr 28, 2017 3pm")
                .withRecurrence(Frequency.NONE)
                .withInformation("10th street")
                .withCategories(AddCommand.BUILT_IN_ALL_TASKS).build();
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

    //---------------- Tests unmark done after find command -----------------------------------------

    /*
     * EP: unmarking after finding a task,
     * should remove Done category from all tasks' current category lists.
     */
    @Test
    public void unmark_findThenUnmark_success() throws Exception {
        commandBox.runCommand("find clean");
        String commandType = "marking";

        int filteredTaskListIndex = 1;
        int taskBossIndex = 1;

        TestTask taskToUnmark = expectedTasksList[taskBossIndex - 1];
        TestTask unmarkedTask = new TaskBuilder(taskToUnmark).build();
        TestTask[] resultList = { unmarkedTask };

        assertUnmarkSuccess(commandType, false, filteredTaskListIndex,
                taskBossIndex, unmarkedTask, resultList);
    }

    //---------------- Test for different types of tasks --------------------------------------


    /*
     * EP: unmarking non-recurring task,
     * should remove Done category from all tasks' current category lists.
     */
    @Test
    public void unmark_nonRecurring_success() throws Exception {
        int taskBossIndex = 1;
        String commandType = "marking";

        TestTask unmarkedTask = new TaskBuilder().withName("Clean house").withPriorityLevel("Yes")
                .withStartDateTime("Feb 19, 2017 11pm")
                .withEndDateTime("Feb 28, 2017 5pm")
                .withInformation("wall street").withRecurrence(Frequency.NONE)
                .withCategories(AddCommand.BUILT_IN_ALL_TASKS).build();

        assertUnmarkSuccess(commandType, false, taskBossIndex,
                taskBossIndex, unmarkedTask, expectedTasksList);
    }

    /*
     * EP: unmarking recurring task,
     * should remove Done category from all tasks' current category lists
     * and should update date of task according to recurrance type
     */
    @Test
    public void unmark_recurring_success() throws Exception {
        int taskBossIndex = 2;
        String commandType = "termination";

        TestTask unmarkedTask =  new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Mar 22, 2017 5pm")
                .withEndDateTime("Mar 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave")
                .withCategories(AddCommand.BUILT_IN_ALL_TASKS).build();

        assertUnmarkSuccess(commandType, false, taskBossIndex,
                taskBossIndex, unmarkedTask, expectedTasksList);
    }

    /*
     * EP: unmarking recurring and non-recurring tasks at the same time,
     * should update the recurring task's dates based on recurrence type.
     * and should remove Done category from all tasks' current category lists
     */
    @Test
    public void unmarkDone_mixTypes_success() throws Exception {
        commandBox.runCommand("terminate 2");
        commandBox.runCommand("mark 4");
        commandBox.runCommand("unmark 2 4");

        // recurring
        expectedTasksList[1] = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Mar 22, 2017 5pm")
                .withEndDateTime("Mar 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave")
                .withCategories(AddCommand.BUILT_IN_ALL_TASKS).build();

        //non recurring
        expectedTasksList[3] = new TaskBuilder().withName("Debug code").withPriorityLevel("Yes")
                .withStartDateTime("Feb 20, 2017 11.30pm")
                .withEndDateTime("Apr 28, 2017 3pm")
                .withRecurrence(Frequency.NONE)
                .withInformation("10th street")
                .withCategories(AddCommand.BUILT_IN_ALL_TASKS).build();

        TestTask[] markedDone = new TestTask[] {expectedTasksList[3], expectedTasksList[1]};
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_DONE_SUCCESS,
                getDesiredFormat(markedDone)));
    }

    //---------------- Tests for inputing wrong task type--------------------------------------------------------

    /*
     * EP: task is not in Done category
     * should show error message: cannot unmark a task that is not in the done category
     * - test for marked and terminated tasks
     */

    //not marked task
    @Test
    public void unmarkTask_NotMarkedDone_failure() {
        commandBox.runCommand("um 1");
        assertResultMessage(UnmarkCommand.ERROR_NOT_MARKED);
    }

    //not terminated task
    @Test
    public void unmarkTask_NotTerminated_failure() {
        commandBox.runCommand("um 2");
        assertResultMessage(UnmarkCommand.ERROR_NOT_MARKED);
    }

    //---------------- End of test cases --------------------------------------

    private void assertUnmarkSuccess(String commandType, boolean isShort,
            int filteredTaskListIndex, int taskBossIndex,
            TestTask terminatedTask, TestTask[] resultList) {

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
        assertTrue(taskListPanel.isListMatching(resultList));
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_DONE_SUCCESS ,
                            "1. " + terminatedTask));

        assertTrue(taskListPanel.isListMatching(resultList));
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
