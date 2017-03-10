package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.model.Task;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Gui tests for delete task command
 */
public class DeleteTaskCommandTest extends ToLuistGuiTest {
    @Test
    public void deleteTask() {
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        String command = "delete 1";
        commandBox.runCommand(command);
        assertFalse(isTaskShown(task));
    }

    @Test
    public void deleteManyTasksIndivudally() {
        // Start with empty list
        commandBox.runCommand("delete 2");
        commandBox.runCommand("delete 1");

        // add one task
        String taskDescription = "do homework for Melvin";
        String command = "add " + taskDescription;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription);

        // add another task
        String taskDescription2 = "drink Koi after school";
        String command2 = "add " + taskDescription2;
        commandBox.runCommand(command2);
        Task task2 = new Task(taskDescription2);

        // add task with deadline
        String taskDescription3 = "get v0.2 ready";
        LocalDateTime endDate3 = DateTimeUtil.toDate("15 Mar 2017, 12pm");
        String command3 = "add " + taskDescription3 + " enddate/" + endDate3;
        commandBox.runCommand(command3);
        Task task3 = new Task(taskDescription3, endDate3);

        // add another task with deadline
        String taskDescription4 = "get v0.3 ready";
        LocalDateTime endDate4 = DateTimeUtil.toDate("22 Mar 2017, 12pm");
        String command4 = "add " + taskDescription4 + " enddate/" + endDate4;
        commandBox.runCommand(command4);
        Task task4 = new Task(taskDescription4, endDate4);

        // add event
        String taskDescription5 = "attend CS2103T tutorial";
        LocalDateTime startDate5 = DateTimeUtil.toDate("15 Mar 2017, 12pm");
        LocalDateTime endDate5 = DateTimeUtil.toDate("15 Mar 2017, 1pm");
        String command5 = "add " + taskDescription5 + " startdate/" + startDate5 + " enddate/" + endDate5;
        commandBox.runCommand(command5);
        Task task5 = new Task(taskDescription5, startDate5, endDate5);

        // add another event
        String taskDescription6 = "attend CS2101 tutorial";
        LocalDateTime startDate6 = DateTimeUtil.toDate("16 Mar 2017, 10am");
        LocalDateTime endDate6 = DateTimeUtil.toDate("16 Mar 2017, 12pm");
        String command6 = "add " + taskDescription6 + " startdate/" + startDate6 + " enddate/" + endDate6;
        Task task6 = new Task(taskDescription6, startDate6, endDate6);
        commandBox.runCommand(command6);

        assertTrue(isTaskShown(task));
        assertTrue(isTaskShown(task2));
        assertTrue(isTaskShown(task3));
        assertTrue(isTaskShown(task4));
        assertTrue(isTaskShown(task5));
        assertTrue(isTaskShown(task6));

        commandBox.runCommand("delete 3");
        assertTrue(isTaskShown(task));
        assertTrue(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        assertTrue(isTaskShown(task4));
        assertTrue(isTaskShown(task5));
        assertTrue(isTaskShown(task6));

        commandBox.runCommand("delete 3");
        assertTrue(isTaskShown(task));
        assertTrue(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        assertFalse(isTaskShown(task4));
        assertTrue(isTaskShown(task5));
        assertTrue(isTaskShown(task6));

        commandBox.runCommand("delete 1");
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        assertFalse(isTaskShown(task4));
        assertTrue(isTaskShown(task5));
        assertTrue(isTaskShown(task6));

        commandBox.runCommand("delete 2");
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        assertFalse(isTaskShown(task4));
        assertFalse(isTaskShown(task5));
        assertTrue(isTaskShown(task6));

        commandBox.runCommand("delete 2");
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        assertFalse(isTaskShown(task4));
        assertFalse(isTaskShown(task5));
        assertFalse(isTaskShown(task6));

        commandBox.runCommand("delete 1");
        assertFalse(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        assertFalse(isTaskShown(task4));
        assertFalse(isTaskShown(task5));
        assertFalse(isTaskShown(task6));
    }
}
