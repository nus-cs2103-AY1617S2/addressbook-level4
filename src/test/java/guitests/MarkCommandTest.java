//@@author A0127545A
package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.model.Task;

public class MarkCommandTest extends ToLuistGuiTest {
    @Test
    public void markMultipleRecurringTaskAsCompletedMultipleTimes() {
        // add recurring floating task
        String taskDescription = "do homework for Melvin";
        String recurFrequencyString = "daily";
        LocalDateTime recurUntilEndDate = DateTimeUtil.parseDateString("15 May 2017, 12pm");
        String command = "add " + taskDescription + " repeat/" + recurFrequencyString
                       + " repeatuntil/" + recurUntilEndDate;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription);
        task.setRecurring(recurUntilEndDate, recurFrequencyString);
        assertTrue(isTaskShown(task));

        // add task with deadline
        String taskDescription2 = "get v0.4 ready";
        String recurFrequencyString2 = "monthly";
        LocalDateTime recurUntilEndDate2 = DateTimeUtil.parseDateString("15 May 2017, 12pm");
        LocalDateTime endDate2 = DateTimeUtil.parseDateString("30 Apr 2017, 12pm");
        String command2 = "add " + taskDescription2 + " by/" + endDate2 + " repeat/" + recurFrequencyString2
                + " repeatuntil/" + recurUntilEndDate2;
        commandBox.runCommand(command2);
        Task task2 = new Task(taskDescription2, endDate2);
        task2.setRecurring(recurUntilEndDate2, recurFrequencyString2);
        assertTrue(isTaskShown(task2));

        // add event
        String taskDescription3 = "attend CS2103T tutorial";
        String recurFrequencyString3 = "weekly";
        LocalDateTime startDate3 = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime endDate3 = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        String command3 = "add " + taskDescription3 + " from/" + startDate3 + " to/" + endDate3
                        + " repeat/" + recurFrequencyString3;
        commandBox.runCommand(command3);
        Task task3 = new Task(taskDescription3, startDate3, endDate3);
        task3.setRecurring(recurFrequencyString3);
        assertTrue(isTaskShown(task3));

        String markCompleteAllCommand = "mark complete -";
        commandBox.runCommand(markCompleteAllCommand);
        assertTrue(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        task2.updateToNextRecurringTask();
        task3.updateToNextRecurringTask();
        assertTrue(isTaskShown(task2));
        assertTrue(isTaskShown(task3));

        commandBox.runCommand(markCompleteAllCommand);
        assertTrue(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        task2.updateToNextRecurringTask();
        task3.updateToNextRecurringTask();
        assertFalse(isTaskShown(task2)); // recurring task reached end date, so it got marked as completed for real
        assertTrue(isTaskShown(task3));
    }

    @Test
    public void markRecurringEventAsCompleted_endingThirtyFirstOfTheMonth() {
        // add event
        String taskDescription = "attend CS2103T tutorial";
        String recurFrequencyString = "monthly";
        LocalDateTime startDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime endDate = DateTimeUtil.parseDateString("31 Mar 2017, 1pm");
        String command = "add " + taskDescription + " from/" + startDate + " to/" + endDate
                        + " repeat/" + recurFrequencyString;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, startDate, endDate);
        task.setRecurring(recurFrequencyString);
        assertTrue(isTaskShown(task));

        command = "mark complete -";
        commandBox.runCommand(command);
        assertFalse(isTaskShown(task));
        LocalDateTime newStartDate = startDate.plusDays(61);
        LocalDateTime newEndDate = endDate.plusDays(61);
        Task task2 = new Task(taskDescription, newStartDate, newEndDate);
        task2.setRecurring(recurFrequencyString);
        assertTrue(isTaskShown(task2));
    }
}
