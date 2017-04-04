//@@author A0127545A
package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
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
    private static final String ADD = "add ";
    private static final String UPDATE = "update ";
    private static final String FROM = " /from ";
    private static final String TO = " /to ";
    private static final String BY = " /by ";
    private static final String FLOATING = " /floating ";
    private static final String TAGS = " /tags ";
    private static final String PRIORITY = " /priority ";
    private static final String REPEAT = " /repeat ";
    private static final String REPEAT_UNTIL = " /repeatuntil ";
    private static final String STOP_REPEATING = " /stoprepeating ";

    private static Tag tag1 = new Tag("tag1");
    private static Tag tag2 = new Tag("tag2");
    private static Tag tag3 = new Tag("tag3");

    @Before
    public void setUp() {
        String switchToDefaultTab = "switch i";
        commandBox.runCommand(switchToDefaultTab);
    }

    @Test
    public void testInvalidIndexInput() {
        String command = UPDATE + " 0 " + "description";
        commandBox.runCommand(command);
        assertResultMessage("No valid index found.");

        command = UPDATE + " 3 " + "description";
        commandBox.runCommand(command);
        assertResultMessage("No valid index found.");

        command = UPDATE + " potato " + "description";
        commandBox.runCommand(command);
        assertResultMessage("No valid index found.");
    }

    @Test
    public void updateFloatingTask() {
        // update description
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        String newDescription = "do homework for Melvin";
        String command = UPDATE + " 1 " + newDescription;
        task.setDescription(newDescription);
        commandBox.runCommand(command);
        assertFalse(isTaskShown(new TypicalTestTodoLists().getTypicalTasks()[0]));
        assertTrue(isTaskShown(task));

        // update tags
        command = UPDATE + "2" + TAGS + "tag2";
        commandBox.runCommand(command);
        Task task2 = new Task(newDescription);
        task2.replaceTags(new ArrayList<>(Arrays.asList(tag2)));
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));

        // update tags with new tags
        command = UPDATE + "2" + TAGS + "tag1 tag3" + PRIORITY + "high";
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
        String command = ADD + taskDescription + BY + endDate;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, endDate);
        assertTrue(isTaskShown(task));

        // update task deadline
        LocalDateTime newEndDate = DateTimeUtil.parseDateString("22 Mar 2017, 11am");
        command = UPDATE + eventIndex + BY + newEndDate;
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription, newEndDate);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));

        // update task description
        String newTaskDescription = "complete v0.2";
        command = UPDATE + eventIndex + " " + newTaskDescription + PRIORITY + "high";
        commandBox.runCommand(command);
        Task task3 = new Task(newTaskDescription, newEndDate);
        task3.setTaskPriority(TaskPriority.HIGH);
        assertFalse(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertTrue(isTaskShown(task3));

        // update all parameters for task with deadline and tags
        String newerTaskDescription = "get v0.2 ready";
        LocalDateTime newerEndDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        command = UPDATE + eventIndex + " " + newerTaskDescription + BY + newerEndDate +
                PRIORITY + "low" + TAGS + "tag1 tag3";
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
        String command = ADD + taskDescription + FROM + startDate + TO + endDate;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, startDate, endDate);
        assertTrue(isTaskShown(task));

        // update event start date and end date
        LocalDateTime newStartDate = DateTimeUtil.parseDateString("22 Mar 2017, 12pm");
        LocalDateTime newEndDate = DateTimeUtil.parseDateString("22 Mar 2017, 1pm");
        command = UPDATE + eventIndex + FROM + newStartDate + TO + newEndDate + PRIORITY + "high";
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription, newStartDate, newEndDate);
        task2.setTaskPriority(TaskPriority.HIGH);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));

        // update event description
        String newTaskDescription = "participate in CS2103 tutorial";
        command = UPDATE + eventIndex + " " + newTaskDescription;
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
        command = UPDATE + eventIndex + " " + newerTaskDescription +
                  PRIORITY + "low" + TO + newerEndDate + TAGS + "tag1" + FROM + newerStartDate;
        commandBox.runCommand(command);
        Task task4 = new Task(newerTaskDescription, newerStartDate, newerEndDate);
        task4.replaceTags(new ArrayList<>(Arrays.asList(tag1)));
        assertFalse(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        assertTrue(isTaskShown(task4));
    }

    @Test
    public void updateTaskToADuplicatedTask_shouldNotBeUpdated() {
        String taskDescription = "yet another task";
        String command = ADD + taskDescription;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription);

        taskDescription = "task";
        command = ADD + taskDescription;
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription);
        assertTasksShown(true, task, task2);

        command = UPDATE + " 3 " + taskDescription;
        commandBox.runCommand(command);
        assertTasksShown(true, task, task2);
        assertResultMessage("Task provided already exist in the list.");

        command = UPDATE + " 4 " + taskDescription;
        commandBox.runCommand(command);
        assertTasksShown(true, task, task2);
        assertResultMessage("Task provided already exist in the list.");
    }

    @Test
    public void updateMultipleTypeTask_shouldNotBeUpdated() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        LocalDateTime startDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime endDate = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        LocalDateTime endDate2 = DateTimeUtil.parseDateString("15 Mar 2017, 5pm");
        String command = ADD + taskDescription + FROM + startDate + TO + endDate;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, startDate, endDate);
        assertTrue(isTaskShown(task));

        // Update to both event, and floating task
        command = UPDATE + eventIndex + FLOATING + FROM + startDate + TO + endDate;
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));

        // Update to both event, and task with deadline
        command = UPDATE + eventIndex + FROM + startDate + TO + endDate + BY + endDate2;
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));

        // Update to both floating task, and task with deadline
        command = UPDATE + eventIndex + FLOATING + BY + endDate2;
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));

        // Update to all floating task, task with deadline, event
        command = UPDATE + eventIndex + BY + endDate2 + FROM + startDate + TO + endDate + FLOATING;
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));
    }

    @Test
    public void updateFloatingTaskToEvent() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        LocalDateTime startDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime endDate = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        String command = ADD + taskDescription;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription);
        assertTrue(isTaskShown(task));

        // Event
        command = UPDATE + eventIndex + FROM + startDate + TO + endDate;
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
        String command = ADD + taskDescription;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription);
        assertTrue(isTaskShown(task));

        // Task with deadline
        command = UPDATE + eventIndex + BY + endDate2;
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
        String command = ADD + taskDescription + BY + endDate2;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, endDate2);
        assertTrue(isTaskShown(task));

        // Floating task
        command = UPDATE + eventIndex + FLOATING;
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
        String command = ADD + taskDescription + BY + endDate2;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, endDate2);
        assertTrue(isTaskShown(task));

        // Task with deadline to event
        command = UPDATE + eventIndex + FROM + startDate + TO + endDate;
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
        String command = ADD + taskDescription + FROM + startDate + TO + endDate;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, startDate, endDate);
        assertTrue(isTaskShown(task));

        // Event to floating task
        command = UPDATE + eventIndex + FLOATING;
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
        String command = ADD + taskDescription + FROM + startDate + TO + endDate;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, startDate, endDate);
        assertTrue(isTaskShown(task));

        // Event to task with deadline
        command = UPDATE + eventIndex + BY + endDate2;
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
        String updateCommand = UPDATE + " 1 " + newDescription;
        task.setDescription(newDescription);
        commandBox.runCommand(updateCommand);
        assertFalse(isTaskShown(new TypicalTestTodoLists().getTypicalTasks()[1]));
        assertTrue(isTaskShown(task));
    }

    @Test
    public void updateTaskWithInvalidPriorityLevel_shouldNotBeUpdated() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        String command = ADD + taskDescription;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription);
        assertTrue(isTaskShown(task));

        command = UPDATE + eventIndex + PRIORITY + "high low";
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));

        command = UPDATE + eventIndex + PRIORITY;
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));
    }

    @Test
    public void updateRecurringEventToRecurringFloatingTask() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        String recurFrequencyString = "weekly";
        LocalDateTime from = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime to = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        String command = ADD + taskDescription + FROM + from + TO + to + REPEAT + recurFrequencyString;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, from, to);
        task.setRecurring(recurFrequencyString);
        assertTrue(isTaskShown(task));

        command = UPDATE + eventIndex + FLOATING;
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription);
        task2.setRecurring(recurFrequencyString);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }

    @Test
    public void updateNonRecurringFloatingTaskToRecurringEvent() {
        int eventIndex = 1;

        String taskDescription = "attend CS2103T tutorial";
        String command = ADD + taskDescription;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription);
        assertTrue(isTaskShown(task));

        LocalDateTime from = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime to = DateTimeUtil.parseDateString("15 Mar 2017, 1pm");
        String recurFrequencyString = "weekly";
        command = UPDATE + eventIndex + FROM + from + TO + to + REPEAT + recurFrequencyString;
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
        String command = ADD + taskDescription + FROM + from + TO + to;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, from, to);
        assertTrue(isTaskShown(task));

        String recurFrequencyString = "monthly";
        LocalDateTime recurEndDate = DateTimeUtil.parseDateString("15 Mar 2018, 1pm");
        command = UPDATE + eventIndex + REPEAT + recurFrequencyString + REPEAT_UNTIL + recurEndDate;
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
        String command = ADD + taskDescription + FROM + from + TO + to;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, from, to);
        assertTrue(isTaskShown(task));

        String recurFrequencyString = "yearly";
        LocalDateTime recurEndDate = DateTimeUtil.parseDateString("15 Mar 2020, 1pm");
        command = UPDATE + eventIndex + BY + to
                + REPEAT + recurFrequencyString + REPEAT_UNTIL + recurEndDate;
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
        String command = ADD + taskDescription + BY + to + REPEAT + recurFrequencyString;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, to);
        task.setRecurring(recurFrequencyString);
        assertTrue(isTaskShown(task));

        command = UPDATE + eventIndex + STOP_REPEATING;
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
        String command = ADD + taskDescription + FROM + from + TO + to + REPEAT + recurFrequencyString;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, from, to);
        task.setRecurring(recurFrequencyString);
        assertTrue(isTaskShown(task));

        command = UPDATE + eventIndex + BY + to + STOP_REPEATING;
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
        String command = ADD + taskDescription + FROM + from + TO + to + REPEAT + recurFrequencyString;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription, from, to);
        task.setRecurring(recurFrequencyString);
        assertTrue(isTaskShown(task));

        command = UPDATE + eventIndex + FLOATING + STOP_REPEATING;
        commandBox.runCommand(command);
        Task task2 = new Task(taskDescription);
        assertFalse(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }

    @Test
    public void updateRecurringTask_invalidParams_shouldNotUpdate() {
        int eventIndex = 1;

        String taskDescription = "shower";
        String recurFrequencyString = "daily";
        String command = ADD + taskDescription + REPEAT + recurFrequencyString;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription);
        task.setRecurring(recurFrequencyString);
        assertTrue(isTaskShown(task));

        command = UPDATE + eventIndex + STOP_REPEATING + REPEAT + recurFrequencyString;
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));
        assertResultMessage("Input contains both recurring and stop recurring arguments at the same time.");

        LocalDateTime recurEndDate = DateTimeUtil.parseDateString("15 Mar 2020, 1pm");
        command = UPDATE + eventIndex + STOP_REPEATING + REPEAT_UNTIL + recurEndDate;
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));
        assertResultMessage("Input contains both recurring and stop recurring arguments at the same time.");
    }

    @Test
    public void updateTaskWithMultipleParameters_testResultMessage() {
        int index = 1;

        LocalDateTime from = DateTimeUtil.parseDateString("5 May 2017, 9pm");
        LocalDateTime to = DateTimeUtil.parseDateString("5 May 2017, 10pm");
        LocalDateTime recurUntil = DateTimeUtil.parseDateString("5 May 2019, 10pm");
        String command = UPDATE + index + " write code" + FROM + from + TO + to + PRIORITY + "high"
                + TAGS + "hello world" + REPEAT + "weekly" + REPEAT_UNTIL + recurUntil;
        commandBox.runCommand(command);
        assertResultMessage("Updated task at index 1:\n" +
            "- Task type: \"TASK\" to \"EVENT\"\n" +
            "- Description: \"clean the house while Lewis is gone\" to \"write code\"\n" +
            "- Start date: \"\" to \"Fri, 05 May 2017 09:00 PM\"\n" +
            "- End date: \"\" to \"Fri, 05 May 2017 10:00 PM\"\n" +
            "- Priority: \"LOW\" to \"HIGH\"\n" +
            "- Repeat: \"\" to \"WEEKLY\"\n" +
            "- Repeat until: \"\" to \"Sun, 05 May 2019 10:00 PM\"\n" +
            "- Tags: \"lewis work\" to \"hello world\"");

        command = UPDATE + index + FLOATING + TAGS + STOP_REPEATING;
        commandBox.runCommand(command);
        assertResultMessage("Updated task at index 1:\n" +
                "- Task type: \"EVENT\" to \"TASK\"\n" +
                "- Start date: \"Fri, 05 May 2017 09:00 PM\" to \"\"\n" +
                "- End date: \"Fri, 05 May 2017 10:00 PM\" to \"\"\n" +
                "- Repeat: \"WEEKLY\" to \"\"\n" +
                "- Repeat until: \"Sun, 05 May 2019 10:00 PM\" to \"\"");
    }


    @Test
    public void updateTaskAfterSwitching() {
        String clearCommand = "clear";
        commandBox.runCommand(clearCommand);
        String switchCommand = "switch t";
        commandBox.runCommand(switchCommand);
        String addCommand = ADD + "sth sth" + BY + "today";
        commandBox.runCommand(addCommand);
        int index = 1;
        String taskDescription = "get a life";
        String updateCommand = UPDATE + index + " " + taskDescription;
        commandBox.runCommand(updateCommand);

        assertEquals(tabBar.getHighlightedTabText(), "TODAY (1/1)");
    }
}
