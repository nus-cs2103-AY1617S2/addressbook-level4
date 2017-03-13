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
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, taskToAdd);

        //add another task with priority
        taskToAdd = td.taskWithPriority;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.hoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        assertTrue(personListPanel.isListMatching(currentList));

        // add a task without description (invalid)
        commandBox.runCommand("do \\as important");
        assertResultMessage(AddCommand.MESSAGE_NO_DESC);

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.laundry);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = personListPanel.navigateToPerson(taskToAdd.getDescription().desc);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}
