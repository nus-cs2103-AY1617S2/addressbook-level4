package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.onetwodo.commons.core.Messages;
import seedu.onetwodo.testutil.TestTask;

public class FindCommandTest extends ToDoListGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Nemo"); // no results
        assertFindResult("find change", td.taskH, td.taskI); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete t2");
        assertFindResult("find change", td.taskH);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Dory"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("finds something");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        //TODO: fix tests below....(taskListPanel's node method).
       // assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        //assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
