//@@author A0142255M

package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TaskCountTest extends TaskManagerGuiTest {

    @Test
    public void showTaskCountWithDefaultFilteredListOfAllTasks() {
        // mixture of timed and floating tasks
        assertEquals(taskCount.getFloatingTaskCount().getText(), "2");
        assertEquals(taskCount.getTimedTaskCount().getText(), "3");

        // no timed tasks
        commandBox.runCommand("clear");
        commandBox.runCommand(td.payDavid.getAddCommand());
        commandBox.runCommand(td.visitSarah.getAddCommand());
        assertEquals(taskCount.getTimedTaskCount().getText(), "0");
        assertEquals(taskCount.getFloatingTaskCount().getText(), "2");

        // no floating tasks
        commandBox.runCommand("clear");
        commandBox.runCommand(td.eggsAndBread.getAddCommand());
        commandBox.runCommand(td.readBook.getAddCommand());
        assertEquals(taskCount.getFloatingTaskCount().getText(), "0");
        assertEquals(taskCount.getTimedTaskCount().getText(), "2");

        // no tasks at all: list is empty
        commandBox.runCommand("clear");
        assertEquals(taskCount.getFloatingTaskCount().getText(), "0");
        assertEquals(taskCount.getTimedTaskCount().getText(), "0");
    }

    @Test
    public void showTaskCountWithUserSelectedFilteredListUsingListCommand() {
        commandBox.runCommand("complete 1, 2, 4");

        // list complete
        commandBox.runCommand("list completed");
        assertEquals(taskCount.getFloatingTaskCount().getText(), "2");
        assertEquals(taskCount.getTimedTaskCount().getText(), "1");

        // list uncompleted
        commandBox.runCommand("list uncompleted");
        assertEquals(taskCount.getFloatingTaskCount().getText(), "0");
        assertEquals(taskCount.getTimedTaskCount().getText(), "2");

        // list timed
        commandBox.runCommand("list timed");
        assertEquals(taskCount.getFloatingTaskCount().getText(), "0");
        assertEquals(taskCount.getTimedTaskCount().getText(), "2");

        // list floating
        commandBox.runCommand("list floating");
        assertEquals(taskCount.getFloatingTaskCount().getText(), "0");
        assertEquals(taskCount.getTimedTaskCount().getText(), "0");
    }

}
