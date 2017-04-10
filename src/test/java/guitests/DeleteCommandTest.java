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

//@@author A0143029M
public class DeleteCommandTest extends ToDoListGuiTest {

    @Test
    public void delete() {
        TestTask[] currentList = td.getTypicalTasks();

        // invalid index
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e99999");
        assertResultMessage("The task index provided is invalid");

        // invalid command
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e-1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // delete first event
        assertDeleteSuccess(DeleteCommand.COMMAND_WORD, TaskType.EVENT, 3, currentList);
        currentList = TestUtil.removeTaskFromList(currentList, 3);

        assertDeleteSuccess(DeleteCommand.SHORT_COMMAND_WORD, TaskType.EVENT, 2, currentList);
        currentList = TestUtil.removeTaskFromList(currentList, 2);

        assertDeleteSuccess(DeleteCommand.COMMAND_WORD, TaskType.EVENT, 1, currentList);
        currentList = TestUtil.removeTaskFromList(currentList, 1);

        // empty list
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e1");
        assertResultMessage("The task index provided is invalid");
    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param commandString The command to be executed, can be either "delete" or its short form "d"
     * @param targetIndexOneIndexed e.g. index 1 to delete the first task in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(String commandString, TaskType taskType, int targetIndexOneIndexed,
            final TestTask[] currentList) {
        TestTask[] filteredTasks = TestUtil.getTasksByTaskType(currentList, taskType);
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(filteredTasks, targetIndexOneIndexed);

        commandBox.runCommand(commandString + " " + taskType.getPrefix() + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(taskType, expectedRemainder));

        //confirm the result message is correct
        ReadOnlyTask taskToDelete = filteredTasks[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
