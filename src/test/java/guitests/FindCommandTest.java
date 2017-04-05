package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.tache.commons.core.Messages;
import seedu.tache.testutil.TestTask;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void findNonEmptyList() {
        assertFindResult("find Dinner"); // no results
        assertFindResult("find Visit", td.visitSarah, td.visitFriend); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Visit", td.visitFriend);
    }

    @Test
    public void findEmptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Book"); // no results
    }

    @Test
    public void findInvalidCommandFail() {
        commandBox.runCommand("findsoftwareengineering");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " task(s) listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
