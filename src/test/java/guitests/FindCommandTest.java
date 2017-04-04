package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.doist.commons.core.Messages;
import seedu.doist.testutil.TestTask;

public class FindCommandTest extends DoistGUITest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Apple"); // no results
        assertFindResult("find homework", td.homework); // one results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find homework");
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Milk"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findmilk");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
