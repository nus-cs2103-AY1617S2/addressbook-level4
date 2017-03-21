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
    public void find_nonEmptyList_byTags() {
        assertFindResult("find t/nosuchtag"); // no results
        assertFindResult("find t/2103", td.tutorial, td.java); // multiple results

        //find more restrictive tags
        assertFindResult("find t/class 2103", td.tutorial);

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find t/2103", td.java);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find CS2103T"); // no results
        assertFindResult("find t/2103"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void findWithFlexibleCommand() {
        assertFindResult("locate CS2103T", td.tutorial, td.java); // multiple results
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
