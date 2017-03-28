package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.model.task.Status;
import seedu.tasklist.testutil.TestTask;

public class FindCommandTest extends TaskListGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); // no results
        assertFindResult("find cs2103T", td.tutorial, td.java); // multiple results
        assertFindResult("find cs", td.tutorial, td.homework, td.java);
        assertFindResult("find cs tut", td.tutorial);

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find cS2103T", td.java);
    }

    @Test
    public void find_nonEmptyList_byTags() {
        assertFindResult("find t/nosuchtag"); // no results
        assertFindResult("find t/2103", td.tutorial, td.java); // multiple results

        //find more restrictive tags
        assertFindResult("find t/class 2103", td.tutorial);

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find t/2103", td.java);
    }

    @Test
    public void find_nonEmptyList_byStatus() {
        commandBox.runCommand("done 1");
        td.tutorial.setStatus(new Status(true));
        assertFindResult("find s/completed", td.tutorial);
        assertFindResult("find s/not completed", td.homework, td.groceries, td.java, td.project, td.drink);

        //find after deleting
        commandBox.runCommand("delete 1");
        assertFindResult("find s/not completed", td.groceries, td.java, td.project, td.drink);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find CS2103T"); // no results
        assertFindResult("find t/2103"); // no results
        assertFindResult("find s/completed"); // no results
        assertFindResult("find s/not completed"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    //@@author A0139747N
    @Test
    public void findWithFlexibleCommand() {
        assertFindResult("locate CS2103T", td.tutorial, td.java); // multiple results
    }
    //@@author

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
