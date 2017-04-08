package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import t09b1.today.commons.core.Messages;
import t09b1.today.commons.exceptions.IllegalValueException;
import t09b1.today.logic.commands.AddCommand;
import t09b1.today.model.task.Task;
import t09b1.today.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() throws IllegalArgumentException, IllegalValueException {
        // add one task
        Task taskToAdd = td.extraFloat;
        futureList = TestUtil.addTasksToList(futureList, taskToAdd, 1);
        assertFutureAddSuccess(taskToAdd, todayList, futureList);

        // add another task
        taskToAdd = td.extraDeadline;
        todayList = TestUtil.addTasksToList(todayList, taskToAdd, 3);
        assertTodayAddSuccess(taskToAdd, todayList, futureList);

        // add duplicate task
        commandBox.runCommand(TestUtil.makeAddCommandString(taskToAdd));
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(futureTaskListPanel.isListMatching(futureList));

        // add to empty list
        commandBox.runCommand("clear");
        commandBox.runCommand(TestUtil.makeAddCommandString(taskToAdd));
        assertTodayAddSuccess(taskToAdd, new Task[] { taskToAdd }, emptyTaskList);

        // invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    // ----- Helper -----
    private void assertFutureAddSuccess(Task taskToAdd, Task[] expectedTodayList, Task[] expectedFutureList)
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(TestUtil.makeAddCommandString(taskToAdd));

        // confirm the new card contains the right data
        TaskCardHandle addedCard = futureTaskListPanel.navigateToTask(taskToAdd.getName().toString());
        assertMatching(taskToAdd, addedCard);

        // confirm the list now contains all previous tasks plus the new task
        assertTodayFutureListsMatching(expectedTodayList, expectedFutureList);
    }

    private void assertTodayAddSuccess(Task taskToAdd, Task[] expectedTodayList, Task[] expectedFutureList)
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(TestUtil.makeAddCommandString(taskToAdd));

        // confirm the new card contains the right data
        TaskCardHandle addedCard = todayTaskListPanel.navigateToTask(taskToAdd.getName().toString());
        assertMatching(taskToAdd, addedCard);

        // confirm the list now contains all previous tasks plus the new task
        assertTodayFutureListsMatching(expectedTodayList, expectedFutureList);
    }

}
