package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.model.Task;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Gui tests for update task command
 */
public class UpdateTaskCommandTest extends ToLuistGuiTest {
    @Test
    public void updateTaskDescription() {
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        String newDescription = "do homework for Melvin";
        String command = "update 1 " + newDescription;
        task.setDescription(newDescription);
        commandBox.runCommand(command);
        assertFalse(isTaskShown(new TypicalTestTodoLists().getTypicalTasks()[0]));
        assertTrue(isTaskShown(task));
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

        // update all parameters for task with deadline
        String newerTaskDescription = "get v0.2 ready";
        LocalDateTime newerEndDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        command = "update " + eventIndex + " " + newerTaskDescription + " enddate/" + newerEndDate;
        commandBox.runCommand(command);
        Task task4 = new Task(newerTaskDescription, newerEndDate);
        assertFalse(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        assertFalse(isTaskShown(task4));
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

        // update all parameters for event
        String newerTaskDescription = "attend CS2103T tutorial";
        LocalDateTime newerStartDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime newerEndDate = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        command = "update " + eventIndex + " " + newerTaskDescription +
                " startdate/" + newerStartDate + " enddate/" + newerEndDate;
        commandBox.runCommand(command);
        Task task4 = new Task(newerTaskDescription, newerStartDate, newerEndDate);
        assertFalse(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        assertFalse(isTaskShown(task4));
    }
}
