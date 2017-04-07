package guitests;

import org.junit.Test;

import seedu.task.logic.commands.RevertCommand;
import seedu.task.logic.commands.UndoCommand;
import seedu.task.testutil.TestTask;

public class RevertCommandTest extends TaskManagerGuiTest {

    @Test
    public void revert_emptySession_failure() {
        commandBox.runCommand("revert");
        assertResultMessage(UndoCommand.MESSAGE_NO_BACKWARDS_COMMAND);
    }

    @Test
    public void revert_noPreviousCommand_failure() {
        //End of state stack
        commandBox.runCommand("delete 1");
        commandBox.runCommand("revert");
        commandBox.runCommand("revert");
        assertResultMessage(UndoCommand.MESSAGE_NO_BACKWARDS_COMMAND);
    }

    @Test
    public void revert_revertMultipleCommands_success() throws Exception {
        TestTask taskToAdd = td.hoon;
        commandBox.runCommand(taskToAdd.getAddCommand());
        taskToAdd = td.ida;
        commandBox.runCommand(taskToAdd.getAddCommand());

        TestTask[] originalList = td.getTypicalTasks();
        assertRevertSuccess(originalList);
    }

    private void assertRevertSuccess(TestTask[] expectedList) {
        commandBox.runCommand("revert");
        assertResultMessage(RevertCommand.MESSAGE_SUCCESS);
    }
}
