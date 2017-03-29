package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import org.junit.Test;

import seedu.onetwodo.logic.commands.DoneCommand;
import seedu.onetwodo.logic.commands.ListCommand;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.testutil.TestTask;
import seedu.onetwodo.testutil.TestUtil;

//@@author A0135739W
public class DoneCommandTest extends ToDoListGuiTest {

    TestTask[] currentList = td.getTypicalTasks();

    @Test
    public void done_emptyParameter_failure() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void done_noIndex_failure() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " d");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void done_noType_failure() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " 1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void done_wrongType_failure() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " a1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void done_invalidIndex_failure() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " t9999");
        assertResultMessage(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void done_success() {
        assertDoneSuccess(TaskType.EVENT, "e3", currentList);
    }

    @Test
    public void done_doneTask_failure() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " d1");
        commandBox.runCommand(ListCommand.COMMAND_WORD + " done");
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " d1");
        assertResultMessage("This task has been done");
    }

    /**
     * Runs the done command to complete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to complete the first task in the list,
     * @param currentList A copy of the current list of tasks (before marking done).
     */
    private void assertDoneSuccess(TaskType taskType, String filteredTaskListIndex, TestTask[] currentList) {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " " + filteredTaskListIndex);

        int testTaskIndex = TestUtil.getFilteredIndexInt(filteredTaskListIndex);
        currentList[testTaskIndex].setIsDone(true);

        //Assert taskListPanel correctly shows tasks left undone
        TestTask[] filteredUndoneList = TestUtil.getTasksByDoneStatus(currentList, false);
        assertTrue(taskListPanel.isListMatching(taskType, filteredUndoneList));

        //confirm the result message is correct
        assertResultMessage(String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, currentList[testTaskIndex]));

        //Assert taskListPanel correctly shows tasks that are undone
        commandBox.runCommand(ListCommand.COMMAND_WORD + " done");
        TestTask[] filteredDoneList = TestUtil.getTasksByDoneStatus(currentList, true);
        assertTrue(taskListPanel.isListMatching(taskType, filteredDoneList));
    }
}
