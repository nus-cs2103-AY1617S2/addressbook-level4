//@@author A0131125Y
package guitests;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;

import seedu.toluist.commons.core.Messages;
import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Gui tests for mark command
 */
public class MarkCommandTest extends ToLuistGuiTest {
    @Before
    public void setUp() {
        for (Task task : TodoList.getInstance().getTasks()) {
            task.setCompleted(false);
        }
        String switchViewtoAll = "switch a";
        commandBox.runCommand(switchViewtoAll);
    }

    @Test
    public void mark_invalidIndex() {
        String command = "mark";
        commandBox.runCommand(command);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_INDEX);
    }

    @Test
    public void markComplete_singleTask() {
        Task task1 = new TypicalTestTodoLists().getTypicalTasks()[0];
        String command1 = "mark complete 1";
        commandBox.runCommand(command1);
        assertTaskComplete(true, task1);

        Task task2 = new TypicalTestTodoLists().getTypicalTasks()[1];
        String command2 = "mark complete 2";
        commandBox.runCommand(command2);
        assertTaskComplete(true, task2);
    }

    @Test
    public void markComplete_multipleTasks() {
        String command = "mark complete 1-2";
        commandBox.runCommand(command);
        assertTaskComplete(true, new TypicalTestTodoLists().getTypicalTasks());
    }

    @Test
    public void markIncomplete_singleTask() {
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        String markCompleteCommand = "mark complete 1";
        commandBox.runCommand(markCompleteCommand);
        assertTaskComplete(true, task);

        String markIncompleteCommand = "mark incomplete 1";
        commandBox.runCommand(markIncompleteCommand);
        assertTaskComplete(false, task);
    }

    @Test
    public void markIncomplete_multipleTasks() {
        String markCompleteCommand = "mark complete 1 - ";
        commandBox.runCommand(markCompleteCommand);
        assertTaskComplete(true, new TypicalTestTodoLists().getTypicalTasks());

        String markIncompleteCommand = "mark incomplete  - 2";
        commandBox.runCommand(markIncompleteCommand);
        assertTaskComplete(false, new TypicalTestTodoLists().getTypicalTasks());
    }

    /**
     * Check that all the tasks are completed or incomplete
     * @param isCompleted whether the tasks should be check for being completed or incomplete
     * @param tasks varargs of tasks
     */
    private void assertTaskComplete(boolean isCompleted, Task... tasks) {
        for (Task task : tasks) {
            Task taskOnUi = getTasksShown().stream()
                    .filter(shownTask -> isEqualAsideFromCompleteDateTime(task, shownTask))
                    .findFirst()
                    .get();
            assertEquals(taskOnUi.isCompleted(), isCompleted);
        }
    }

    /**
     * Check if two tasks have equal properties, not counting completionDateTime
     */
    private boolean isEqualAsideFromCompleteDateTime(Task task1, Task task2) {
        return task1.getDescription().equals(task2.getDescription())
                && task1.getTaskPriority().equals(task2.getTaskPriority())
                && task1.getAllTags().equals(task2.getAllTags())
                && Objects.equals(task1.getStartDateTime(), task2.getStartDateTime())
                && Objects.equals(task1.getEndDateTime(), task2.getEndDateTime());
    }

    //@@author A0127545A
    @Test
    public void markMultipleRecurringTaskAsCompletedMultipleTimes() {
        // add recurring floating task, will recur until end date is due.
        String taskDescription = "do homework for Melvin";
        String recurFrequencyString = "daily";
        LocalDateTime recurUntilEndDate = DateTimeUtil.parseDateString("15 May 2018, 12pm");
        String command = "add " + taskDescription + " /repeat " + recurFrequencyString
                       + " /repeatuntil " + recurUntilEndDate;
        commandBox.runCommand(command);
        Task task = new Task(taskDescription);
        task.setRecurring(recurUntilEndDate, recurFrequencyString);
        assertTrue(isTaskShown(task));

        // add task with deadline, can recur once and then get deleted
        String taskDescription2 = "get v0.4 ready";
        String recurFrequencyString2 = "monthly";
        LocalDateTime endDate2 = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime recurUntilEndDate2 = DateTimeUtil.parseDateString("30 Apr 2017, 12pm");
        String command2 = "add " + taskDescription2 + " /by " + endDate2 + " /repeat " + recurFrequencyString2
                + " /repeatuntil " + recurUntilEndDate2;
        commandBox.runCommand(command2);
        Task task2 = new Task(taskDescription2, endDate2);
        task2.setRecurring(recurUntilEndDate2, recurFrequencyString2);
        assertTrue(isTaskShown(task2));

        // add event, can recur 0 times and then get deleted
        String taskDescription3 = "attend CS2103T tutorial";
        String recurFrequencyString3 = "weekly";
        LocalDateTime startDate3 = DateTimeUtil.parseDateString("24 Mar 2017, 12pm");
        LocalDateTime endDate3 = DateTimeUtil.parseDateString("24 Mar 2017, 1pm");
        LocalDateTime recurUntilEndDate3 = DateTimeUtil.parseDateString("28 Mar 2017, 1pm");
        String command3 = "add " + taskDescription3 + " /from " + startDate3 + " /to " + endDate3
                        + " /repeat " + recurFrequencyString3 + " /repeatuntil " + recurUntilEndDate3;
        commandBox.runCommand(command3);
        Task task3 = new Task(taskDescription3, startDate3, endDate3);
        task3.setRecurring(recurUntilEndDate3, recurFrequencyString3);
        assertTrue(isTaskShown(task3));

        String deleteAllCommand = "mark complete -";
        commandBox.runCommand(deleteAllCommand);
        assertTrue(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        task2.updateToNextRecurringTask();
        task3.updateToNextRecurringTask();
        assertTrue(isTaskShown(task2));
        assertFalse(isTaskShown(task3)); // recurring task reached end date, so it got deleted

        commandBox.runCommand(deleteAllCommand);
        assertTrue(isTaskShown(task));
        assertFalse(isTaskShown(task2));
        assertFalse(isTaskShown(task3));
        task2.updateToNextRecurringTask();
        task3.updateToNextRecurringTask();
        assertFalse(isTaskShown(task2)); // recurring task reached end date, so it got deleted for real
        assertFalse(isTaskShown(task3));
    }

    @Test
    public void markRecurringEventAsCompleted_endingThirtyFirstOfTheMonth() {
        // add event
        String taskDescription = "attend CS2103T tutorial";
        String recurFrequencyString = "monthly";
        LocalDateTime startDate = DateTimeUtil.parseDateString("15 Mar 2017, 12pm");
        LocalDateTime endDate = DateTimeUtil.parseDateString("31 Mar 2017, 1pm");
        String command = "add " + taskDescription + " /from " + startDate + " /to " + endDate
                        + " /repeat " + recurFrequencyString;
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
