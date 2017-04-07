package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.testutil.TestTask;
//@@author A0142487Y
public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList_success() {

        //find full name
        assertFindResult("find dinner", td.eat, td.decide); // multiple results

        //find substring
        assertFindResult("find din", td.eat, td.decide); // multiple results

        // find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find dinner", td.decide);
    }

    @Test
    public void find_nonEmptyList_byName_success() {
        assertFindResult("find dinner", td.eat, td.decide); // 2 results
        assertFindResult("find Mark"); // no results

    }

    @Test
    public void find_nonEmptyList_byTag_success() {
        assertFindResult("find personal", td.apply);
    }

    @Test
    public void find_nonEmptyList_byLocation_success() {
        try {
            commandBox.runCommand(new TestTask(td.jump).getAddCommand());
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertFindResult("find ERC field", td.jump);

    }

    @Test
    public void find_nonEmptyList_byRemark_success() {
        try {
            commandBox.runCommand(new TestTask(td.neglect).getAddCommand());
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertFindResult("find remark", td.neglect);
    }

    @Test
    public void find_nonEmptyList_byDate_success() {
        try {
            commandBox.runCommand(new TestTask(td.look).getAddCommand());
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertFindResult("find Feb 27 2112", td.look);
    }

    @Test
    public void find_nonEmptyList_byDate_fail() {
        assertFindResult("find tomorrow");
        assertFindResult("find last tuesday");
        assertFindResult("find next christmas");
    }

    @Test
    public void find_nonEmptyList_ConfusingNameAndDate_success() {
        try {
            commandBox.runCommand(new TestTask(td.open).getAddCommand());
            commandBox.runCommand(new TestTask(td.practice).getAddCommand());
            commandBox.runCommand(new TestTask(td.queue).getAddCommand());
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertFindResult("find June 4 2018", td.open);
        assertFindResult("find new year", td.practice);
        assertFindResult("find 1/2/2018", td.practice);
        assertFindResult("find christmas", td.queue);
    }

    @Test
    public void find_nonEmptyList_ConfusingNameAndDate_fail() {
        try {
            commandBox.runCommand(new TestTask(td.open).getAddCommand());
            commandBox.runCommand(new TestTask(td.practice).getAddCommand());
            commandBox.runCommand(new TestTask(td.queue).getAddCommand());
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertFindResult("find June 4 2017");
     // Jan 2 forms an object with the current year:2017,so this test case will succeed next year
        assertFindResult("find Jan 2");
        assertFindResult("find 12/25/2017");

    }

    @Test
    public void find_emptyList_success() {
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
