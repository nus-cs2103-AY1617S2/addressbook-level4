//@@author A0135739W
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.logic.commands.DeleteCommand;
import seedu.onetwodo.logic.commands.DoneCommand;
import seedu.onetwodo.logic.commands.ListCommand;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.testutil.TestTask;

public class ClearCommandTest extends ToDoListGuiTest {

    TestTask[] currentList = td.getTypicalTasks();

    @Test
    public void clear_invalid_input_failure () {
        commandBox.runCommand(ClearCommand.COMMAND_WORD + " ddoonee");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
    }

    @Test
    public void clear_all_success() {
        //verify a non-empty list can be cleared
        TaskType taskATaskType = td.taskA.getTaskType();

        assertTrue(taskListPanel.isListMatching(taskATaskType, currentList));
        assertClearAllCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.task1.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.task1.getTaskType(), td.task1));
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e1");
        assertListSize(0);
    }

    @Test
    public void clear_success() {
        assertClearCommandSuccess();
    }

    @Test
    public void clear_empty_list_failure() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertResultMessage(ClearCommand.MESSAGE_NO_MORE_TASK);
    }

    @Test
    public void clear_done_success () {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " t1");
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " t1");
        commandBox.runCommand(ClearCommand.COMMAND_CLEAR_DONE);
        assertResultMessage(ClearCommand.MESSAGE_CLEAR_DONE_SUCCESS);
        commandBox.runCommand(ListCommand.COMMAND_LIST_DONE);
        assertListSize(0);
    }

    @Test
    public void double_clear_done_failure() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " t1");
        commandBox.runCommand(ClearCommand.COMMAND_CLEAR_DONE);
        commandBox.runCommand(ClearCommand.COMMAND_CLEAR_DONE);
        assertResultMessage(ClearCommand.MESSAGE_NO_MORE_DONE_TASK);
    }

    @Test
    public void clear_undone_success () {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " t1");
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " t1");
        commandBox.runCommand(ClearCommand.COMMAND_CLEAR_UNDONE);
        assertResultMessage(ClearCommand.MESSAGE_CLEAR_UNDONE_SUCCESS);
        assertListSize(0);
    }

    @Test
    public void double_clear_undone_failure() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " t1");
        commandBox.runCommand(ClearCommand.COMMAND_CLEAR_UNDONE);
        commandBox.runCommand(ClearCommand.COMMAND_CLEAR_UNDONE);
        assertResultMessage(ClearCommand.MESSAGE_NO_MORE_UNDONE_TASK);
    }

    /**
     * Runs the clear_all command and confirms the result is correct.
     */
    private void assertClearAllCommandSuccess() {
        commandBox.runCommand(ClearCommand.COMMAND_CLEAR_ALL);
        assertListSize(0);
        assertResultMessage(ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS);
    }

    /**
     * Runs the clear command and confirms the result is correct.
     */
    private void assertClearCommandSuccess() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertListSize(0);
        assertResultMessage(ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS);
    }
}
