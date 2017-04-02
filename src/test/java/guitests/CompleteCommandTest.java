//@@author A0139217E
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.CompleteCommand;
import seedu.task.testutil.TaskBuilder;
import seedu.task.testutil.TestTask;

public class CompleteCommandTest extends TaskManagerGuiTest {

    TestTask[] originalTaskList = td.getTypicalTasks();

    @Test
    public void complete_invalidIndexNumber_failure() {
        int indexNumber = originalTaskList.length + 1;
        assertCompleteFailure(indexNumber);
    }

    @Test
    public void complete_MissingIndexNumber_failure() {
        commandBox.runCommand("complete");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void complete_validIndexNumber_success() {
        commandBox.runCommand(td.hoon.getAddCommand());
        TestTask editedTask = null;
        try {
            editedTask = new TaskBuilder(td.hoon).withPriority("-" + td.hoon.getPriority().value).build();
        } catch (IllegalValueException e) {
        }
        int indexNumber = originalTaskList.length + 1;
        assertCompleteSuccess(indexNumber, editedTask);
    }

    /**
     * Runs the complete command to mark as complete a task specified at an invalid index and confirms
     * the index is incorrect.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertCompleteFailure(int targetIndexOneIndexed) {
        commandBox.runCommand("complete " + targetIndexOneIndexed);

        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Runs the complete command to mark as complete a specified task  at an invalid index and confirms
     * the index is incorrect.
     * @param targetIndexOneIndexed e.g. index 1 to mark as complete the first task in the list,
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertCompleteSuccess(int targetIndexOneIndexed, TestTask editedTask) {
        commandBox.runCommand("complete " + targetIndexOneIndexed);

        assertResultMessage(String.format(CompleteCommand.MESSAGE_MARK_COMPLETE_TASK_SUCCESS, editedTask));
        assertTrue(taskListPanel.isListMatching(originalTaskList));
    }

}

