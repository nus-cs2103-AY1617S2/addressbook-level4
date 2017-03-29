package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.logic.commands.AddCommand;
import org.teamstbf.yats.testutil.TestEvent;
import org.teamstbf.yats.testutil.TestUtil;

import guitests.guihandles.PersonCardHandle;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one person
        TestEvent[] currentList = td.getTypicalTasks();
        TestEvent personToAdd = td.cower;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, personToAdd);

        //add another person
        personToAdd = td.cower;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, personToAdd);

        //add duplicate person
        commandBox.runCommand(td.cower.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_EVENT);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.duck);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestEvent personToAdd, TestEvent... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        PersonCardHandle addedCard = taskListPanel.navigateToPerson(personToAdd.getTitle().fullName);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestEvent[] expectedList = TestUtil.addPersonsToList(currentList, personToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
