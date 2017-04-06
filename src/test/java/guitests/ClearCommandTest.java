package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;

public class ClearCommandTest extends TaskBookGuiTest {

    @Test
    public void clear() throws IllegalArgumentException, IllegalTimeException {

        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.CS4101.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.CS4101));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Description book has been cleared!");
    }
}
