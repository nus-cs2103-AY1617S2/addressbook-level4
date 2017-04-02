package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezdo.logic.commands.DoneCommand.MESSAGE_DONE_TASK_SUCCESS;
import static seedu.ezdo.logic.commands.DoneCommand.MESSAGE_UNDONE_TASK_SUCCESS;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.logic.commands.DoneCommand;
import seedu.ezdo.logic.parser.DateParser;
import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.Recur;
import seedu.ezdo.model.todo.StartDate;
import seedu.ezdo.testutil.TaskBuilder;
import seedu.ezdo.testutil.TestTask;
import seedu.ezdo.testutil.TestUtil;

//@@author A0141010L
public class DoneCommandTest extends EzDoGuiTest {

    @Test
    public void done_success() {

        // marks a recurring task in the list as done
        TestTask[] currentList = td.getTypicalTasks();
        TestTask[] doneList = td.getTypicalDoneTasks();
        int targetIndex = currentList.length;
        TestTask doneTask_1 = currentList[targetIndex - 1];
        assertDoneSuccess(false, targetIndex, currentList, doneList);
        doneList = TestUtil.addTasksToList(doneList, doneTask_1);

        // reset td.george (dates taken from typical test case) and set recurrence to nil
        commandBox.runCommand("edit " + targetIndex + " s/02/07/2012 04:55 " + "d/17/07/2015 22:22 " + "f/");
        TestTask editedTask = new TaskBuilder(currentList[targetIndex - 1]).build();

        try {
            editedTask.setRecur(new Recur(""));
        } catch (IllegalValueException e) {

        }

        currentList[targetIndex - 1] = editedTask;

        //marks that non recurring task in a list as done
        TestTask doneTask_2 = currentList[targetIndex - 1];
        assertDoneSuccess(false, targetIndex, currentList, doneList);
        doneList = TestUtil.addTasksToList(doneList, doneTask_2);

        // invalid index
        commandBox.runCommand("done " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid.");

        // invalid command
        commandBox.runCommand("done a");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));

        // invalid command
        commandBox.runCommand("dones 1");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        // view done tasks
        commandBox.runCommand("done");
        assertTrue(taskListPanel.isListMatching(doneList));

