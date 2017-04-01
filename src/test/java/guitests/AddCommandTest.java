package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.doist.commons.core.Messages;
import seedu.doist.logic.commands.AddCommand;
import seedu.doist.testutil.TestTask;
import seedu.doist.testutil.TestUtil;

public class AddCommandTest extends DoistGUITest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();

        TestTask taskToAdd = td.email;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.exercise;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task with priority
        taskToAdd = td.chores;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task with dates
        taskToAdd = td.meeting;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

      //add another task with from to dates
        taskToAdd = td.movie;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.email.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        assertTrue(taskListPanel.isListMatching(currentList));

        // add a task without description (invalid)
        commandBox.runCommand("do \\as important");
        assertResultMessage(AddCommand.MESSAGE_NO_DESC);

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.laundry);

        //invalid command
        commandBox.runCommand("adds Do laundry");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        //invalid command since \from and \by are used together
        commandBox.runCommand("add Do laundry \\from today \\by tomorrow");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        //invalid command since \to and \by are used together
        commandBox.runCommand("add Do laundry \\to today \\by tomorrow");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        //invalid command since \from and \to aren't used together
        commandBox.runCommand("add Do laundry \\from today ");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getDescription().desc);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
