package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClearCommandTest extends TodoListGuiTest {

    @Test
    public void clear() {
        //verify a non-empty list can be cleared
        assertTrue(todoListPanel.isListMatching(true, td.getTypicalTodos()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.laundry.getAddCommand());
        assertTrue(todoListPanel.isListMatching(true, td.laundry));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Todo list has been cleared!");
    }
}
