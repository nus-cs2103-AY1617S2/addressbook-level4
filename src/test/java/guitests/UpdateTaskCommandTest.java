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
        Task task2 = new Task(newDescription, null, null);
        task2.replaceTags(new ArrayList<>(Arrays.asList(tag2)));
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));

        // update tags with new tags
        command = "update 2 tags/tag1 tag3";
        commandBox.runCommand(command);
        Task task3 = new Task(newDescription, null, null);
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
        String command = "add " + taskDescription + " enddate/" + endDate;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, endDate);
        assertTrue(isTaskShown(task));

        // update task deadline
        LocalDateTime newEndDate = DateTimeUtil.parseDateString("22 Mar 2017, 11am");
        command = "update " + eventIndex + " enddate/" + newEndDate;
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription, newEndDate);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));

        // update task description
        String newTaskDescription = "complete v0.2";
        command = "update " + eventIndex + " " + newTaskDescription;
        commandBox.runCommand(command);
        Task task3 = new Task(newTaskDescription, newEndDate);
        assertFalse(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertTrue(isTaskShown(task3));

        // update all parameters for task with deadline and tags
        String newerTaskDescription = "get v0.2 ready";
        LocalDateTime newerEndDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        command = "update " + eventIndex + " " + newerTaskDescription + " enddate/" + newerEndDate + " tags/tag1 tag3";
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
        String command = "add " + taskDescription + " startdate/" + startDate + " enddate/" + endDate;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, startDate, endDate);
        assertTrue(isTaskShown(task));

        // update event start date and end date
        LocalDateTime newStartDate = DateTimeUtil.parseDateString("22 Mar 2017, 12pm");
        LocalDateTime newEndDate = DateTimeUtil.parseDateString("22 Mar 2017, 1pm");
        command = "update " + eventIndex + " startdate/" + newStartDate + " enddate/" + newEndDate;
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription, newStartDate, newEndDate);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));

        // update event description
        String newTaskDescription = "participate in CS2103 tutorial";
        command = "update " + eventIndex + " " + newTaskDescription;
        commandBox.runCommand(command);
        Task task3 = new Task(newTaskDescription, newStartDate, newEndDate);
        assertFalse(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertTrue(isTaskShown(task3));

        // update all parameters for event (and test tags, startdate and enddate not in order)
        String newerTaskDescription = "attend CS2103T tutorial";
        LocalDateTime newerStartDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime newerEndDate = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        command = "update " + eventIndex + " " + newerTaskDescription +
                  " enddate/" + newerEndDate + " tags/tag1 "  + " startdate/" + newerStartDate;
        commandBox.runCommand(command);
        Task task4 = new Task(newerTaskDescription, newerStartDate, newerEndDate);
        task4.replaceTags(new ArrayList<>(Arrays.asList(tag1)));
        assertFalse(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        assertTrue(isTaskShown(task4));
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
}
