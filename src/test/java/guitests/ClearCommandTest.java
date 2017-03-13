package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClearCommandTest extends DoistGUITest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(personListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.email.getAddCommand());
        assertTrue(personListPanel.isListMatching(td.email));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("To-do list has been cleared!");
    }
}
