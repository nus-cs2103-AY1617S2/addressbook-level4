package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.geekeep.commons.core.Messages;
import seedu.geekeep.testutil.TestTask;

public class FindCommandTest extends GeeKeepGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Travel"); // no results
        assertFindResult("find Trip", td.japan, td.spain); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Trip", td.spain);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Fishing"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findevent");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
