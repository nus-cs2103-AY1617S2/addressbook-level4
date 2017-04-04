package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.taskit.testutil.TestTask;

public class ClearCommandTest extends AddressBookGuiTest {
    //@@author A0141011J
    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        TestTask[] expectedTasks = td.getTypicalTasks();
        Arrays.sort(expectedTasks);
        assertTrue(taskListPanel.isListMatching(expectedTasks));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.lunch.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.lunch));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Tasks have been cleared!");
    }
}
