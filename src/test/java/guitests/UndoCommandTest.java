package guitests;
import static org.junit.Assert.assertTrue;
import static typetask.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import typetask.testutil.TestTask;
import typetask.testutil.TestUtil;


public class UndoCommandTest extends AddressBookGuiTest {

    @Test
    public void undo() {
        //undo clear command
        assertTrue(personListPanel.isListMatching(td.getTypicalTasks()));
        commandBox.runCommand("clear");
        assertListSize(0);
        assertUndoSuccess();

        //undo delete command
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        TestTask taskToDelete = currentList[targetIndex - 1]; // -1 as array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removePersonFromList(currentList, targetIndex);
        commandBox.runCommand("delete " + targetIndex);
        //confirm the list now contains all previous tasks except the deleted person
        assertTrue(personListPanel.isListMatching(expectedRemainder));
        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
        assertUndoSuccess();

        //undo add command
        //add one task
        TestTask taskToAdd = td.hoon;
        commandBox.runCommand(taskToAdd.getAddCommand());
        //confirm the new card contains the right data
        PersonCardHandle addedCard = personListPanel.navigateToPerson(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);
        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addPersonsToList(currentList, taskToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
        assertUndoSuccess();


        //undo edit command....working in progress...
    }
    private void assertUndoSuccess() {
        commandBox.runCommand("undo");
        assertTrue(personListPanel.isListMatching(td.getTypicalTasks()));
    }
}
