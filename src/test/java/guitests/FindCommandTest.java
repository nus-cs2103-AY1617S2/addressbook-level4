package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import t15b1.taskcrusher.commons.core.Messages;
import t15b1.taskcrusher.testutil.TestCard;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); // no results
        assertFindResult("find Meier", td.benson, td.daniel); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Meier", td.daniel);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestCard... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " persons listed!");
        assertTrue(personListPanel.isListMatching(expectedHits));
    }
}
