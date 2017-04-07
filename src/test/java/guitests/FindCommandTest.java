package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;

public class FindCommandTest extends TaskListGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mop"); // no results
        assertFindResult("find dog", td.bet, td.dog); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find dog", td.dog);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Jump"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findassignment");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks found!"); // TODO find
        assertTrue(personListPanel.isListMatching(expectedHits));
    }
}
