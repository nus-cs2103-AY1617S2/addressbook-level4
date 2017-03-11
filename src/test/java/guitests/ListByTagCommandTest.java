package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;

public class ListByTagCommandTest extends TaskManagerGuiTest {
    @Test
    public void find_nonEmptyList() {
        assertListByTagResult("listag friends"); // no results
        assertListByTagResult("ltag personal", td.apply); // single result
        assertListByTagResult("ltag school", td.buy); // single result


        //ltag after deleting one result
        commandBox.runCommand("list");
        commandBox.runCommand("delete 4");
        assertListByTagResult("ltag shopping", td.buy);


        //ltag after modifying the tag
        commandBox.runCommand("list");
        commandBox.runCommand("edit 1 t/nonpersonal");
        assertListByTagResult("ltag personal"); // no result

    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertListByTagResult("listag shopping"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("listtttag");
        commandBox.runCommand("ltagggg");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertListByTagResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
