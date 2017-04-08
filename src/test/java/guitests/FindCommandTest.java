package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import project.taskcrusher.commons.core.Messages;
import project.taskcrusher.testutil.TestEventCard;
import project.taskcrusher.testutil.TestTaskCard;
import project.taskcrusher.testutil.TestUtil;

//@@author A0127737X
/**
 * The find command searches for any keywords contained as a substring in the name, description, tags
 * (and for events, location). Checks through both the event and task lists for any hits with a keyword.
 */
public class FindCommandTest extends TaskcrusherGuiTest {

    @Test
    public void find_nonEmptyList() {

        //no hits in tasks nor events
        TestTaskCard[] currentTaskList = new TestTaskCard[0];
        TestEventCard[] currentEventList = new TestEventCard[0];
        assertFindResult("find hahahahaha", currentTaskList, currentEventList);

        //hits across events and tasks
        currentTaskList = TestUtil.addTasksToList(currentTaskList, td.assignment1, td.assignment2);

        currentEventList = TestUtil.addEventsToList(currentEventList, td.islandTrip);

        assertFindResult("find assignment", currentTaskList, currentEventList);

        //find after deleting one result
        currentTaskList = TestUtil.removeTasksFromList(currentTaskList, td.assignment1);
        commandBox.runCommand("delete t 1");
        assertFindResult("find assignment", currentTaskList, currentEventList);

        //multiple keywords provided
        currentTaskList = TestUtil.addTasksToList(currentTaskList, td.payment);
        assertFindResult("find assignment school", currentTaskList, currentEventList);

    }

    @Test
    public void find_emptyList() {
        TestTaskCard[] emptyTaskList = new TestTaskCard[0];
        TestEventCard[] emptyEventList = new TestEventCard[0];
        commandBox.runCommand("clear");
        assertFindResult("find Jean", emptyTaskList, emptyEventList); // no results
    }

    @Test
    public void find_nonEmptyList_no_hit() {
        TestTaskCard[] emptyTaskList = new TestTaskCard[0];
        TestEventCard[] emptyEventList = new TestEventCard[0];
        assertFindResult("find Jean", emptyTaskList, emptyEventList); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTaskCard[] expectedTaskHits, TestEventCard[]
            expectedEventHits) {
        commandBox.runCommand(command);
        assertTaskListSize(expectedTaskHits.length);
        assertEventListSize(expectedEventHits.length);
        assertTrue(userInboxPanel.isListMatching(expectedTaskHits));
        assertTrue(userInboxPanel.isListMatching(expectedEventHits));
        assertResultMessage(expectedTaskHits.length + " tasks and " + expectedEventHits.length
                + " events listed!");
    }

}
