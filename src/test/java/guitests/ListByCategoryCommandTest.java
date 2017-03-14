package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.testutil.TestTask;

public class ListByCategoryCommandTest extends TaskBossGuiTest {
    @Test
    public void listByCategory_nonEmptyList() {
        assertListByCategoryResult("listcategory wife"); // no results
        assertListByCategoryResult("listcategory friends", td.alice, td.benson); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertListByCategoryResult("find friends", td.benson);
    }

    @Test
    public void listByCategory_emptyList() {
        commandBox.runCommand("clear");
        assertListByCategoryResult("listcategory friends"); // no results
    }

    @Test
    public void listByCategory_invalidCommand_fail() {
        commandBox.runCommand("listbycategory");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertListByCategoryResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
