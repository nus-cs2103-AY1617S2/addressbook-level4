package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClearCommandTest extends TodoListGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess("clear");

        //verify other commands can work after a clear command
        commandBox.runCommand(td.hangclothes.getAddCommand("add "));
        assertTrue(taskListPanel.isListMatching(td.hangclothes));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess("clear");

        //verify other commands can work after a clear command
        commandBox.runCommand(td.hangclothes.getAddCommand("add "));
        assertTrue(taskListPanel.isListMatching(td.hangclothes));
        commandBox.runCommand("delete 1");
        assertListSize(0);
        assertClearCommandSuccess("clr");


        //verify other commands can work after a clear command
        commandBox.runCommand(td.hangclothes.getAddCommand("add "));
        assertTrue(taskListPanel.isListMatching(td.hangclothes));
        commandBox.runCommand("delete 1");
        assertListSize(0);
        assertClearCommandSuccess("c");

        //verify other commands can work after a clear command
        commandBox.runCommand(td.hangclothes.getAddCommand("add "));
        assertTrue(taskListPanel.isListMatching(td.hangclothes));
        commandBox.runCommand("delete 1");
        assertListSize(0);
        assertClearCommandSuccess("clears");

        //verify other commands can work after a clear command
        commandBox.runCommand(td.hangclothes.getAddCommand("add "));
        assertTrue(taskListPanel.isListMatching(td.hangclothes));
        commandBox.runCommand("delete 1");
        assertListSize(0);
        assertClearCommandSuccess("empty");


        //verify other commands can work after a clear command
        commandBox.runCommand(td.hangclothes.getAddCommand("add "));
        assertTrue(taskListPanel.isListMatching(td.hangclothes));
        commandBox.runCommand("delete 1");
        assertListSize(0);
        assertClearCommandSuccess("empties");

    }

    private void assertClearCommandSuccess(String clearCommand) {
        commandBox.runCommand(clearCommand);
        assertListSize(0);
        assertResultMessage("Todo list has been cleared!");
    }
}
