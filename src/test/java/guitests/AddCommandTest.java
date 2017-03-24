package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.logic.commands.AddCommand;
import seedu.taskboss.testutil.TestTask;
import seedu.taskboss.testutil.TestUtil;

public class AddCommandTest extends TaskBossGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.taskH;
        assertAddSuccess(false, taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.taskI;
        assertAddSuccess(false, taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task using short command
        taskToAdd = td.taskK;
        assertAddSuccess(true, taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.taskH.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add invalid dates task
        commandBox.runCommand(td.taskJ.getAddCommand());
        assertResultMessage(AddCommand.ERROR_INVALID_DATES);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(false, td.taskA);

        //invalid command
        commandBox.runCommand("adds new task");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(boolean isShortCommand, TestTask taskToAdd, TestTask... currentList) {
        if (isShortCommand) {
            commandBox.runCommand(taskToAdd.getShortAddCommand());
        } else {
            commandBox.runCommand(taskToAdd.getAddCommand());
        }

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
