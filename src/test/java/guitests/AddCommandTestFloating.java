//@@author A0146738U

package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.bulletjournal.commons.core.Messages;
import seedu.bulletjournal.logic.commands.AddCommandFloating;
import seedu.bulletjournal.testutil.TestTask;
import seedu.bulletjournal.testutil.TestUtil;

public class AddCommandTestFloating extends TodoListGuiTest {

    @Test
    public void add() {
        //add one floating task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.floatone;
        assertAddSuccessFloating(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.floattwo;
        assertAddSuccessFloating(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.floatone.getAddCommandFloating("addf "));
        assertResultMessage(AddCommandFloating.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccessFloating(td.floatone);

        //invalid command
        commandBox.runCommand("ad Join club");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccessFloating(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommandFloating("addf "));

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getTaskName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
