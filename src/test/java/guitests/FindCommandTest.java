package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import typetask.commons.core.Messages;
import typetask.testutil.TestTask;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void findNonEmptyList() {
        assertFindResult("find Mark"); // no results
        assertFindResult("find Meier", td.benson, td.daniel); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Meier", td.daniel);
    }

    @Test
    public void findEmptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); // no results
    }

    @Test
    public void findInvalidCommandFail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " task(s) listed!");
        assertTrue(personListPanel.isListMatching(expectedHits));
    }
}
