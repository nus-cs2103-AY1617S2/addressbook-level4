package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.bulletjournal.commons.core.Messages;
import seedu.bulletjournal.testutil.TestTask;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); // no results
        assertFindResult("find milk", td.buymilk, td.dumpmilk); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find milk", td.dumpmilk);
        assertFindResult("finds milk", td.dumpmilk);
        assertFindResult("search milk", td.dumpmilk);
        assertFindResult("f milk", td.dumpmilk);
        assertFindResult("lookup milk", td.dumpmilk);
        assertFindResult("searches milk", td.dumpmilk);
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

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " persons listed!");
        assertTrue(personListPanel.isListMatching(expectedHits));
    }
}
