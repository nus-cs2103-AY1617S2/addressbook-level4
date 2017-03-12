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
        //add one person
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.email;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another person
        taskToAdd = td.exercise;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate person
        commandBox.runCommand(td.email.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        assertTrue(personListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.laundry);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = personListPanel.navigateToPerson(personToAdd.getDescription().desc);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, personToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}
