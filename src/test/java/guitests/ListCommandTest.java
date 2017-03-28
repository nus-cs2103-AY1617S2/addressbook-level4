//@@author A0139925U
package guitests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import seedu.tache.commons.core.Messages;
import seedu.tache.testutil.TestTask;

public class ListCommandTest extends TaskManagerGuiTest {

    @Test
    public void listAllTasks() {
        assertListResult("list", "All", td.getTypicalTasks()); // no results
        assertListResult("list all", "All", td.getTypicalTasks()); // multiple results

        //list after deleting one result
        commandBox.runCommand("delete 1");
        TestTask[] tempTasksList = td.getTypicalTasks();
        ArrayList<TestTask> expectedTasksList = new ArrayList<TestTask>();
        for (int i = 0; i < tempTasksList.length - 1; i++) {
            expectedTasksList.add(tempTasksList[i + 1]);
        }
        assertListResult("list", "All", expectedTasksList.toArray(new TestTask[0]));
    }

    @Test
    public void listUncompletedTasks() {
        assertListResult("list uncompleted", "Uncompleted", td.getTypicalTasks()); // no results
        commandBox.runCommand("list");
        commandBox.runCommand("complete 1");
        TestTask[] tempTasksList = td.getTypicalTasks();
        ArrayList<TestTask> expectedTasksList = new ArrayList<TestTask>();
        for (int i = 0; i < tempTasksList.length - 1; i++) {
            expectedTasksList.add(tempTasksList[i + 1]);
        }
        assertListResult("list uncompleted", "Uncompleted", expectedTasksList.toArray(new TestTask[0]));
    }

    @Test
    public void listCompletedTasks() {
        assertListResult("list completed", "Completed"); // no results
        commandBox.runCommand("list");
        commandBox.runCommand("complete 1,2,3");
        TestTask[] tempTasksList = td.getTypicalTasks();
        ArrayList<TestTask> expectedTasksList = new ArrayList<TestTask>();
        for (int i = 0; i < 3; i++) {
            expectedTasksList.add(tempTasksList[i]);
        }
        assertListResult("list completed", "Completed", expectedTasksList.toArray(new TestTask[0]));
    }

    @Test
    public void listNoEvent() {
        commandBox.runCommand("clear");
        assertListResult("list", "All"); // no results
    }

    @Test
    public void findInvalidCommandFail() {
        commandBox.runCommand("listkkjksjkds");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertListResult(String command, String expectedMessage, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedMessage + " tasks listed");
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
