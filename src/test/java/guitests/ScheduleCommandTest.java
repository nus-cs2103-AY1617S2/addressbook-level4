package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.opus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.opus.commons.core.Messages;
import seedu.opus.logic.commands.EditCommand;
import seedu.opus.logic.commands.ScheduleCommand;
import seedu.opus.model.task.DateTime;
import seedu.opus.testutil.TaskBuilder;
import seedu.opus.testutil.TestTask;

//@@author A0126345J
public class ScheduleCommandTest extends TaskManagerGuiTest {

    @Test
    public void scheduleEventSuccess() throws Exception {
        String detailsToSchedule = "sunday 10pm to sunday 11pm";
        int taskManagerIndex = 5;

        TestTask scheduledTask = new TaskBuilder().withName("Wash the dishes")
                .withNote("They're in the sink").withStatus("incomplete").withPriority("mid")
                .withStartTime("sunday 10pm").withEndTime("sunday 11pm")
                .withTags("chores").build();

        assertScheduleSuccess(taskManagerIndex, taskManagerIndex, detailsToSchedule, scheduledTask);
    }

    @Test
    public void scheduleEndTimeSuccess() throws Exception {
        String detailsToSchedule = "12/12/2017 14:00";
        int taskManagerIndex = 5;

        TestTask scheduledTask = new TaskBuilder().withName("Wash the dishes")
                .withNote("They're in the sink").withStatus("incomplete").withPriority("mid")
                .withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 14:00")
                .withTags("chores").build();

        assertScheduleSuccess(taskManagerIndex, taskManagerIndex, detailsToSchedule, scheduledTask);
    }

    @Test
    public void scheduleInvalidDateTimeFailure() throws Exception {
        commandBox.runCommand("schedule 2 afdasf");
        assertResultMessage(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
    }

    @Test
    public void scheduleMissingTaskIndexFailure() {
        commandBox.runCommand("schedule next friday");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void scheduleInvalidTaskIndexFailure() {
        commandBox.runCommand("schedule 10 next friday");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void scheduleNoFieldsSpecifiedFailure() {
        commandBox.runCommand("schedule 1");
        assertResultMessage(ScheduleCommand.MESSAGE_NOT_SCHEDULED);

        // trailing whitespace after command should be considered as a invalid command
        commandBox.runCommand("schedule 1    ");
        assertResultMessage(ScheduleCommand.MESSAGE_NOT_SCHEDULED);
    }

    /**
     * Checks whether the scheduled task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to schedule in filtered list
     * @param taskManagerIndex index of task to schedule in the task manager.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToSchedule details to schedule the task with as input to the schedule command
     * @param scheduledTask the expected task after scheduling the task's start time and end time
     */
    private void assertScheduleSuccess(int filteredTaskListIndex, int taskManagerIndex,
        String detailsToSchedule, TestTask scheduledTask) {

        TestTask[] expectedTasksList = td.getTypicalTasks();
        commandBox.runCommand("schedule " + filteredTaskListIndex + " " + detailsToSchedule);

        // confirm the new card contains the right data
        TaskCardHandle scheduledCard = taskListPanel.navigateToTask(scheduledTask.getName().fullName);
        assertMatching(scheduledTask, scheduledCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskManagerIndex - 1] = scheduledTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, scheduledTask));
    }

}
