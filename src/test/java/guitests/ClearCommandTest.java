//@@author A0135739W
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.logic.commands.DeleteCommand;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.testutil.TestTask;

public class ClearCommandTest extends ToDoListGuiTest {

    TestTask[] currentList = td.getTypicalTasks();

    @Test
    public void clear_invalid_input_failure () {
        commandBox.runCommand(ClearCommand.COMMAND_WORD + " e9999");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
    }

    @Test
    public void clear_all_sucess() {
        //verify a non-empty list can be cleared
        TaskType taskATaskType = td.taskA.getTaskType();

        assertTrue(taskListPanel.isListMatching(taskATaskType, currentList));
        assertClearAllCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.task1.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.task1.getTaskType(), td.task1));
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearAllCommandSuccess();
    }

    public void clear_done_success () {

    }

    private void assertClearAllCommandSuccess() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertListSize(0);
        assertResultMessage(ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS);
    }
}
