package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import project.taskcrusher.commons.core.Messages;
import project.taskcrusher.logic.commands.AddCommand;
import project.taskcrusher.testutil.TestTaskCard;
import project.taskcrusher.testutil.TestUtil;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one task
        TestTaskCard[] currentList = td.getTypicalTasks();
        TestTaskCard taskToAdd = td.notAddedYetQuiz;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.notAddedBuyTicket;

        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.phoneCall.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(userInboxPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.assignment);

        //invalid command
        commandBox.runCommand("adds earning 100 dollars");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTaskCard taskToAdd, TestTaskCard... currentList) {

        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = userInboxPanel.navigateToPerson(taskToAdd.getName().name);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTaskCard[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(userInboxPanel.isListMatching(expectedList));
    }

}
