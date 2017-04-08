package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void findNonEmptyList() {
        commandBox.runCommand(td.benson.getAddCommand());
        commandBox.runCommand(td.daniel.getAddCommand());
        assertFindResult("find Mark"); // no results
        assertFindResult("find Say", td.benson, td.daniel); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Meier", td.daniel);
        commandBox.runCommand("undo");
        assertFindResult("find Say", td.benson, td.daniel);
    }

    @Test
    public void findEmptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); // no results
    }
    //
    @Test
    public void findInvalidCommandFail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
