package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.watodo.commons.core.Messages;
import seedu.watodo.logic.commands.AddCommand;
import seedu.watodo.testutil.TestTask;
import seedu.watodo.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        /*TestTask taskToAdd = td.play;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTaskToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.shop;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTaskToList(currentList, taskToAdd);*/

        //add duplicate task
        commandBox.runCommand(td.play.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.sleep);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getDescription().fullDescription);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTaskToList(currentList, taskToAdd);

        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
