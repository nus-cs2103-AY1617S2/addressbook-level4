package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.logic.commands.AddCommand;
import seedu.taskmanager.testutil.TestTask;
import seedu.taskmanager.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one person
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.send2;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another person
        taskToAdd = td.call1;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate person
        commandBox.runCommand(td.send2.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.buy1);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(personToAdd.getTitle().value);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, personToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
