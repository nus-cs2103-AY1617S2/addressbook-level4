package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.whatsleft.commons.core.Messages;
import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;

public class FindCommandTest extends WhatsLeftGuiTest {

    @Test
    public void findNonEmptyList() {
        assertFindResult("find play", new TestEvent[]{}, new TestTask[]{}); // no results
        assertFindResult("find project", new TestEvent[]{te.discussion}, new TestTask[]{tt.report}); // multiple results

        //find after list
        commandBox.runCommand("list");
        commandBox.runCommand("delete ts 3");
        assertFindResult("find project", new TestEvent[]{te.discussion}, new TestTask[]{});

        //find after deleting one result
        commandBox.runCommand("delete ev 1");
        assertFindResult("find project", new TestEvent[]{}, new TestTask[]{});
    }

    @Test
    public void findEmptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find tutorial", new TestEvent[]{}, new TestTask[]{}); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findwhatsleft");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestEvent[] expectedEventList, TestTask[] expectedTaskList) {
        commandBox.runCommand(command);
        assertEventListSize(expectedEventList.length);
        assertTaskListSize(expectedTaskList.length);
        int numberOfActivities = expectedEventList.length + expectedTaskList.length;
        assertResultMessage(numberOfActivities + " activities listed!");
        assertTrue(eventListPanel.isListMatching(expectedEventList));
        assertTrue(taskListPanel.isListMatching(expectedTaskList));
    }

}
