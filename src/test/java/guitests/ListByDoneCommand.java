package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;

public class ListByDoneCommand extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        //assertListByDone("listdone"); // no results
        commandBox.runCommand("done 1");
        TestTask[] currentList = td.getTypicalTasks();
        assertListByDone("listdone", currentList[0]);


    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertListByDone("listdone"); // no results
    }


    private void assertListByDone(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " done tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
