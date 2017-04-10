package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClearCommandTest extends EzDoGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess(false);

        //verify a non-empty list can be cleared using the short clear command
        commandBox.runCommand(td.jack.getAddCommand(true));
        assertListSize(1);
        assertClearCommandSuccess(true);

        //verify other commands can work after a clear command
        commandBox.runCommand(td.hoon.getAddCommand(false));
        assertTrue(taskListPanel.isListMatching(td.hoon));
        commandBox.runCommand("kill 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess(false);
    }

    private void assertClearCommandSuccess(boolean usesShortCommand) {
        if (usesShortCommand) {
            commandBox.runCommand("c");
        } else {
            commandBox.runCommand("clear");
        }
        assertListSize(0);
        assertResultMessage("EzDo has been cleared!");
    }
}
