package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import onlythree.imanager.logic.commands.ClearCommand;

public class ClearCommandTest extends TaskListGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks())); // TODO UI
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.helpMe.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.helpMe));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage(ClearCommand.MESSAGE_SUCCESS);
    }
}
