package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.TestTask;
import seedu.task.commons.core.Messages;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); // no results
        assertFindResult("find Task4", td.task4); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Task7", td.task7);

        //find task after deleting it
        commandBox.runCommand("delete 1");
        commandBox.runCommand("find Task7");
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
