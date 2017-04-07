package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.logic.commands.DeleteCommand;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.testutil.TestTask;

public class ClearCommandTest extends ToDoListGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        TestTask[] tasks = td.getTypicalTasks();
        TaskType td1TaskType = td.task1.getTaskType();

        assertTrue(taskListPanel.isListMatching(td1TaskType, tasks));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.task1.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.task1.getTaskType(), td.task1));
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();

    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertListSize(0);
        assertResultMessage(ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS);
    }
}
