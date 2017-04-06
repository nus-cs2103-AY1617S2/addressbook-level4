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
        assertListResult("list all", "All", td.getTypicalTasks());

        //list after deleting one result
        commandBox.runCommand("delete 1");
        TestTask[] tempTasksList = td.getTypicalTasks();
        ArrayList<TestTask> expectedTasksList = new ArrayList<TestTask>();
        for (int i = 0; i < tempTasksList.length - 1; i++) {
            expectedTasksList.add(tempTasksList[i + 1]);
        }
        assertListResult("list all", "All", expectedTasksList.toArray(new TestTask[0]));
    }

    @Test
    public void listUncompletedTasks() {
        assertListResult("list uncompleted", "Uncompleted", td.getTypicalTasks());
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
        assertListResult("list completed", "Completed");
        commandBox.runCommand("list");
        commandBox.runCommand("complete 1,2,3");
        TestTask[] tempTasksList = td.getTypicalTasks();
        ArrayList<TestTask> expectedTasksList = new ArrayList<TestTask>();
        for (int i = 0; i < 3; i++) {
            expectedTasksList.add(tempTasksList[i]);
        }
        assertListResult("list completed", "Completed", expectedTasksList.toArray(new TestTask[0]));
    }

    //@@author A0142255M
    @Test
    public void listTimedTasks() {
        assertListResult("list timed", "Timed");
        commandBox.runCommand("list");
        TestTask[] tempTasksList = td.getTypicalTasks();
        ArrayList<TestTask> expectedTasksList = new ArrayList<TestTask>();
        for (TestTask task : tempTasksList) {
            expectedTasksList.add(task);
        }
        assertListResult("list timed", "Timed", expectedTasksList.toArray(new TestTask[0]));
    }

    @Test
    public void listFloatingTasks() {
        assertListResult("list floating", "Floating");
        commandBox.runCommand("list");
        TestTask[] tempTasksList = td.getTypicalTasks();
        ArrayList<TestTask> expectedTasksList = new ArrayList<TestTask>();
        for (TestTask task : tempTasksList) {
            expectedTasksList.add(task);
        }
        assertListResult("list floating", "Floating", expectedTasksList.toArray(new TestTask[0]));
    }
    //@@author

    @Test
    public void listNoEvent() {
        commandBox.runCommand("clear");
        assertListResult("list", "Uncompleted");
    }

    @Test
    public void listInvalidCommandFail() {
        commandBox.runCommand("listkkjksjkds");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertListResult(String command, String expectedMessage, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertResultMessage(expectedMessage + " tasks listed");
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
