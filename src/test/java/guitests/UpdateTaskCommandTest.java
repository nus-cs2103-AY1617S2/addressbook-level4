package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.model.Task.TaskPriority;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Gui tests for update task command
 */
public class UpdateTaskCommandTest extends ToLuistGuiTest {
    private static Tag tag1 = new Tag("tag1");
    private static Tag tag2 = new Tag("tag2");
    private static Tag tag3 = new Tag("tag3");

    @Test
    public void updateFloatingTask() {
        // update description
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        String newDescription = "do homework for Melvin";
        String command = "update 1 " + newDescription;
        task.setDescription(newDescription);
        commandBox.runCommand(command);
        assertFalse(isTaskShown(new TypicalTestTodoLists().getTypicalTasks()[0]));
        assertTrue(isTaskShown(task));

        // update tags
        command = "update 2 tags/tag2";
        commandBox.runCommand(command);
        Task task2 = new Task(newDescription);
        task2.replaceTags(new ArrayList<>(Arrays.asList(tag2)));
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));

        // update tags with new tags
        command = "update 2 tags/tag1 tag3 priority/high";
        commandBox.runCommand(command);
        Task task3 = new Task(newDescription);
        task3.setTaskPriority(TaskPriority.HIGH);
        task3.replaceTags(new ArrayList<>(Arrays.asList(tag1, tag3)));
        assertFalse(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertTrue(isTaskShown(task3));
    }

    @Test
    public void updateTaskWithDeadline() {
        int eventIndex = 1;

        // add task with deadline
        String taskDescription = "get v0.2 ready";
        LocalDateTime endDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        String command = "add " + taskDescription + " by/" + endDate;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, endDate);
        assertTrue(isTaskShown(task));

        // update task deadline
        LocalDateTime newEndDate = DateTimeUtil.parseDateString("22 Mar 2017, 11am");
        command = "update " + eventIndex + " by/" + newEndDate;
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription, newEndDate);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));

        // update task description
        String newTaskDescription = "complete v0.2";
        command = "update " + eventIndex + " " + newTaskDescription + " priority/high";
        commandBox.runCommand(command);
        Task task3 = new Task(newTaskDescription, newEndDate);
        task3.setTaskPriority(TaskPriority.HIGH);
        assertFalse(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertTrue(isTaskShown(task3));

        // update all parameters for task with deadline and tags
        String newerTaskDescription = "get v0.2 ready";
        LocalDateTime newerEndDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        command = "update " + eventIndex + " " + newerTaskDescription + " by/" + newerEndDate +
                " priority/low tags/tag1 tag3";
        commandBox.runCommand(command);
        Task task4 = new Task(newerTaskDescription, null, newerEndDate);
        task4.replaceTags(new ArrayList<>(Arrays.asList(tag1, tag3)));
        assertFalse(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        assertTrue(isTaskShown(task4));
    }

    @Test
    public void updateEvents() {
        int eventIndex = 1;

        // add event
        String taskDescription = "attend CS2103T tutorial";
        LocalDateTime startDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime endDate = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        String command = "add " + taskDescription + " from/" + startDate + " to/" + endDate;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, startDate, endDate);
        assertTrue(isTaskShown(task));

        // update event start date and end date
        LocalDateTime newStartDate = DateTimeUtil.parseDateString("22 Mar 2017, 12pm");
        LocalDateTime newEndDate = DateTimeUtil.parseDateString("22 Mar 2017, 1pm");
        command = "update " + eventIndex + " from/" + newStartDate + " to/" + newEndDate + " priority/high";
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription, newStartDate, newEndDate);
        task2.setTaskPriority(TaskPriority.HIGH);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));

        // update event description
        String newTaskDescription = "participate in CS2103 tutorial";
        command = "update " + eventIndex + " " + newTaskDescription;
        commandBox.runCommand(command);
        Task task3 = new Task(newTaskDescription, newStartDate, newEndDate);
        task3.setTaskPriority(TaskPriority.HIGH);
        assertFalse(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertTrue(isTaskShown(task3));

        // update all parameters for event (and test tags, startdate and enddate not in order)
        String newerTaskDescription = "attend CS2103T tutorial";
        LocalDateTime newerStartDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime newerEndDate = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        command = "update " + eventIndex + " " + newerTaskDescription +
                  " priority/low to/" + newerEndDate + " tags/tag1 "  + " from/" + newerStartDate;
        commandBox.runCommand(command);
        Task task4 = new Task(newerTaskDescription, newerStartDate, newerEndDate);
        task4.replaceTags(new ArrayList<>(Arrays.asList(tag1)));
        assertFalse(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        assertTrue(isTaskShown(task4));
    }

    @Test
    public void updateMultipleTypeTask_shouldNotBeUpdated() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        LocalDateTime startDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime endDate = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        LocalDateTime endDate2 = DateTimeUtil.parseDateString("15 Mar 2017, 5pm");
        String command = "add " + taskDescription + " from/" + startDate + " to/" + endDate;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, startDate, endDate);
        assertTrue(isTaskShown(task));

        // Update to both event, and floating task
        command = "update " + eventIndex + " floating/" + " from/" + startDate + " to/" + endDate;
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));

        // Update to both event, and task with deadline
        command = "update " + eventIndex + " from/" + startDate + " to/" + endDate + " by/" + endDate2;
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));

        // Update to both floating task, and task with deadline
        command = "update " + eventIndex + " floating/" + " by/" + endDate2;
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));

        // Update to all floating task, task with deadline, event
        command = "update " + eventIndex + " by/" + endDate2 + " from/" + startDate + " to/" + endDate + " floating/";
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));
    }

    @Test
    public void updateFloatingTaskToEvent() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        LocalDateTime startDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime endDate = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        String command = "add " + taskDescription;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription);
        assertTrue(isTaskShown(task));

        // Event
        command = "update " + eventIndex + " from/" + startDate + " to/" + endDate;
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription, startDate, endDate);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }

    @Test
    public void updateFloatingTaskToTaskWithDeadline() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        LocalDateTime endDate2 = DateTimeUtil.parseDateString("15 Mar 2017, 5pm");
        String command = "add " + taskDescription;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription);
        assertTrue(isTaskShown(task));

        // Task with deadline
        command = "update " + eventIndex + " by/" + endDate2;
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription, endDate2);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }

    @Test
    public void updateTaskWithDeadlineToFloatingTask() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        LocalDateTime endDate2 = DateTimeUtil.parseDateString("15 Mar 2017, 5pm");
        String command = "add " + taskDescription + " by/" + endDate2;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, endDate2);
        assertTrue(isTaskShown(task));

        // Floating task
        command = "update " + eventIndex + " floating/";
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }

    @Test
    public void updateTaskWithDeadlineToEvent() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        LocalDateTime startDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime endDate = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        LocalDateTime endDate2 = DateTimeUtil.parseDateString("15 Mar 2017, 5pm");
        String command = "add " + taskDescription + " by/" + endDate2;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, endDate2);
        assertTrue(isTaskShown(task));

        // Task with deadline to event
        command = "update " + eventIndex + " from/" + startDate + " to/" + endDate;
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription, startDate, endDate);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }

    @Test
    public void updateEventToFloatingTask() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        LocalDateTime startDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime endDate = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        String command = "add " + taskDescription + " from/" + startDate + " to/" + endDate;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, startDate, endDate);
        assertTrue(isTaskShown(task));

        // Event to floating task
        command = "update " + eventIndex + " floating/";
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }

    @Test
    public void updateEventToTaskWithDeadline() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        LocalDateTime startDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime endDate = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        LocalDateTime endDate2 = DateTimeUtil.parseDateString("15 Mar 2017, 5pm");
        String command = "add " + taskDescription + " from/" + startDate + " to/" + endDate;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, startDate, endDate);
        assertTrue(isTaskShown(task));

        // Event to task with deadline
        command = "update " + eventIndex + " by/" + endDate2;
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription, endDate2);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }

    @Test
    public void updateTaskAfterMarkingComplete() {
        String markCompleteCommand = "mark 1";
        commandBox.runCommand(markCompleteCommand);

        // update description
        Task task = new TypicalTestTodoLists().getTypicalTasks()[1];
        String newDescription = "do homework for Melvin";
        String updateCommand = "update 1 " + newDescription;
        task.setDescription(newDescription);
        commandBox.runCommand(updateCommand);
        assertFalse(isTaskShown(new TypicalTestTodoLists().getTypicalTasks()[1]));
        assertTrue(isTaskShown(task));
    }

    @Test
    public void updateTaskWithInvalidPriorityLevel_shouldNotBeUpdated() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        String command = "add " + taskDescription;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription);
        assertTrue(isTaskShown(task));

        command = "update " + eventIndex + " priority/high low";
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));

        command = "update " + eventIndex + " priority/";
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));
    }

    @Test
    public void updateNonRecurringFloatingTaskToRecurringEvent() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        String command = "add " + taskDescription;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription);
        assertTrue(isTaskShown(task));

        LocalDateTime from = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime to = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        String recurFrequencyString = "weekly";
        command = "update " + eventIndex + " from/" + from + " to/" + to + " repeat/" + recurFrequencyString;
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription, from, to);
        task2.setRecurring(recurFrequencyString);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }

    @Test
    public void updateNonRecurringEventToRecurringEvent() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        LocalDateTime from = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime to = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        String command = "add " + taskDescription + " from/" + from + " to/" + to;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, from, to);
        assertTrue(isTaskShown(task));

        String recurFrequencyString = "monthly";
        LocalDateTime recurEndDate = DateTimeUtil.parseDateString("15 Mar 2018, 1pm");
        command = "update " + eventIndex + " repeat/" + recurFrequencyString + " until/" + recurEndDate;
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription, from, to);
        task2.setRecurring(recurEndDate, recurFrequencyString);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }

    @Test
    public void updateNonRecurringEventToRecurringTaskWithDeadline() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        LocalDateTime from = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime to = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        String command = "add " + taskDescription + " from/" + from + " to/" + to;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, from, to);
        assertTrue(isTaskShown(task));

        String recurFrequencyString = "yearly";
        LocalDateTime recurEndDate = DateTimeUtil.parseDateString("15 Mar 2020, 1pm");
        command = "update " + eventIndex + " by/" + to + " repeat/" + recurFrequencyString + " until/" + recurEndDate;
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription, to);
        task2.setRecurring(recurEndDate, recurFrequencyString);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }

    @Test
    public void updateRecurringTaskWithDeadlineToNonRecurringTaskWithDeadline() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        String recurFrequencyString = "daily";
        LocalDateTime to = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        String command = "add " + taskDescription + " by/" + to + " repeat/" + recurFrequencyString;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, to);
        task.setRecurring(recurFrequencyString);
        assertTrue(isTaskShown(task));

        command = "update " + eventIndex + " stoprepeating/";
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription, to);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }

    @Test
    public void updateRecurringEventToNonRecurringTaskWithDeadline() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        String recurFrequencyString = "weekly";
        LocalDateTime from = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime to = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        String command = "add " + taskDescription + " from/" + from + " to/" + to + " repeat/" + recurFrequencyString;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, from, to);
        task.setRecurring(recurFrequencyString);
        assertTrue(isTaskShown(task));

        command = "update " + eventIndex + " by/" + to + " stoprepeating/";
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription, to);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }

    @Test
    public void updateRecurringEventToNonRecurringFloatingTask() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        String recurFrequencyString = "weekly";
        LocalDateTime from = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime to = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        String command = "add " + taskDescription + " from/" + from + " to/" + to + " repeat/" + recurFrequencyString;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, from, to);
        task.setRecurring(recurFrequencyString);
        assertTrue(isTaskShown(task));

        command = "update " + eventIndex + " floating/";
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }
}
