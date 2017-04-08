package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.TaskCardHandle;
import project.taskcrusher.commons.core.Messages;
import project.taskcrusher.logic.commands.AddCommand;
import project.taskcrusher.testutil.TestEventCard;
import project.taskcrusher.testutil.TestTaskCard;
import project.taskcrusher.testutil.TestUtil;

public class AddCommandTest extends TaskcrusherGuiTest {

    @Test
    public void addTasks() {
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
        assertAddSuccess(td.assignment1);

        //invalid command
        commandBox.runCommand("adds earning 100 dollars");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void addEvents() {
        //add one event
        TestEventCard[] currentList = td.getTypicalEvents();
        TestEventCard eventToAdd = td.notAddedYetCheckSamples;
        assertAddSuccess(eventToAdd, currentList);
        currentList = TestUtil.addEventsToList(currentList, eventToAdd);

        //add another event
        eventToAdd = td.notAddedYetTownFestival;

        assertAddSuccess(eventToAdd, currentList);
        currentList = TestUtil.addEventsToList(currentList, eventToAdd);

        //add duplicate event
        commandBox.runCommand(td.islandTrip.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_EVENT_CLASHES);
        assertTrue(userInboxPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.fixRoof);

        //invalid command
        commandBox.runCommand("adds going to Mars");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTaskCard taskToAdd, TestTaskCard... currentList) {

        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = userInboxPanel.navigateToTask(taskToAdd.getName().name);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTaskCard[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(userInboxPanel.isListMatching(expectedList));
    }

    private void assertAddSuccess(TestEventCard eventToAdd, TestEventCard... currentList) {

        commandBox.runCommand(eventToAdd.getAddCommand());

        //confirm the new card contains the right data
        EventCardHandle addedCard = userInboxPanel.navigateToEvent(eventToAdd.getName().name);
        assertMatching(eventToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestEventCard[] expectedList = TestUtil.addEventsToList(currentList, eventToAdd);
        assertTrue(userInboxPanel.isListMatching(expectedList));
    }

}
