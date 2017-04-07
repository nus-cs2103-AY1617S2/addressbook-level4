//@@author A0139925U
package guitests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import seedu.tache.commons.core.Messages;
import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.model.task.DateTime;
import seedu.tache.testutil.TaskBuilder;
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
        assertListResult("list timed", "Timed", td.eggsAndBread, td.visitFriend, td.readBook);
        commandBox.runCommand("list");
        commandBox.runCommand("edit 2 change sd to 2 months ago and change st to 9am and change ed to 1 months ago");
        TestTask[] tempTasksList = td.getTypicalTasks();
        ArrayList<TestTask> expectedTasksList = new ArrayList<TestTask>();
        for (int i = 1; i < 5; i++) {
            expectedTasksList.add(tempTasksList[i]);
        }
        assertListResult("list timed", "Timed", expectedTasksList.toArray(new TestTask[0]));
    }

    @Test
    public void listFloatingTasks() throws IllegalValueException {
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
    public void listNoEvent() {
        commandBox.runCommand("clear");
        assertListResult("list", "Uncompleted");
    }

    @Test
    public void listInvalidCommandFail() {
        commandBox.runCommand("listkkjksjkds");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    //@@author A0139961U
    @Test
    public void listTodayTasks() throws IllegalValueException {
        assertListResult("list today", "Today");
        commandBox.runCommand("list");
        commandBox.runCommand("edit 1 change ed to today");
        TestTask[] tempTasksList = td.getTypicalTasks();
        ArrayList<TestTask> expectedTasksList = new ArrayList<TestTask>();
        tempTasksList[0].setEndDateTime(new DateTime("today 00:00"));
        expectedTasksList.add(tempTasksList[0]);

        assertListResult("list today", "Today", expectedTasksList.toArray(new TestTask[0]));
    }

    //@@author

    private void assertListResult(String command, String expectedMessage, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertResultMessage(expectedMessage + " tasks listed");
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

}
