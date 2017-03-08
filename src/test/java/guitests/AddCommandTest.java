package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.taskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask personToAdd = td.hoon;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, personToAdd);

        //add another task
        personToAdd = td.ida;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, personToAdd);

        //add duplicate task
        commandBox.runCommand(td.hoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        taskCardHandle addedCard = taskListPanel.navigateToTask(personToAdd.getName().fullName);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, personToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
