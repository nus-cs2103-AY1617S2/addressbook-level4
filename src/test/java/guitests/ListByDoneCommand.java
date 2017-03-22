package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;

public class ListByDoneCommand extends TaskManagerGuiTest {
   
    @Test
    public void find_nonEmptyList() {
        assertListByDone("listdone"); // no results
        assertListByDone("ltag personal", td.apply); // single result
        assertListByDone("ltag school", td.buy); // single result


        //ltag after deleting one result
        commandBox.runCommand("list");
        commandBox.runCommand("delete 4");
        assertListByDone("ltag shopping", td.buy);


        //ltag after modifying the tag
        commandBox.runCommand("list");
        commandBox.runCommand("edit 1 t/nonpersonal");
        assertListByDone("ltag personal"); // no result

    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertListByDone("listag shopping"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("listtttag");
        commandBox.runCommand("ltagggg");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertListByDone(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
