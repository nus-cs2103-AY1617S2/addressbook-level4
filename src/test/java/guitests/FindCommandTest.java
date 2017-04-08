package guitests;

import org.junit.Test;

import t09b1.today.commons.core.Messages;
import t09b1.today.commons.exceptions.IllegalValueException;
import t09b1.today.model.task.Task;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() throws IllegalArgumentException, IllegalValueException {
        // No results
        assertFindResult("find dragon", new Task[] {}, new Task[] {}, new Task[] {});

        // Multiple Results
        assertFindResult("find assignment", new Task[] { td.todayListOverdue },
                new Task[] { td.futureListDeadline, td.futureListEvent }, new Task[] { td.completedListFloat });

        // find after deleting one result
        commandBox.runCommand("delete T1");
        assertFindResult("find assignment", new Task[] {}, new Task[] { td.futureListDeadline, td.futureListEvent },
                new Task[] { td.completedListFloat });
    }

    @Test
    public void find_emptyList() throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("clear");
        assertFindResult("find Jean", new Task[] {}, new Task[] {}, new Task[] {});
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, Task[] expectedTodayList, Task[] expectedFutureList,
            Task[] expectedCompletedList) throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(command);

        int expectedHits = expectedTodayList.length + expectedFutureList.length;
        assertResultMessage(expectedHits + " tasks listed!");

        assertAllListsMatching(expectedTodayList, expectedFutureList, expectedCompletedList);

    }
}
