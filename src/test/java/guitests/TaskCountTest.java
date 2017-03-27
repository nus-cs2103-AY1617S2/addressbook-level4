//@@author A0142255M

package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TaskCountTest extends TaskManagerGuiTest {

    @Test
    public void showZeroTaskCount() {
        // no timed tasks
        commandBox.runCommand("clear");
        commandBox.runCommand(td.payDavid.getAddCommand());
        commandBox.runCommand(td.visitSarah.getAddCommand());
        assertEquals(taskCount.getTimedTaskCount().getText(), "0");

        // no floating tasks
        commandBox.runCommand("clear");
        commandBox.runCommand(td.eggsAndBread.getAddCommand());
        commandBox.runCommand(td.readBook.getAddCommand());
        assertEquals(taskCount.getFloatingTaskCount().getText(), "0");

        // no tasks at all: list is empty
        commandBox.runCommand("clear");
        assertEquals(taskCount.getFloatingTaskCount().getText(), "0");
        assertEquals(taskCount.getTimedTaskCount().getText(), "0");
    }

    @Test
    public void showPositiveTaskCount() {
        // timed tasks
        commandBox.runCommand("clear");
        commandBox.runCommand(td.eggsAndBread.getAddCommand());
        commandBox.runCommand(td.readBook.getAddCommand());
        assertEquals(taskCount.getTimedTaskCount().getText(), "2");

        // floating tasks
        commandBox.runCommand(td.payDavid.getAddCommand());
        commandBox.runCommand(td.visitSarah.getAddCommand());
        assertEquals(taskCount.getFloatingTaskCount().getText(), "2");

        // mixture of timed and floating tasks
        assertEquals(taskCount.getFloatingTaskCount().getText(), "2");
        assertEquals(taskCount.getTimedTaskCount().getText(), "2");
    }

}
