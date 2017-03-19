package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;

/**
 * Gui tests for add task command
 */
public class AddTaskCommandTest extends ToLuistGuiTest {

    private static IllegalArgumentException illegalArgumentException1 =
            new IllegalArgumentException("Description must not be empty.");
    private static IllegalArgumentException illegalArgumentException2 =
            new IllegalArgumentException("Start date must be before end date.");
    private Tag tag1 = new Tag("tag1");
    private Tag tag2 = new Tag("tag2");
    private Tag tag3 = new Tag("tag3");

    @Test
    public void addFloatingTask() {
        // add one task
        String taskDescription = "do homework for Melvin";
        String command = "add " + taskDescription;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription);
        assertTrue(isTaskShown(task));

        // add another task, with tags
        String taskDescription2 = "drink Koi after school";
        String command2 = "add " + taskDescription2 + " tags/tag1 tag2 tag3";
        commandBox.runCommand(command2);
        Task task2 = new Task(taskDescription2, null, null);
        task2.replaceTags(new ArrayList<>(Arrays.asList(tag1, tag2, tag3)));
        assertTrue(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }

    @Test
    public void addTaskWithDeadline() {
        // add task with deadline
        String taskDescription1 = "get v0.2 ready";
        LocalDateTime endDate1 = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        String command1 = "add " + taskDescription1 + " enddate/" + endDate1;
        commandBox.runCommand(command1);
        Task task1 = new Task(taskDescription1, endDate1);
        assertTrue(isTaskShown(task1));

        // add task without description with deadline
        String taskDescription2 = "";
        LocalDateTime endDate2 = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
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

        // add another task with deadline and tags
        String taskDescription3 = "get v0.3 ready";
        LocalDateTime endDate3 = DateTimeUtil.parseDateString("22 Mar 2017, 12pm");
        String command3 = "add " + taskDescription3 + " enddate/" + endDate3 + " tags/ tag1 tag2 ";
        commandBox.runCommand(command3);
        Task task3 = new Task(taskDescription3, null, endDate3);
        task3.replaceTags(new ArrayList<>(Arrays.asList(tag1, tag2)));
        assertTrue(isTaskShown(task1));
        assertFalse(isTaskShown(task2));
        assertTrue(isTaskShown(task3));
    }

    @Test
    public void addEvent() {
        // add event
        String taskDescription1 = "attend CS2103T tutorial";
        LocalDateTime startDate1 = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime endDate1 = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        String command1 = "add " + taskDescription1 + " startdate/" + startDate1 + " enddate/" + endDate1;
        commandBox.runCommand(command1);
        Task task1 = new Task(taskDescription1, startDate1, endDate1);
        assertTrue(isTaskShown(task1));

        // add event with start date after end date
        String taskDescription2 = "attend CS2103T tutorial";
        LocalDateTime startDate2 = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        LocalDateTime endDate2 = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
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
        LocalDateTime startDate3 = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        LocalDateTime endDate3 = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
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

        // add another event with tags
        String taskDescription4 = "attend CS2101 tutorial";
        LocalDateTime startDate4 = DateTimeUtil.parseDateString("16 Mar 2017, 10am");
        LocalDateTime endDate4 = DateTimeUtil.parseDateString("16 Mar 2017, 12pm");
        String command4 = "add " + taskDescription4 + " tags/tag3" +
                 " startdate/" + startDate4 + " enddate/" + endDate4;
        commandBox.runCommand(command4);
        Task task4 = new Task(taskDescription4, startDate4, endDate4);
        task4.replaceTags(new ArrayList<>(Arrays.asList(tag3)));
        assertTrue(isTaskShown(task1));
        assertFalse(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        assertTrue(isTaskShown(task4));
    }
}
