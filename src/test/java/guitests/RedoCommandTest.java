package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import typetask.testutil.TestTask;
import typetask.testutil.TestUtil;

public class RedoCommandTest extends AddressBookGuiTest {

    @Test
    public void redo() {
        TestTask[] currentList = td.getTypicalTasks();

        //redo add one task after undo
        TestTask taskToAdd = td.hoon;
        commandBox.runCommand(taskToAdd.getAddCommand());
        //confirm the new card contains the right data
        PersonCardHandle addedCard = personListPanel.navigateToPerson(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);
        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addPersonsToList(currentList, taskToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
        commandBox.runCommand("undo");
        assertTrue(personListPanel.isListMatching(td.getTypicalTasks()));
        assertRedoSuccess(expectedList);

        //revert the list back for testing
        commandBox.runCommand("undo");

        //redo clear command after undo
        assertTrue(personListPanel.isListMatching(td.getTypicalTasks()));
        commandBox.runCommand("clear");
        assertListSize(0);
        commandBox.runCommand("undo");
        assertTrue(personListPanel.isListMatching(td.getTypicalTasks()));
        TestTask[] emptyList = {};
        assertRedoSuccess(emptyList);
            }
    private void assertRedoSuccess(TestTask[] expectedList) {
        commandBox.runCommand("redo");
        assertTrue(personListPanel.isListMatching(expectedList));
    }
}
