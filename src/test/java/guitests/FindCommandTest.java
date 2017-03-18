package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTodo;

public class FindCommandTest extends TodoListGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find supper"); // no results
        assertFindResult("find homework", td.math, td.english); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find homework", td.english);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find homework"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findhomework");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTodo... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " todos listed!");
        assertTrue(todoListPanel.isListMatching(expectedHits));
    }
}
