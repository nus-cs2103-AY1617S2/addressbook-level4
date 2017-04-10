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
    public void add_invalidEvent_fail() {
        commandBox.runCommand("a invalidEvent from: today");
        assertResultMessage(String.format
                (Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
    @Test
    public void add_invalidMultiplePrefix_fail() {
        commandBox.runCommand("+ invalidEvent by: today @tmr");
        assertResultMessage(String.format
                (Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
    @Test
    public void add_validTask_success() throws IllegalValueException {
        commandBox.runCommand("add success p/Low");
        TestTask expectedResult = new TaskBuilder().withName("success")
                .withDate("").withEndDate("").withCompleted(false).withPriority("Low").build();
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, expectedResult));
    }
    @Test
    public void add_taskWithInvalidName_fail() {
        commandBox.runCommand("add ^_^");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);
    }
    @Test
    public void add_taskWithEndDateBeforeStartDate_fail() {
        commandBox.runCommand("add failEvent from: today to: yesterday");
        assertResultMessage(Messages.MESSAGE_INVALID_START_AND_END_DATE);
    }
    @Test
    public void add_taskWithInvalidStartDate_fail() {
        commandBox.runCommand("add failEvent from: gg to: yesterday");
        assertResultMessage(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_START_DATE);
    }
    @Test
    public void add_taskWithInvalidEndDate_fail() {
        commandBox.runCommand("add failEvent from: today to: gg");
        assertResultMessage(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_END_DATE);
    }
    @Test
    public void add_taskWithInvalidTime_fail() {
        commandBox.runCommand("add failEvent @lol");
        assertResultMessage(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_DATE);
    }
    @Test
    public void add_taskWithInvalidDate_fail() {
        commandBox.runCommand("add invalidDate by: lol");
        assertResultMessage(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_DATE);
    }
    @Test
    public void add_deadlineTaskWithDateNoTime_success() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("add deadline by: 10 oct 1993 p/Low");
        TestTask deadlineTask = new TaskBuilder().withName("deadline")
                .withDate("").withEndDate("Sun Oct 10 1993 23:59:59")
                .withCompleted(false).withPriority("Low").build();
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, deadlineTask);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    @Test
    public void add_deadlineTaskwithDateWithTime_success() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("add deadline @ 10 oct 1993 4pm p/Low");
        TestTask deadlineTask = new TaskBuilder().withName("deadline")
                .withDate("").withEndDate("Sun Oct 10 1993 16:00:00")
                .withCompleted(false).withPriority("Low").build();
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, deadlineTask);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    @Test
    public void add_eventTask_success() throws IllegalValueException {
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
