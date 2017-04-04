package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskit.commons.core.Messages;
import seedu.taskit.testutil.TestTask;

//@@author A0141872E
public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find assignment"); // no results
        assertFindResult("find HW", td.hw1, td.hw2);// multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find HW", td.hw2);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find HW"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findHW");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
