package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import typetask.commons.core.Messages;
import typetask.commons.exceptions.IllegalValueException;
import typetask.logic.commands.AddCommand;
import typetask.model.task.Name;
import typetask.testutil.TaskBuilder;
import typetask.testutil.TestTask;
import typetask.testutil.TestUtil;
//@@author A0139926R
public class AddCommandTest extends TypeTaskGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);


        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void addInvalidEvent_fail() {
        commandBox.runCommand("a invalidEvent from: today");
        assertResultMessage(String.format
                (Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
    @Test
    public void addInvalidMultiplePrefix_fail() {
        commandBox.runCommand("+ invalidEvent by: today @tmr");
        assertResultMessage(String.format
                (Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
    @Test
    public void addTask_success() throws IllegalValueException {
        commandBox.runCommand("add success p/Low");
        TestTask expectedResult = new TaskBuilder().withName("success")
                .withDate("").withEndDate("").withCompleted(false).withPriority("Low").build();
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, expectedResult));
    }
    @Test
    public void addTaskWithInvalidName_fail() {
        commandBox.runCommand("add ^_^");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);
    }
    @Test
    public void addTaskWithEndDateBeforeStartDate_fail() {
        commandBox.runCommand("add failEvent from: today to: yesterday");
        assertResultMessage(Messages.MESSAGE_INVALID_START_AND_END_DATE);
    }
    @Test
    public void addTaskWithInvalidStartDate_fail() {
        commandBox.runCommand("add failEvent from: gg to: yesterday");
        assertResultMessage(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_START_DATE);
    }
    @Test
    public void addTaskWithInvalidEndDate_fail() {
        commandBox.runCommand("add failEvent from: today to: gg");
        assertResultMessage(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_END_DATE);
    }
    @Test
    public void addTaskWithInvalidTime_fail() {
        commandBox.runCommand("add failEvent @lol");
        assertResultMessage(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_DATE);
    }
    @Test
    public void addTaskWithInvalidDate_fail() {
        commandBox.runCommand("add invalidDate by: lol");
        assertResultMessage(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_DATE);
    }
    @Test
    public void addDeadlineTaskWithDateNoTime_success() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("add deadline by: 10 oct 1993 p/Low");
        TestTask deadlineTask = new TaskBuilder().withName("deadline")
                .withDate("").withEndDate("Sun Oct 10 1993 23:59:59")
                .withCompleted(false).withPriority("Low").build();
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, deadlineTask);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    @Test
    public void addDeadlineTaskwithDateWithTime_success() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("add deadline @ 10 oct 1993 4pm p/Low");
        TestTask deadlineTask = new TaskBuilder().withName("deadline")
                .withDate("").withEndDate("Sun Oct 10 1993 16:00:00")
                .withCompleted(false).withPriority("Low").build();
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, deadlineTask);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    @Test
    public void addEventTask_success() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("add event from: 10 oct 1993 1pm to: 10 oct 1993 4pm p/Low");
        TestTask deadlineTask = new TaskBuilder().withName("event")
                .withDate("Sun Oct 10 1993 13:00:00").withEndDate("Sun Oct 10 1993 16:00:00")
                .withCompleted(false).withPriority("Low").build();
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, deadlineTask);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
