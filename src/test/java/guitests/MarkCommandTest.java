package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.MarkCommand.MESSAGE_TYPE_BOOKING;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import guitests.guihandles.TaskCardHandle;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

//@@author A0105287E
public class MarkCommandTest extends TaskManagerGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void mark_statusSpecified_success() throws Exception {

        String detailsToEdit = "completed";
        int taskManagerIndex = 2;

        TestTask editedTask = new TaskBuilder().withTitle("Complete task 2").withStartTime("10-10-2017 0100")
                .withDeadline("11-11-2017 2300").withLabels("owesMoney", "friends")
                .withStatus(true).build();

        assertMarkSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void mark_missingTaskIndex_failure() {
        commandBox.runCommand("mark completed");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void mark_missingStatus_failure() {
        commandBox.runCommand("mark 1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void mark_completeBooking_failure() {
        //add a booking
        commandBox.runCommand("book Complete booking #friends on 10-10-2017 2pm to 5pm,"
                + " 11-10-2017 2pm to 5pm, 12-10-2017 2pm to 5pm");
        commandBox.runCommand("mark 8 completed");
        assertResultMessage(MESSAGE_TYPE_BOOKING);
    }


    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param (int filteredTaskListIndex index of task to edit in filtered list
     * @param taskManagerIndex index of task to edit in the task manager.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param status the status to edit the task with as input to the mark command
     * @param editedTask the expected task after editing the task's details
     */
    private void assertMarkSuccess(int filteredTaskListIndex, int taskManagerIndex,
                                    String status, TestTask editedTask) {
        commandBox.runCommand("mark " + filteredTaskListIndex + " " + status);
        System.out.println("details to edit: " + status);
        System.out.println("edited task: " + editedTask);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getTitle().title);
        System.out.println("Edited card: " + editedCard);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskManagerIndex - 1] = editedTask;
        Arrays.sort(expectedTasksList);
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, editedTask));
    }

}
