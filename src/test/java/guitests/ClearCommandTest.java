package guitests;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import project.taskcrusher.testutil.TestTaskCard;

public class ClearCommandTest extends TaskcrusherGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        TestTaskCard[] taskList = td.getTypicalTasks();
        Arrays.sort(taskList);
        assertTrue(userInboxPanel.isListMatching(taskList));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.assignment.getAddCommand());
        assertTrue(userInboxPanel.isListMatching(td.assignment));
        commandBox.runCommand("delete t 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Active list has been cleared!");
    }
}
