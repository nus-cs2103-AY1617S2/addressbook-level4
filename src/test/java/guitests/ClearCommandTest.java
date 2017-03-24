package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClearCommandTest extends TaskBossGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess(false);

        //verify other commands can work after a clear command
        commandBox.runCommand(td.taskH.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.taskH));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify the short clear command can work
        commandBox.runCommand(td.taskK.getShortAddCommand());
        assertListSize(1);
        assertClearCommandSuccess(true);

        //verify clear command works when the list is empty
        assertClearCommandSuccess(false);
    }

    private void assertClearCommandSuccess(boolean isShortCommand) {
        if (isShortCommand) {
            commandBox.runCommand("c");
        } else {
            commandBox.runCommand("clear");
        }
        assertListSize(0);
        assertResultMessage("TaskBoss has been cleared!");
    }
}
