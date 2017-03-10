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

        // add task with deadline
        String taskDescription2 = "get v0.2 ready";
        LocalDateTime endDate2 = DateTimeUtil.toDate("15 Mar 2017, 12pm");
        String command2 = "add " + taskDescription2 + " enddate/" + endDate2;
        commandBox.runCommand(command2);
        Task task2 = new Task(taskDescription2, endDate2);

        // add event
        String taskDescription3 = "attend CS2103T tutorial";
        LocalDateTime startDate3 = DateTimeUtil.toDate("15 Mar 2017, 12pm");
        LocalDateTime endDate3 = DateTimeUtil.toDate("15 Mar 2017, 1pm");
        String command3 = "add " + taskDescription3 + " startdate/" + startDate3 + " enddate/" + endDate3;
        commandBox.runCommand(command3);
        Task task3 = new Task(taskDescription3, startDate3, endDate3);

        assertTrue(isTaskShown(task));
        assertTrue(isTaskShown(task2));
        assertTrue(isTaskShown(task3));

        commandBox.runCommand("delete 3");
        assertTrue(isTaskShown(task));
        assertTrue(isTaskShown(task2));
        assertFalse(isTaskShown(task3));

        commandBox.runCommand("delete 1");
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
        assertFalse(isTaskShown(task3));

        commandBox.runCommand("delete 1");
        assertFalse(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
    }
}
