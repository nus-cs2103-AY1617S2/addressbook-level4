package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.jobs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.jobs.commons.core.Messages;
import seedu.jobs.logic.commands.CompleteCommand;
import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;
import seedu.jobs.testutil.TestTask;

//@@author A0164440M
public class CompleteCommandTest extends TaskBookGuiTest {

    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void complete_missingTaskIndex_failure() {
        commandBox.runCommand("complete CS3101");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void complete_invalidTaskIndex_failure() {
        commandBox.runCommand("complete 100");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void complete_invalidValues_failure() {
        commandBox.runCommand("complete");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
    }

    //TODO
//    @Test
//    public void complete_task_Success() throws IllegalArgumentException, IllegalTimeException {
//        commandBox.runCommand("complete " + (expectedTasksList.length));
//        assertCompleteSuccess(expectedTasksList.length, expectedTasksList[expectedTasksList.length - 1]);
//    }

    /**
     * Checks whether the completed task has the correct updated details.
     * @throws IllegalTimeException
     */
    private void assertCompleteSuccess(int index, TestTask completedTask)
                                            throws IllegalArgumentException, IllegalTimeException {
        commandBox.runCommand("complete " + index);

        // confirm the new card contains the right data
        TaskCardHandle completedCard = taskListPanel.navigateToTask(completedTask.getName().fullName);
        assertMatching(completedTask, completedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[index] = completedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS, completedTask));
    }
}
