package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find dragon"); // no results
        assertFindResult("find Meier", td.buyStove, td.buyRiceCooker); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Meier", td.buyRiceCooker);
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
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(futureTaskListPanel.isListMatching(expectedHits));
    }
}
