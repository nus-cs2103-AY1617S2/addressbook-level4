package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.onetwodo.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.logic.commands.DeleteCommand;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.testutil.TestTask;
import seedu.onetwodo.testutil.TestUtil;

public class DeleteCommandTest extends ToDoListGuiTest {

    @Test
    public void delete() {
/*

        TODO: write delete tests here. Use td.getTypicalTasks for testing.
        TODO: command back import if needed.
        Suggestion: 1) Delete first item from any TaskType
                    2) Delete last item same TaskType
                    3) Delete the item that was just deleted
*/

        TestTask[] currentList = td.getTypicalTasks();

        // invalid index
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e99999");
        assertResultMessage("The task index provided is invalid");

        // invalid command
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e-1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // delete first event
        assertDeleteSuccess(TaskType.EVENT, 3, currentList);
        currentList = TestUtil.removeTaskFromList(currentList, 3);

        assertDeleteSuccess(TaskType.EVENT, 2, currentList);
        currentList = TestUtil.removeTaskFromList(currentList, 2);

        assertDeleteSuccess(TaskType.EVENT, 1, currentList);
        currentList = TestUtil.removeTaskFromList(currentList, 1);

        // empty list
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e1");
        assertResultMessage("The task index provided is invalid");
    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first task in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(TaskType taskType, int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask[] filteredTasks = TestUtil.getTasksByTaskType(currentList, taskType);
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(filteredTasks, targetIndexOneIndexed);

        commandBox.runCommand("delete " + taskType.getPrefix() + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(taskType, expectedRemainder));

        //confirm the result message is correct
        ReadOnlyTask taskToDelete = filteredTasks[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
