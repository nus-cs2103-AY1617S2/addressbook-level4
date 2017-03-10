package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.model.Task;

/**
 * Gui tests for add task command
 */
public class AddTaskCommandTest extends ToLuistGuiTest {

    IllegalArgumentException illegalArgumentException1 =
            new IllegalArgumentException("Description must not be empty.");
    IllegalArgumentException illegalArgumentException2 =
            new IllegalArgumentException("Start date must be before end date.");

    @Test
    public void addFloatingTask() {
        // add one task
        String taskDescription = "do homework for Melvin";
        String command = "add " + taskDescription;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription);
        assertTrue(isTaskShown(task));

        // add another task
        String taskDescription2 = "drink Koi after school";
        String command2 = "add " + taskDescription2;
        commandBox.runCommand(command2);
        Task task2 = new Task(taskDescription2);
        assertTrue(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }

    @Test
    public void addTaskWithDeadline() {
        // add task with deadline
        String taskDescription1 = "get v0.2 ready";
        LocalDateTime endDate1 = DateTimeUtil.toDate("15 Mar 2017, 12pm");
        String command1 = "add " + taskDescription1 + " enddate/" + endDate1;
        commandBox.runCommand(command1);
        Task task1 = new Task(taskDescription1, endDate1);
        assertTrue(isTaskShown(task1));

        // add task without description with deadline
        String taskDescription2 = "";
        LocalDateTime endDate2 = DateTimeUtil.toDate("15 Mar 2017, 12pm");
        String command2 = "add " + taskDescription2 + " enddate/" + endDate2;
        Task task2 = null;
        try {
            commandBox.runCommand(command2);
            task2 = new Task(taskDescription2, endDate2);
            fail("Should throw an IllegalArgumentException here since description is empty.");
        } catch (IllegalArgumentException illegalArgumentException) {
            assertTrue(illegalArgumentException.getMessage().equals(illegalArgumentException1.getMessage()));
        }
        assertTrue(isTaskShown(task1));
        assertFalse(isTaskShown(task2));

        // add another task with deadline
        String taskDescription3 = "get v0.3 ready";
        LocalDateTime endDate3 = DateTimeUtil.toDate("22 Mar 2017, 12pm");
        String command3 = "add " + taskDescription3 + " enddate/" + endDate3;
        commandBox.runCommand(command3);
        Task task3 = new Task(taskDescription3, endDate3);
        assertTrue(isTaskShown(task1));
        assertFalse(isTaskShown(task2));
        assertTrue(isTaskShown(task3));
    }

    @Test
    public void addEvent() {
        // add event
        String taskDescription1 = "attend CS2103T tutorial";
        LocalDateTime startDate1 = DateTimeUtil.toDate("15 Mar 2017, 12pm");
        LocalDateTime endDate1 = DateTimeUtil.toDate("15 Mar 2017, 1pm");
        String command1 = "add " + taskDescription1 + " startdate/" + startDate1 + " enddate/" + endDate1;
        commandBox.runCommand(command1);
        Task task1 = new Task(taskDescription1, startDate1, endDate1);
        assertTrue(isTaskShown(task1));

        // add event with start date after end date
        String taskDescription2 = "attend CS2103T tutorial";
        LocalDateTime startDate2 = DateTimeUtil.toDate("15 Mar 2017, 1pm");
        LocalDateTime endDate2 = DateTimeUtil.toDate("15 Mar 2017, 12pm");
        String command2 = "add " + taskDescription2 + " startdate/" + startDate2 + " enddate/" + endDate2;
        Task task2 = null;
        try {
            commandBox.runCommand(command2);
            task2 = new Task(taskDescription2, startDate2, endDate2);
            fail("Should throw an IllegalArgumentException here since start date is after end date.");
        } catch (IllegalArgumentException illegalArgumentException) {
            assertTrue(illegalArgumentException.getMessage().equals(illegalArgumentException2.getMessage()));
        }
        assertTrue(isTaskShown(task1));
        assertFalse(isTaskShown(task2));

        // add event without description
        String taskDescription3 = "";
        LocalDateTime startDate3 = DateTimeUtil.toDate("15 Mar 2017, 1pm");
        LocalDateTime endDate3 = DateTimeUtil.toDate("15 Mar 2017, 12pm");
        String command3 = "add " + taskDescription3 + " startdate/" + startDate3 + " enddate/" + endDate3;
        Task task3 = null;
        try {
            commandBox.runCommand(command3);
            task3 = new Task(taskDescription3, startDate3, endDate3);
            fail("Should throw an IllegalArgumentException here since description is empty.");
        } catch (IllegalArgumentException illegalArgumentException) {
            assertTrue(illegalArgumentException.getMessage().equals(illegalArgumentException1.getMessage()));
        }
        assertTrue(isTaskShown(task1));
        assertFalse(isTaskShown(task2));
        assertFalse(isTaskShown(task3));

        // add another event
        String taskDescription4 = "attend CS2101 tutorial";
        LocalDateTime startDate4 = DateTimeUtil.toDate("16 Mar 2017, 10am");
        LocalDateTime endDate4 = DateTimeUtil.toDate("16 Mar 2017, 12pm");
        String command4 = "add " + taskDescription4 + " startdate/" + startDate4 + " enddate/" + endDate4;
        Task task4 = new Task(taskDescription4, startDate4, endDate4);
        commandBox.runCommand(command4);
        assertTrue(isTaskShown(task1));
        assertFalse(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        assertTrue(isTaskShown(task4));
    }
}
