package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.TestTask;
import seedu.task.commons.core.Messages;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); // no results
        assertFindResult("find Task44", td.task44); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Task77", td.task77);

        //find task after deleting it
        commandBox.runCommand("delete 1");
        commandBox.runCommand("find Task77");
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
        assertResultMessage(expectedHits.length
                + " Task(s) listed! Including " + expectedHits.length
                + " Exact Match case(s) & 0 Near Match case(s).");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
