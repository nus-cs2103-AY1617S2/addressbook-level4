package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.logic.commands.RedoCommand;
import seedu.task.logic.commands.UnrevertCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class UnrevertCommandTest extends TaskManagerGuiTest {

    @Test
    public void unrevert_emptySession_failure() {
        commandBox.runCommand("unrevert");
        assertResultMessage(RedoCommand.MESSAGE_NO_FORWARDS_COMMAND);
    }

    @Test
    public void unrevert_noForwardCommand_failure() {
        //End of state stack
        commandBox.runCommand("delete 1");
        commandBox.runCommand("revert");
        commandBox.runCommand("unrevert");
        commandBox.runCommand("unrevert");
        assertResultMessage(RedoCommand.MESSAGE_NO_FORWARDS_COMMAND);
    }

    @Test
    public void unrevert_unrevertMultipleCommands_success() throws Exception {
        TestTask[] originalList = td.getTypicalTasks();

        TestTask taskToAdd = td.hoon;
        commandBox.runCommand(taskToAdd.getAddCommand());
        TestTask[] expectedList = TestUtil.addTasksToList(originalList, taskToAdd);

        taskToAdd = td.ida;
        commandBox.runCommand(taskToAdd.getAddCommand());
        expectedList = TestUtil.addTasksToList(expectedList, taskToAdd);

        assertUnrevertSuccess(expectedList);
    }

    private void assertUnrevertSuccess(TestTask[] expectedList) {
        commandBox.runCommand("revert");
        commandBox.runCommand("unrevert");
        assertResultMessage(UnrevertCommand.MESSAGE_SUCCESS);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
