//@@author A0142255M

package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.tache.testutil.TestTask;

public class TaskCardTest extends TaskManagerGuiTest {

    @Test
    public void showTimedTask() {
        // task with start date and time only
        TestTask taskToShow = td.eggsAndBread;
        commandBox.runCommand(taskToShow.getAddCommand());
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToShow.getName().fullName);
        assertEquals(addedCard.getEndDate(), taskToShow.getEndDateTime().get().getDateOnly());
        assertEquals(addedCard.getEndTime(), taskToShow.getEndDateTime().get().getTimeOnly());

        // task with end date and time only
        taskToShow = td.readBook;
        commandBox.runCommand(taskToShow.getAddCommand());
        addedCard = taskListPanel.navigateToTask(taskToShow.getName().fullName);
        assertEquals(addedCard.getEndDate(), taskToShow.getEndDateTime().get().getDateOnly());
        assertEquals(addedCard.getEndTime(), taskToShow.getEndDateTime().get().getTimeOnly());

        // task with start date and time as well as end date and time
        taskToShow = td.visitFriend;
        commandBox.runCommand(taskToShow.getAddCommand());
        addedCard = taskListPanel.navigateToTask(taskToShow.getName().fullName);
        assertEquals(addedCard.getStartDate(), taskToShow.getStartDateTime().get().getDateOnly());
        assertEquals(addedCard.getStartTime(), taskToShow.getStartDateTime().get().getTimeOnly());
        assertEquals(addedCard.getEndDate(), taskToShow.getEndDateTime().get().getDateOnly());
        assertEquals(addedCard.getEndTime(), taskToShow.getEndDateTime().get().getTimeOnly());
    }

}
