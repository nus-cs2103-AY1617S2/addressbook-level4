package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.logic.commands.AddCommand;
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
     * EP: valid task index,
     * should add category "Done" to the task's current category list.
     * - test for long and short command formats
     * - test multiple and single mark done
     */

    //long command format
    //single mark done
    @Test
    public void markDone_validIndex_longCommandFormat_success() throws Exception {
        int taskBossIndex = 1;

        TestTask markedDoneTask = new TaskBuilder().withName("Clean house").withPriorityLevel("Yes")
                .withStartDateTime("Feb 19, 2017 11pm")
                .withEndDateTime("Feb 28, 2017 5pm")
                .withInformation("wall street").withRecurrence(Frequency.NONE)
                .withCategories(AddCommand.BUILT_IN_DONE, AddCommand.BUILT_IN_ALL_TASKS).build();

        assertMarkDoneSuccess(false, taskBossIndex, taskBossIndex, markedDoneTask, expectedTasksList);
    }

    //multiple mark done
    @Test
    public void multiple_markDone_validIndexes_longCommandFormat_success() throws Exception {
        commandBox.runCommand("mark 4 5");

        expectedTasksList[4] = new TaskBuilder().withName("Birthday party")
                .withInformation("311, Clementi Ave 2, #02-25")
                .withPriorityLevel("No")
                .withRecurrence(Frequency.NONE)
                .withStartDateTime("Feb 23, 2017 10pm")
                .withEndDateTime("Jun 28, 2017 5pm")
                .withCategories(AddCommand.BUILT_IN_DONE, AddCommand.BUILT_IN_ALL_TASKS,
                        "Friends", "Owesmoney").build();

        expectedTasksList[3] = new TaskBuilder().withName("Debug code").withPriorityLevel("Yes")
                .withStartDateTime("Feb 20, 2017 11.30pm")
                .withEndDateTime("Apr 28, 2017 3pm")
                .withRecurrence(Frequency.NONE)
                .withInformation("10th street")
                .withCategories(AddCommand.BUILT_IN_DONE, AddCommand.BUILT_IN_ALL_TASKS).build();

        TestTask[] markedDone = new TestTask[] {expectedTasksList[4], expectedTasksList[3]};

        assertTrue(taskListPanel.isListMatching(expectedTasksList));

        assertResultMessage(String.format(MarkDoneCommand.MESSAGE_MARK_TASK_DONE_SUCCESS,
                getDesiredFormat(markedDone)));
    }

    //short command format
    //single mark done
    @Test
    public void markTaskDone_validIndex_shortCommandFormat_success() throws Exception {
        int taskBossIndex = 1;

        TestTask markedDoneTask = new TaskBuilder().withName("Clean house").withPriorityLevel("Yes")
                .withStartDateTime("Feb 19, 2017 11pm")
                .withEndDateTime("Feb 28, 2017 5pm")
                .withInformation("wall street").withRecurrence(Frequency.NONE)
                .withCategories(AddCommand.BUILT_IN_DONE, AddCommand.BUILT_IN_ALL_TASKS).build();

        assertMarkDoneSuccess(true, taskBossIndex, taskBossIndex, markedDoneTask, expectedTasksList);
    }

    //multiple mark done
    @Test
    public void multiple_markDone_validIndexes_shortCommandFormat_success() throws Exception {
        commandBox.runCommand("m 4 5");

        expectedTasksList[4] = new TaskBuilder().withName("Birthday party")
                .withInformation("311, Clementi Ave 2, #02-25")
                .withPriorityLevel("No")
                .withRecurrence(Frequency.NONE)
                .withStartDateTime("Feb 23, 2017 10pm")
                .withEndDateTime("Jun 28, 2017 5pm")
                .withCategories(AddCommand.BUILT_IN_DONE, AddCommand.BUILT_IN_ALL_TASKS,
                        "Friends", "Owesmoney").build();

        expectedTasksList[3] = new TaskBuilder().withName("Debug code").withPriorityLevel("Yes")
                .withStartDateTime("Feb 20, 2017 11.30pm")
                .withEndDateTime("Apr 28, 2017 3pm")
                .withRecurrence(Frequency.NONE)
                .withInformation("10th street")
                .withCategories(AddCommand.BUILT_IN_DONE, AddCommand.BUILT_IN_ALL_TASKS).build();

        TestTask[] markedDone = new TestTask[] {expectedTasksList[4], expectedTasksList[3]};

        assertTrue(taskListPanel.isListMatching(expectedTasksList));

        assertResultMessage(String.format(MarkDoneCommand.MESSAGE_MARK_TASK_DONE_SUCCESS,
                getDesiredFormat(markedDone)));
    }


    /*
     * EP: missing task index,
     * should show error message: invalid command format
     * and display a message demonstrating the correct way to write the command
     */
    @Test
    public void markDone_missingTaskIndex_failure() {
        //long command format
        commandBox.runCommand("mark ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));

        //short command format
        commandBox.runCommand("m ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));
    }

    /*
     * EP: invalid task index,
     * should show error message: invalid index
     * - test short and long command format
     * - test multiple and single mark done
     */

    //single mark done
    @Test
    public void markDone_invalidTaskIndex_failure() {

        //long command format

        commandBox.runCommand("mark 9");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand("mark -1");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // non-numeric inputs
        commandBox.runCommand("mark ^");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));

        commandBox.runCommand("mark b");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));

        //short command format

        commandBox.runCommand("m 10");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand("m 0");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // non-numeric inputs
        commandBox.runCommand("m ^");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));

        commandBox.runCommand("m b");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));
    }

    //multiple mark done
    @Test
    public void multiple_markDone_InvalidIndexes_failure() {
        //long command format
        commandBox.runCommand("mark 1 2 100");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand("mark 0 2 3");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        //user inputs non-numeric index values
        commandBox.runCommand("mark a 2 3");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));

        commandBox.runCommand("mark ; 2 3");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));

        //short command format
        commandBox.runCommand("m 1 2 100");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand("m 0 2 3");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        //user inputs non-numeric index values
        commandBox.runCommand("m a 2 3");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));

        commandBox.runCommand("m ; 2 3");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));
    }

    //---------------- Tests for corner cases --------------------------------------------------------

    /*
     * EP: marking a marked task,
     * should show error message: cannot mark marked tasks
     * - test short and long command format
     * - test multiple and single mark done
     */

    //long command format

    //single mark done
    @Test
    public void markDone_taskMarkedDone_longCommandFormat_failure() {
        commandBox.runCommand("mark 1");
        commandBox.runCommand("mark 1");
        assertResultMessage(MarkDoneCommand.ERROR_MARKED_TASK);
    }

    //multiple mark done
    @Test
    public void multiple_markDone_taskMarkedDone_longCommandFormat_failure() {
        commandBox.runCommand("mark 1 4");
        commandBox.runCommand("mark 2 4");
        assertResultMessage(MarkDoneCommand.ERROR_MARKED_TASK);
    }

    //short command format

    //single mark done
    @Test
    public void markDone_taskMarkedDone_shortCommandFormat_failure() {
        commandBox.runCommand("m 1");
        commandBox.runCommand("m 1");
        assertResultMessage(MarkDoneCommand.ERROR_MARKED_TASK);
    }

    //multiple mark done
    @Test
    public void multiple_markDone_taskMarkedDone_shortCommandFormat_failure() {
        commandBox.runCommand("m 1 5");
        commandBox.runCommand("m 2 5");
        assertResultMessage(MarkDoneCommand.ERROR_MARKED_TASK);
    }

    /*
     * EP: marking multiple tasks with spaces between indexes,
     * should add category "Done" to all the tasks's current category lists.
     */
    @Test
    public void multiple_SpacesInBetween_markTaskDone_success() throws Exception {
        commandBox.runCommand("mark 5       4 ");
        expectedTasksList[4] = new TaskBuilder().withName("Birthday party")
                .withInformation("311, Clementi Ave 2, #02-25")
                .withPriorityLevel("No")
                .withRecurrence(Frequency.NONE)
                .withStartDateTime("Feb 23, 2017 10pm")
                .withEndDateTime("Jun 28, 2017 5pm")
                .withCategories(AddCommand.BUILT_IN_DONE, AddCommand.BUILT_IN_ALL_TASKS,
                                    "Friends", "Owesmoney").build();

        expectedTasksList[3] = new TaskBuilder().withName("Debug code").withPriorityLevel("Yes")
                .withStartDateTime("Feb 20, 2017 11.30pm")
                .withEndDateTime("Apr 28, 2017 3pm")
                .withRecurrence(Frequency.NONE)
                .withInformation("10th street")
                .withCategories(AddCommand.BUILT_IN_DONE, AddCommand.BUILT_IN_ALL_TASKS).build();

        TestTask[] markedDone = new TestTask[] {expectedTasksList[4], expectedTasksList[3]};
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(MarkDoneCommand.MESSAGE_MARK_TASK_DONE_SUCCESS,
                getDesiredFormat(markedDone)));
    }

    //---------------- Tests mark done after find command -----------------------------------------

    /*
     * EP: marking after finding a task,
     * should add category "Done" to the task's current category list.
     */
    @Test
    public void markDone_findThenMarkDone_success() throws Exception {
        commandBox.runCommand("find Clean house");

        int filteredTaskListIndex = 1;
        int taskBossIndex = 1;

        TestTask taskToMarkDone = expectedTasksList[taskBossIndex - 1];
        TestTask markedDoneTask = new TaskBuilder(taskToMarkDone).withCategories(AddCommand.BUILT_IN_DONE,
                AddCommand.BUILT_IN_ALL_TASKS).build();
        TestTask[] expectedList = { markedDoneTask };

        assertMarkDoneSuccess(false, filteredTaskListIndex, taskBossIndex, markedDoneTask, expectedList);
    }

    //---------------- Test for different types of tasks --------------------------------------


    /*
     * EP: marking non-recurring task,
     * should add category "Done" to the task's current category list.
     */
    @Test
    public void markDone_nonRecurring_success() throws Exception {
        int taskBossIndex = 1;

        TestTask markedDoneTask = new TaskBuilder().withName("Clean house").withPriorityLevel("Yes")
                .withStartDateTime("Feb 19, 2017 11pm")
                .withEndDateTime("Feb 28, 2017 5pm")
                .withInformation("wall street").withRecurrence(Frequency.NONE)
                .withCategories(AddCommand.BUILT_IN_DONE, AddCommand.BUILT_IN_ALL_TASKS).build();

        assertMarkDoneSuccess(false, taskBossIndex, taskBossIndex, markedDoneTask, expectedTasksList);
    }

    /*
     * EP: marking recurring task,
     * should update task's dates based on recurrence type.
     */
    @Test
    public void markDone_recurring_success() throws Exception {
        int taskBossIndex = 2;

        TestTask markedDoneTask =  new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                .withStartDateTime("Mar 22, 2017 5pm")
                .withEndDateTime("Mar 28, 2017 5pm")
                .withRecurrence(Frequency.MONTHLY)
                .withInformation("michegan ave")
                .withCategories(AddCommand.BUILT_IN_ALL_TASKS).build();

        assertMarkDoneSuccess(false, taskBossIndex, taskBossIndex, markedDoneTask, expectedTasksList);
    }

    /*
     * EP: marking recurring and non-recurring tasks at the same time,
     * should update the recurring task's dates based on recurrence type.
     * should add category "Done" to the non-recurring task's current category list.
     */
    @Test
    public void markDone_mixTypes_success() throws Exception {

        commandBox.runCommand("mark 2 4");

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
                .withCategories(AddCommand.BUILT_IN_DONE, AddCommand.BUILT_IN_ALL_TASKS).build();

        TestTask[] markedDone = new TestTask[] {expectedTasksList[3], expectedTasksList[1]};
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(MarkDoneCommand.MESSAGE_MARK_TASK_DONE_SUCCESS,
                getDesiredFormat(markedDone)));
    }

    //---------------- End of test cases --------------------------------------

    private void assertMarkDoneSuccess(boolean isShort, int filteredTaskListIndex, int taskBossIndex,
            TestTask markedDoneTask, TestTask[] expectedTasks) {

        if (isShort) {
            commandBox.runCommand("m " + filteredTaskListIndex);
        } else {
            commandBox.runCommand("mark " + filteredTaskListIndex);
        }

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskBossIndex - 1] = markedDoneTask;

        assertTrue(taskListPanel.isListMatching(expectedTasks));
        assertResultMessage(String.format(MarkDoneCommand.MESSAGE_MARK_TASK_DONE_SUCCESS, "1. " +
                markedDoneTask));
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
