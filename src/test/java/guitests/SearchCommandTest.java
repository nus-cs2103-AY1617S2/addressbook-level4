package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.testutil.TestTask;

public class SearchCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("SEARCH Mark"); // no results
        assertFindResult("SEARCH Meier", td.benson, td.daniel); // multiple results

        //find after deleting one result
        commandBox.runCommand("DELETE 1");
        assertFindResult("SEARCH Meier", td.daniel);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("CLEAR");
        assertFindResult("SEARCH Jean"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("SEARCHgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
