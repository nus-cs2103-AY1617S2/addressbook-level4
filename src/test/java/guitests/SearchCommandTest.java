package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.testutil.TestTask;

public class SearchCommandTest extends TaskManagerGuiTest {

    // @@author A0141102H
    @Test
    public void find_nonEmptyList() {
        assertFindResult("SEARCH food"); // no results
        assertFindResult("SEARCH with", td.eatBreakfast, td.eatDinner); // multiple
                                                                        // results

        // find after deleting one result
        commandBox.runCommand("DELETE 1");
        assertFindResult("SEARCH with", td.eatDinner);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("CLEAR");
        assertFindResult("SEARCH regret"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("SEARCHregret");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
