package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;
import seedu.jobs.testutil.TestTask;

//@@author A0164440M
public class ListCommandTest extends TaskBookGuiTest {

    @Test
  //TODO add success cases listed by all, complete, in-progress
    public void list_nonEmptyList() throws IllegalArgumentException, IllegalTimeException {

        //list all tasks
        assertListWithoutArgumentResult("list", td.CS3101, td.CS3102, td.CS3103, td.CS3104,
                td.CS3105, td.CS3106, td.CS3107, td.CS5101);

        // complete some tasks
        int completeTasks = 6;
        for (int i = 0; i < completeTasks; i++) {
            commandBox.runCommand("complete " + (i + 1));
        }

        // list all completed tasks
        assertListWithArgumentResult("list complete", td.CS3101, td.CS3102, td.CS3103, td.CS3104,
                td.CS3105, td.CS3106);

        // list all in-progress tasks
        assertListWithArgumentResult("list in-progress", td.CS3107, td.CS5101);
    }

    @Test
    public void find_emptyList() throws IllegalArgumentException, IllegalTimeException {
        // list with wrong argument
        assertListWithArgumentResult("list abc");

        commandBox.runCommand("clear");
        assertListWithoutArgumentResult("list"); // no results
    }

    private void assertListWithArgumentResult(String command, TestTask... expectedHits)
            throws IllegalArgumentException, IllegalTimeException {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

    private void assertListWithoutArgumentResult(String command, TestTask... expectedHits)
            throws IllegalArgumentException, IllegalTimeException {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage("Listed all tasks");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
