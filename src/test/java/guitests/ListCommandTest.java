//@@author A0139925U
package guitests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import seedu.tache.commons.core.Messages;
import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.testutil.TaskBuilder;
import seedu.tache.testutil.TestTask;

public class ListCommandTest extends TaskManagerGuiTest {

    @Test
    public void list_allTasks_success() {
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
    public void list_uncompletedTasks_success() {
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
    public void list_completedTasks_success() {
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
    public void list_timedTasks_success() {
        assertListResult("list timed", "Timed", td.eggsAndBread, td.visitFriend, td.readBook);
        commandBox.runCommand("list");
        commandBox.runCommand("edit 2 change sd to 5 jun and change st to 9am");
        TestTask[] tempTasksList = td.getTypicalTasks();
        ArrayList<TestTask> expectedTasksList = new ArrayList<TestTask>();
        for (int i = 1; i < 5; i++) {
            expectedTasksList.add(tempTasksList[i]);
        }
        assertListResult("list timed", "Timed", expectedTasksList.toArray(new TestTask[0]));
    }

    @Test
    public void list_floatingTasks_success() throws IllegalValueException {
        assertListResult("list floating", "Floating", td.payDavid, td.visitSarah);
        commandBox.runCommand("list");
        commandBox.runCommand("add watch tv");
        TestTask[] tempTasksList = td.getTypicalTasks();
        ArrayList<TestTask> expectedTasksList = new ArrayList<TestTask>();
        for (int i = 0; i < 2; i++) {
            expectedTasksList.add(tempTasksList[i]);
        }
        expectedTasksList.add(new TaskBuilder().withName("watch tv").build());
        assertListResult("list floating", "Floating", expectedTasksList.toArray(new TestTask[0]));
    }
    //@@author

    @Test
    public void list_noEvent_success() {
        commandBox.runCommand("clear");
        assertListResult("list", "Uncompleted");
    }

    @Test
    public void list_invalidCommand_failure() {
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
