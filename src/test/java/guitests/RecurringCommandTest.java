package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.logic.commands.RecurringCommand;
import seedu.taskmanager.testutil.TestTask;

public class RecurringCommandTest extends TaskManagerGuiTest {

    // @@author A0141102H
    @Test
    public void recurFloatingTaskFailure() {
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = currentList.length;
        commandBox.runCommand("RECUR " + targetIndex + " 3 days");
        assertResultMessage(RecurringCommand.MESSAGE_RECURRING_FLOATING_TASK_FAILURE);
    }

    @Test
    public void recurEventTaskSuccess() {
        // for days
        int targetIndex = 1;
        commandBox.runCommand("CLEAR");
        commandBox.runCommand(td.recurTestDay.getAddCommand());
        commandBox.runCommand("RECUR " + targetIndex + " 3 days");
        assertTrue(eventTaskListPanel.isListMatching(td.getTypicalRecurringEventTasksForDays()));

        // for weeks
        commandBox.runCommand("CLEAR");
        commandBox.runCommand(td.recurTestWeek.getAddCommand());
        commandBox.runCommand("RECUR " + targetIndex + " 3 weeks");
        assertTrue(eventTaskListPanel.isListMatching(td.getTypicalRecurringEventTasksForWeeks()));

    }

    @Test
    public void recurDeadlineTaskSuccess() {
        // for months
        int targetIndex = 1;
        commandBox.runCommand("CLEAR");
        commandBox.runCommand(td.recurTestMonth.getAddCommand());
        commandBox.runCommand("RECUR " + targetIndex + " 3 months");
        assertTrue(eventTaskListPanel.isListMatching(td.getTypicalRecurringDeadlineTasksForMonths()));

        // for years
        commandBox.runCommand("CLEAR");
        commandBox.runCommand(td.recurTestYear.getAddCommand());
        commandBox.runCommand("RECUR " + targetIndex + " 3 years");
        assertTrue(eventTaskListPanel.isListMatching(td.getTypicalRecurringDeadlineTasksForYears()));

    }
}