        // invalid input when viewing done task
        commandBox.runCommand("done " + doneList.length);
        assertResultMessage(Messages.MESSAGE_WRONG_LIST);

    }

    private void assertDoneSuccess(boolean usesShortCommand, int targetIndexOneIndexed, TestTask[] currentList,
            TestTask[] doneList) {

        TestTask taskToDone = currentList[targetIndexOneIndexed - 1]; // -1 as
                                                                      // array
                                                                      // uses
                                                                      // zero
                                                                      // indexing
        ArrayList<TestTask> tasksToDone = new ArrayList<TestTask>();
        tasksToDone.add(taskToDone); // old date
        currentList = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);
        doneList = TestUtil.addTasksToList(doneList, taskToDone);
        if (usesShortCommand) {
            commandBox.runCommand("d " + targetIndexOneIndexed);
        } else {
            commandBox.runCommand("done " + targetIndexOneIndexed);
        }

        if (!taskToDone.getRecur().isRecur()) {

            // confirm the task list no longer has the done task
            assertTrue(taskListPanel.isListMatching(currentList));

            // confirm the result message is correct
            assertResultMessage(String.format(MESSAGE_DONE_TASK_SUCCESS, tasksToDone));

            // confirm the new done list contains the right data
            commandBox.runCommand("done");
            TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToDone.getName().fullName);
            assertMatching(taskToDone, addedCard);
            assertTrue(taskListPanel.isListMatching(doneList));

            // confirm the undone list does not contain the task just marked as
            // done
            commandBox.runCommand("list");
            assertTrue(taskListPanel.isListMatching(currentList));

        } else {

            TestTask recTask = updateRecTask(new TestTask(taskToDone));

            currentList = TestUtil.addTasksToList(currentList, recTask);
            // confirm the task list no longer has the done task
            assertTrue(taskListPanel.isListMatching(currentList));

            // confirm the result message is correct
            assertResultMessage(String.format(MESSAGE_DONE_TASK_SUCCESS, tasksToDone));

            // confirm the new done list contains the right data
            commandBox.runCommand("done");
            TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToDone.getName().fullName);
            assertMatching(tasksToDone.get(0), addedCard);
            assertTrue(taskListPanel.isListMatching(doneList));

            // confirm the undone list does not contain the task just marked as
            // done
            commandBox.runCommand("list");
            assertTrue(taskListPanel.isListMatching(currentList));
        }
    }

    private String updateDate(int type, String originalDate) {
        try {
            int increment = 1;
            Calendar c = Calendar.getInstance();
            c.setTime(DateParser.USER_OUTPUT_DATE_FORMAT.parse(originalDate));
            c.add(type, increment);
            return DateParser.USER_OUTPUT_DATE_FORMAT.format(c.getTime());
        } catch (ParseException pe) {
            // Do nothing as the date is optional
            // and cannot be parsed as Date object
        }
        return originalDate;
    }

    private TestTask updateRecTask(TestTask taskToDone) {
        String recurIntervalInString = taskToDone.getRecur().toString().trim();
        int recurringInterval = Recur.RECUR_INTERVALS.get(recurIntervalInString);

        String startDateInString = taskToDone.getStartDate().value;
        String dueDateInString = taskToDone.getDueDate().value;

        String newStartDate = updateDate(recurringInterval, startDateInString);
        String newDueDate = updateDate(recurringInterval, dueDateInString);

        try {
            taskToDone.setStartDate(new StartDate(newStartDate));
            taskToDone.setDueDate(new DueDate(newDueDate));
            taskToDone.setRecur(new Recur(""));
        } catch (IllegalValueException e) {

        }
        return taskToDone;
    }

    //@@author A0139248X
    @Test
    public void undone_success() {
        TestTask[] currentList = td.getTypicalDoneTasks();
        TestTask[] doneList = td.getTypicalTasks();
        //undone a task
        int targetIndex = 1;
        assertUndoneSuccess(false, targetIndex, currentList, doneList);
    }

    @Test
    public void undone_empty() {
        //no tasks in done list to undone
        commandBox.runCommand("done");
        commandBox.runCommand("done 1");
        assertResultMessage("The task index provided is invalid.");
    }

    private void assertUndoneSuccess(boolean usesShortCommand, int targetIndexOneIndexed, final TestTask[] currentList,
            final TestTask[] doneList) {

        StringBuilder sb = new StringBuilder(); // workaround to done all tasks so we can test
        sb.append("done ");
        for (int i = 0; i < doneList.length; i++) {
            sb.append((i + 1) + " ");
        }
        commandBox.runCommand(sb.toString());

        TestTask taskToUndone = doneList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        ArrayList<TestTask> tasksToUndone = new ArrayList<TestTask>();
        tasksToUndone.add(taskToUndone);
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(doneList, targetIndexOneIndexed);
        TestTask[] expectedUndone = TestUtil.addTasksToList(currentList, taskToUndone);

        commandBox.runCommand("done"); // to get to done list view

        if (usesShortCommand) {
            commandBox.runCommand("d " + targetIndexOneIndexed);
        } else {
            commandBox.runCommand("done " + targetIndexOneIndexed);
        }

        //confirm the done list now contains all done tasks excluding the one just marked as undone
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_UNDONE_TASK_SUCCESS, tasksToUndone));

        //confirm the new current list contains the right data
        commandBox.runCommand("list");
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToUndone.getName().fullName);
        assertMatching(taskToUndone, addedCard);
        assertTrue(taskListPanel.isListMatching(expectedUndone));

        //confirm the done list does not contain the task just marked as undone
        commandBox.runCommand("done");
        assertTrue(taskListPanel.isListMatching(expectedRemainder));
    }
}
