package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.testutil.TestTask;

public class FindCommandTest extends TaskListGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); // no results
        assertFindResult("find CS2103T", td.tutorial, td.java); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find CS2103T", td.java);
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
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
