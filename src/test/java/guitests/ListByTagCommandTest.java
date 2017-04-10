package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;
//@@author A0142487Y
public class ListByTagCommandTest extends TaskManagerGuiTest {
    @Test
    public void listByTag_nonEmptyList_success() {
        assertListByTagResult("list tag friends", "friends"); // no results
        assertListByTagResult("l tag personal", "personal", td.apply); // single result
        assertListByTagResult("l tag school", "school", td.buy); // single result

        // ltag after deleting one result
        commandBox.runCommand("list");
        commandBox.runCommand("delete 4");
        assertListByTagResult("l tag shopping", "shopping", td.buy);

        // ltag after modifying the tag
        commandBox.runCommand("list");
        commandBox.runCommand("edit 1 t/nonpersonal");
        assertListByTagResult("l tag personal", "personal"); // no result

    }

    @Test
    public void listByTag_emptyList_success() {
        commandBox.runCommand("clear");
        assertListByTagResult("list tag shopping", "shopping"); // no results
    }

    @Test
    public void listByTag_invalidCommand_fail() {
        commandBox.runCommand("listtttag");
        commandBox.runCommand("ltagggg");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertListByTagResult(String command, String tag, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks with tag " + "\"" + tag + "\"" + " listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
