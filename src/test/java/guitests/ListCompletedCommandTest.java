package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListCompletedCommand;

public class ListCompletedCommandTest extends TaskManagerGuiTest {

    @Test
    public void openCompletedTask() throws InterruptedException {
        commandBox.runCommand(ListCompletedCommand.COMMAND_WORD);
        assertTrue(completedTaskListPanel.isWindowOpen());
        assertResultMessage(ListCompletedCommand.MESSAGE_SUCCESS);

        commandBox.runCommand(ListCompletedCommand.COMMAND_WORD);
        assertTrue(completedTaskListPanel.isWindowOpen());
        assertResultMessage(ListCompletedCommand.MESSAGE_SUCCESS);

        commandBox.runCommand(ListCommand.COMMAND_WORD);
        assertFalse(completedTaskListPanel.isWindowOpen());
        assertResultMessage(ListCommand.MESSAGE_SUCCESS);

        commandBox.runCommand(ListCommand.COMMAND_WORD);
        assertFalse(completedTaskListPanel.isWindowOpen());
        assertResultMessage(ListCommand.MESSAGE_SUCCESS);

        commandBox.runCommand(ListCompletedCommand.COMMAND_WORD);
        assertTrue(completedTaskListPanel.isWindowOpen());
        assertResultMessage(ListCompletedCommand.MESSAGE_SUCCESS);
    }

}
