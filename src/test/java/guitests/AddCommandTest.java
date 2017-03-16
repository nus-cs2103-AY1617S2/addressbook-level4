package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import project.taskcrusher.commons.core.Messages;
import project.taskcrusher.logic.commands.AddCommand;
import project.taskcrusher.testutil.TestCard;
import project.taskcrusher.testutil.TestUtil;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one task
        TestCard[] currentList = td.getTypicalTasks();
//        //TODO: need to have some new task, not a duplicate
//        TestCard taskToAdd = td.assignment;
//        assertAddSuccess(taskToAdd, currentList);
//        currentList = TestUtil.addPersonsToList(currentList, taskToAdd);
//
//        //add another task
//        taskToAdd = td.phoneCall;
//        assertAddSuccess(taskToAdd, currentList);
//        currentList = TestUtil.addPersonsToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.phoneCall.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(personListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.assignment);

        //invalid command
        commandBox.runCommand("adds earning 100 dollars");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestCard taskToAdd, TestCard... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = personListPanel.navigateToPerson(taskToAdd.getTaskName().name);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestCard[] expectedList = TestUtil.addPersonsToList(currentList, taskToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}
